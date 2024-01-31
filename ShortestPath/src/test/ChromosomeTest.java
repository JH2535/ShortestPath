package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import main.Chromosome;
import main.LatLon;

public class ChromosomeTest {
	
	private final int TEST_PATH_LENGTH = 4;

	@Test
	public void failsToCreateForInconsistentArguements() {
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
	public void worksWithSets() {
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
	public void testRandomise() {
		Set<LatLon> path = getTestPath();
		Chromosome first = new Chromosome(path, TEST_PATH_LENGTH);
		Chromosome second = new Chromosome(path, TEST_PATH_LENGTH);
		
		first.randomise(473);
		
		boolean notSame = !first.equals(second);
		boolean samePath = path.equals(getTestPath());
		
		assertTrue(notSame && samePath);
	}
	
}