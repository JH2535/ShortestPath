package com.portfolio.shortest_path;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Test;


public class BreederTest {
	
	private final int PATH_LENGTH = 8;
	
	@Test 
	public void breederRequiresDifferentParentsTest() {
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

	@Test
	public void breedingDoesNotEffectParentsTest() {
		Chromosome father = new Chromosome(getFatherPath(), PATH_LENGTH);
		Chromosome fatherCopy = new Chromosome(getFatherPath(), PATH_LENGTH);
		
		Chromosome mother = new Chromosome(getMotherPath(), PATH_LENGTH);
		Chromosome motherCopy = new Chromosome(getMotherPath(), PATH_LENGTH);
		
		Breeder breeder = new Breeder(mother, father);
		int seed = 5543;
		breeder.nextChild(seed);
		
		boolean sameFather = father.equals(fatherCopy);
		boolean sameMother = mother.equals(motherCopy);
		assertTrue(sameFather && sameMother);
	}
	
	private Set<LatLon> getMotherPath() {
		Set<LatLon> path = new LinkedHashSet<LatLon>();
		Collections.addAll(
			path,
			new LatLon(49.5255556, 5.9405556),
			new LatLon(49.7388889, 6.3450000),
			new LatLon(49.8286111, 5.7647222),
			new LatLon(49.7577778, 6.3533333),
			new LatLon(49.5333333, 6.3166667),
			new LatLon(49.6083333, 6.4058333),
			new LatLon(49.7966667, 6.1555556),
			new LatLon(49.7163889, 6.4900000)
		);
		return path;
	}
	
	@Test
	public void childIsDifferentFromEachParentTest() {
		Chromosome father = new Chromosome(getFatherPath(), PATH_LENGTH);
		Chromosome mother = new Chromosome(getMotherPath(), PATH_LENGTH);
		
		Breeder breeder = new Breeder(mother, father);
		int seed = 5543;
		Chromosome child = breeder.nextChild(seed);
		
		try {
			boolean childNotFather = !child.equals(father);
			boolean childNotMother = !child.equals(mother);
		
			assertTrue(childNotFather && childNotMother);
		} catch (NullPointerException e) {
			fail("Breeding resulted in a null child object.");
		}
	}
	
	@Test
	public void unmutatedChildHasParentsCharacteristicsTest() {
		Chromosome father = new Chromosome(getFatherPath(), PATH_LENGTH);
		Chromosome mother = new Chromosome(getMotherPath(), PATH_LENGTH);
		
		Breeder breeder = new Breeder(mother, father);
		int seed = 5543;
		Chromosome child = breeder.nextUnmutatedChild(seed);
		try {
			Map<Integer, Integer> fathersMatchingSnips = findMatchingSnipStartLength(father, child);
			Map<Integer, Integer> mothersMatchingSnips = findMatchingSnipStartLength(mother, child);

			int fathersMaxSnipSize = maxSnipSize(fathersMatchingSnips);
			int mothersMaxSnipSize = maxSnipSize(mothersMatchingSnips);

			boolean isExpectedSize = child.getPathLength() == PATH_LENGTH;

			boolean isRoughlySameSize = fathersMaxSnipSize <= mothersMaxSnipSize * 1.2
					|| fathersMaxSnipSize >= mothersMaxSnipSize * 0.8;

			boolean hasSomeContributation = fathersMaxSnipSize + mothersMaxSnipSize > 2;

			assertTrue(isExpectedSize && isRoughlySameSize && hasSomeContributation);
		} catch (NullPointerException e) {
			fail(e.getMessage());
		}
	}
	
	private Map<Integer,Integer> findMatchingSnipStartLength(Chromosome first, Chromosome second)
		throws NullPointerException {
		List<LatLon> firstPath = first.getPath();
		List<LatLon> secondPath = second.getPath();
		
		Map<Integer,Integer> snipStartLengths = new HashMap<>();
		List<LatLon> seenPoints = new ArrayList<>();
		int count = 0;
		for(int i = 0; i < PATH_LENGTH; i++) {
			if(seenPoints.contains(firstPath.get(i))) {
				continue;
			}
			for(int j = 0; j < PATH_LENGTH; j++) {
				if(seenPoints.contains(secondPath.get(j))) {
					continue;
				}
				if(firstPath.get(i).equals(secondPath.get(j))) {
					try {
						do {
							count += 1;
							seenPoints.add(firstPath.get(i + count));
						} while (firstPath.get(i + count).equals(secondPath.get(j + count)));
					} catch(IndexOutOfBoundsException e) {
					}
					snipStartLengths.put(Integer.valueOf(j), Integer.valueOf(count));
				}
			}
			count = 0;
		}
		return snipStartLengths;
	}

	private int maxSnipSize(Map<Integer, Integer> snips) {
		int maxSnipSize = Integer.MIN_VALUE;
		for (Entry<Integer, Integer> snip : snips.entrySet()) {
			int snipSize = snip.getValue();
			maxSnipSize = (snipSize > maxSnipSize) ? snipSize : maxSnipSize;
		}
		return maxSnipSize;
	}
}
