package com.portfolio.shortest_path;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Chromosome {
	
	private final float MUTATION_RATE = 0.70f;

	private List<LatLon> path;
	private double score;
	
	public Chromosome(Set<LatLon> path, int expectedLength) 
			throws IllegalStateException {
		if (path.size() != expectedLength) {
			throw new IllegalStateException("Inconsistent size");
		}
		this.path = new ArrayList<LatLon>(path);
		this.score = 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromosome other = (Chromosome) obj;
		return Objects.equals(path, other.path);
	}
	
	public void randomise() {
		Random rand = new Random();
		this.randomise(rand.nextInt());
	}

	public void randomise(int seed) {
		Random rand = new Random(seed);
		List<LatLon> result = new ArrayList<LatLon>();
		while(this.path.size() > 0) {
			Object[] vals = this.path.toArray();
			LatLon toBeSwapped = (LatLon) vals[rand.nextInt(vals.length)];
			result.add(toBeSwapped);
			this.path.remove(toBeSwapped);
		}
		this.path = result;
	}

	public double computeScore() {
		if(this.score > 0 || (this.path.size() == 0)) {
			return this.score;
		}
		LatLon first = null;
		LatLon previousLocation = null;
		for(LatLon currentLocation: this.path) {
			if(previousLocation == null) {
				first = currentLocation;
				previousLocation = currentLocation;
				continue;
			}
			this.score += previousLocation.distanceTo(currentLocation);
			previousLocation = currentLocation;
		}
		this.score += previousLocation.distanceTo(first);
		return this.score;
	}

	public List<LatLon> getPath() {
		return new ArrayList<>(this.path);
	}

	public int getPathLength() {
		return this.path.size();
	}
	
	public void mutate(int seed) {
		Random random = new Random(seed);
		float roll = random.nextFloat();
		if(roll > MUTATION_RATE) {
			return;
		}
		int indexToSwapWithNext = random.nextInt(this.path.size() - 1);
		LatLon temp = this.path.get(indexToSwapWithNext);
		this.path.remove(indexToSwapWithNext);
		this.path.add(indexToSwapWithNext + 1,temp);
	}
}
