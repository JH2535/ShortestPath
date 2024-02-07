package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Environment {
	
	private final int PATH_SIZE;
	
	private Breeder breeder;
	
	private Chromosome mother;
	private Chromosome father;
	
	
	
	public Environment(String filePath, int seed)
			throws FileNotFoundException{
		Set<LatLon> path = (new TSPFileReader(filePath)).readLonLats();
		this.PATH_SIZE = path.size();
		this.mother = new Chromosome(path, PATH_SIZE);
		this.father = new Chromosome(path, PATH_SIZE);
		
		father.randomise(seed);
		this.breeder = new Breeder(mother, father);
	}
	
	public Environment(String filePath)
			throws FileNotFoundException{
		this(filePath, (new Random()).nextInt());
	}
	
	public Set<Chromosome> getNextGeneration() {
		return this.getNextGeneration(PATH_SIZE);
	}
	
	public Set<Chromosome> getNextGeneration(int num) {
		Random random = new Random();
		return this.getNextGeneration(num, random);
		
	}

	public Set<Chromosome> getNextGeneration(int num, int seed) {
		Random random = new Random(seed);
		return this.getNextGeneration(num, random);
	}
	
	private Set<Chromosome> getNextGeneration(int num, Random random) {
		Set<Chromosome> nextGeneration = new HashSet<>();
		nextGeneration.add(this.mother);
		nextGeneration.add(this.father);
		while(nextGeneration.size() < num)
			nextGeneration.add(breeder.nextChild(random.nextInt()));
		this.setParentsFromGeneration(nextGeneration);
		this.setBreeder();
		return nextGeneration;
	}

	private void setParentsFromGeneration(Set<Chromosome> generation) {
		double bestMotherScore = Double.MAX_VALUE;
		double bestFatherScore = Double.MAX_VALUE;
		Chromosome bestMother = new Chromosome(new HashSet<>(), 0);
		Chromosome bestFather = new Chromosome(new HashSet<>(), 0);
		for(Chromosome child: generation) {
			double childScore = child.computeScore();
			if(childScore < bestMotherScore) {
				bestMother = child;
				bestMotherScore = childScore;
			}
			if(childScore < bestFatherScore && !child.equals(bestMother)) {
				bestFather = child;
				bestFatherScore = childScore;
			}
		}
		this.father = bestFather;
		this.mother = bestMother;
	}
	
	private void setBreeder() {
		this.breeder = new Breeder(this.mother, this.father);
	}

	public List<Chromosome> getParents() {
		List<Chromosome> parents = new ArrayList<>();
		parents.add(this.mother);
		parents.add(this.mother);
		return parents;
	}

	

}
