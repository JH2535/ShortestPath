package com.portfolio.shortest_path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public class Breeder {
	
	private final int FAIR_BREEDING_ATTEMPTS = 5;
	private final float PARENT_SIMILAR_PERCENT = 0.1f;

	
	private Chromosome mother;
	private Chromosome father;
	
	private List<LatLon> fatherSnip;
	private List<List<LatLon>> motherCuts;
	
	private List<LatLon> bestFatherSnip;
	private List<List<LatLon>> bestMotherCuts;

	public Breeder(Chromosome mother, Chromosome father) 
			throws IllegalArgumentException {
		if(mother.equals(father)) {
			throw new IllegalArgumentException("Parents have same genetics");
		}
		
		this.mother = mother;
		this.father = father;
	}

	public Chromosome nextChild() {
		Random random = new Random();
		return this.nextChild(random.nextInt());
	}

	public Chromosome nextChild(int seed) {
		int expectedLength = mother.getPathLength();
		Random random = new Random(seed);
		int minSnipSize = Math.max(Math.min(2, expectedLength - 1), expectedLength/5);
		int maxSnipSize = Math.min(Math.max(3, expectedLength/3), expectedLength);
		
		int snipSize = random.nextInt(minSnipSize, maxSnipSize);
		int snipStartLoc = random.nextInt(expectedLength - snipSize);
		int offset = 0;
		
		this.fatherSnip = this.findFatherSnip(snipStartLoc, snipSize);
		this.motherCuts = this.findMotherCuts();
		
		this.bestFatherSnip = this.fatherSnip;
		this.bestMotherCuts = this.motherCuts;
		
		int attempts = 0;
		while(!this.parentsContributionSimilarSize()) {
			offset += 1;
			while(snipSize - offset < 1) {
				snipSize = random.nextInt(expectedLength/5, expectedLength/3);
				snipStartLoc = random.nextInt(expectedLength - snipSize);
				offset = 0;
				attempts += 1;
			}
			this.fatherSnip = this.findFatherSnip(snipStartLoc, snipSize - offset);
			this.motherCuts = this.findMotherCuts();
			if(this.isBetter(this.motherCuts, this.bestMotherCuts)) {
				this.bestFatherSnip = this.fatherSnip;
				this.bestMotherCuts = this.motherCuts;
			}
			if(attempts >= FAIR_BREEDING_ATTEMPTS) {
				break;
			}
		}
		
		List<LatLon> bestMothersSimilarSnip = getBestMotherSimilar();
		List<LatLon> childsPartial = combine(this.bestFatherSnip, bestMothersSimilarSnip);
		List<LatLon> childsList = combine(childsPartial, findLeftovers(childsPartial));
		Set<LatLon> childsPath = new HashSet<LatLon>(childsList);
		return new Chromosome(childsPath, expectedLength);
	}
	
	private List<LatLon> findFatherSnip(int snipStartLoc, int snipSize) {
		return this.father.getPath().subList(snipStartLoc, snipStartLoc + snipSize);
	}

	private List<List<LatLon>> findMotherCuts() {
		List<List<LatLon>> motherCuts = new ArrayList<>();
		motherCuts.add(this.mother.getPath());
		for(LatLon fathersBase: this.fatherSnip) {
			motherCuts = subdivideMotherCutsAt(fathersBase, motherCuts);
		}
		return motherCuts;
	}

	private List<List<LatLon>> subdivideMotherCutsAt(LatLon base, List<List<LatLon>> motherCuts) {
		List<List<LatLon>> newCuts = new ArrayList<>();
		for(List<LatLon> currentCut: motherCuts) {
			int indexOfCut = currentCut.lastIndexOf(base);
			if(indexOfCut == -1) {
				continue;
			}
			List<LatLon> firstHalf = currentCut.subList(0, indexOfCut);
			List<LatLon> secondHalf = currentCut.subList(indexOfCut, currentCut.size());
			newCuts.add(firstHalf);
			newCuts.add(secondHalf);
		}
		return newCuts;
	}

	private boolean parentsContributionSimilarSize() {
		int pathSize = this.father.getPathLength();
		int fathersContribuationSize = this.fatherSnip.size();
		for(List<LatLon> mothersCut: this.motherCuts) {
			float lowerThreshold = fathersContribuationSize - pathSize*PARENT_SIMILAR_PERCENT;
			boolean lowerBound = mothersCut.size() >= lowerThreshold;
			float upperThreshold = fathersContribuationSize + pathSize*PARENT_SIMILAR_PERCENT;
			boolean upperBound = mothersCut.size() <= upperThreshold;
			
			if(lowerBound && upperBound) {
				return true;
			}
		}
		return false;
	}

	private boolean isBetter(List<List<LatLon>> current, List<List<LatLon>> best) {
		return findMatchPercentage(this.fatherSnip, current) > 
			findMatchPercentage(this.bestFatherSnip, best);
	}
	
	private float findMatchPercentage(List<LatLon> snip, List<List<LatLon>> cuts) {
		float bestPercentage = 0.0f;
		for(List<LatLon> cut: cuts) {
			int cutSize = cut.size();
			int snipSize = snip.size();
			
			int larger = (cutSize > snipSize)? cutSize: snipSize;
			int smaller = (cutSize < snipSize)? cutSize: snipSize;
			bestPercentage = (float)smaller / (float)larger;
		}
		return bestPercentage;
	}

	private List<LatLon> getBestMotherSimilar() {
		
		List<LatLon> currentBest = new ArrayList<>();
		int bestSizeDiff = this.bestFatherSnip.size() - currentBest.size();
		for(List<LatLon> bestMotherCut: this.bestMotherCuts) {
			int sizeDiff = this.bestFatherSnip.size() - bestMotherCut.size();
			if (sizeDiff < bestSizeDiff && sizeDiff > -bestSizeDiff) {
				currentBest = bestMotherCut;
				bestSizeDiff = sizeDiff;
			}
		}
		return currentBest;
	}

	private List<LatLon> combine(List<LatLon> first, List<LatLon> second) {
		return Stream.concat(first.stream(), second.stream()).toList();
	}

	private List<LatLon> findLeftovers(List<LatLon> childsPartial) {
		List<LatLon> leftovers = new ArrayList<>();
		List<LatLon> fathersPath = this.father.getPath();
		for(LatLon fathersBase: fathersPath) {
			if (childsPartial.lastIndexOf(fathersBase) == -1) {
				leftovers.add(fathersBase);
			}
		}
		return leftovers;
	}
}
