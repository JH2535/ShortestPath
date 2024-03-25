package com.portfolio.shortest_path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;


public class ChromosomeTest {
	
	private final int TEST_PATH_LENGTH = 4;

	@Test
	public void creationTest() {
		try {
			new Chromosome(getTestPath(), TEST_PATH_LENGTH - 1);
			fail("Created chromosome when length of path doesn't match expected");
		} catch (IllegalStateException e) {
			
		}
	}
	
	private Set<LatLon> getTestPath() {
		Set<LatLon> path = new LinkedHashSet<LatLon>();
		Collections.addAll(
			path,
			new LatLon(49.5255556, 5.9405556),
			new LatLon(49.5255556, 5.9405556),
			new LatLon(49.7388889, 6.3450000),
			new LatLon(49.6083333, 6.4058333),
			new LatLon(49.7966667, 6.1555556)
		);
		return path;
	}
	
	@Test
	public void worksWithSetsTest() {
		Chromosome first = new Chromosome(getTestPath(), TEST_PATH_LENGTH);
		Chromosome sameAsFirst = new Chromosome(getTestPath(), TEST_PATH_LENGTH);
		
		Set<LatLon> slightlyDifferentPath = getTestPath();
		slightlyDifferentPath.add(new LatLon(79.5255556, 5.9405556));
		
		Chromosome thirdPath = new Chromosome(slightlyDifferentPath, TEST_PATH_LENGTH + 1);
		
		Set<Chromosome> chromosomes = new LinkedHashSet<Chromosome>();
		Collections.addAll(chromosomes, first, sameAsFirst, thirdPath);
		
		assertEquals(chromosomes.size(), 2);
	}
	
	@Test
	public void randomiseTest() {
		Set<LatLon> path = getTestPath();
		Chromosome first = new Chromosome(path, TEST_PATH_LENGTH);
		Chromosome second = new Chromosome(path, TEST_PATH_LENGTH);
		
		first.randomise(473);
		
		boolean notSame = !first.equals(second);
		boolean samePath = path.equals(getTestPath());
		
		assertTrue(notSame && samePath);
	}
	
	@Test
	public void scoreComputationTest() {
		Set<LatLon> path = getTestPath();
		Chromosome chromosome = new Chromosome(path, TEST_PATH_LENGTH);
		
		double actualScore = chromosome.computeScore();
		
		double expectedScore = 114.23;
		
		assertTrue(Math.abs(actualScore - expectedScore) < 0.005);
	}
	
	@Test
	public void emptyScoreComputionTest() {
		Set<LatLon> path = new LinkedHashSet<>();
		Chromosome chromosome = new Chromosome(path, 0);
		double actualScore = chromosome.computeScore();
		double expectedScore = 0;
		
		assertTrue(actualScore == expectedScore);
	}
	
	@Test
	public void mutateTest() {
		int seed = 5543;
		Chromosome chromosome = new Chromosome(getTestPath(), TEST_PATH_LENGTH);
		Chromosome chromosomeCopy = new Chromosome(getTestPath(), TEST_PATH_LENGTH);
		boolean isEqualPreMutation = chromosome.equals(chromosomeCopy);
		chromosome.mutate(seed);
		boolean notPostMutation = !chromosome.equals(chromosomeCopy);
		
		assertTrue(isEqualPreMutation && notPostMutation);
	}
	
}
