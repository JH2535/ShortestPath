package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import main.Breeder;
import main.Chromosome;
import main.LatLon;

public class BreederTest {
	
	private final int PATH_LENGTH = 8;
	
	@Test 
	public void breederRequiresDifferentParents() {
		Chromosome father = new Chromosome(getFatherPath(), PATH_LENGTH);
		Chromosome fatherCopy = new Chromosome(getFatherPath(), PATH_LENGTH);
		try {
			new Breeder(father, fatherCopy);
			fail("Created breeder with same parents...");
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	private Set<LatLon> getFatherPath() {
		Set<LatLon> path = new LinkedHashSet<LatLon>();
		Collections.addAll(
			path,
			new LatLon(49.5255556, 5.9405556),
			new LatLon(49.7388889, 6.3450000),
			new LatLon(49.6083333, 6.4058333),
			new LatLon(49.7966667, 6.1555556),
			new LatLon(49.8286111, 5.7647222),
			new LatLon(49.7577778, 6.3533333),
			new LatLon(49.5333333, 6.3166667),
			new LatLon(49.7163889, 6.4900000)
		);
		return path;
	}
}
