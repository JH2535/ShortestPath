package com.portfolio.shortest_path;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.portfolio.shortest_path.util.FileWriterTester;

public class TSPFileWriterTest {
	
	private final int TEST_PATH_LENGTH = 4;
	
	@Test
	public void writeTspMakesFileTest() {
		new FileWriterTester("tsp_out", "tsp", new TSPFileWriter())
			.writeMakesFile(this.getTestChromosome());
	}

	private Chromosome getTestChromosome() {
		Set<LatLon> path = new LinkedHashSet<LatLon>();
		Collections.addAll(
			path,
			new LatLon(49.5255556, 5.9405556),
			new LatLon(49.5255556, 5.9405556),
			new LatLon(49.7388889, 6.3450000),
			new LatLon(49.6083333, 6.4058333),
			new LatLon(49.7966667, 6.1555556)
		);
		return new Chromosome(path, TEST_PATH_LENGTH);
	}
}
