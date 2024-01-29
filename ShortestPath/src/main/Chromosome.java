package main;

import java.util.Objects;
import java.util.Set;

public class Chromosome {

	private Set<LonLat> path;
	
	public Chromosome(Set<LonLat> path, int expectedLength) 
			throws IllegalStateException {
		if ((path.size() + 1) != expectedLength) {
			throw new IllegalStateException("Inconsistent size");
		}
		this.path = path;
	}
}
