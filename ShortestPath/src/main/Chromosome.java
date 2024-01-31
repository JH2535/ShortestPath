package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Chromosome {

	private List<LatLon> path;
	
	public Chromosome(Set<LatLon> path, int expectedLength) 
			throws IllegalStateException {
		if (path.size() != expectedLength) {
			throw new IllegalStateException("Inconsistent size");
		}
		this.path = new ArrayList<LatLon>(path);
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
}
