package main;

public class Breeder {
	
	private Chromosome mother;
	private Chromosome father;

	public Breeder(Chromosome mother, Chromosome father) 
			throws IllegalArgumentException {
		if(mother.equals(father)) {
			throw new IllegalArgumentException("Parents have same genetics");
		}
		
		this.mother = mother;
		this.father = father;
	}
}
