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
}
