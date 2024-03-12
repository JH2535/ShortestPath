package com.portfolio.shortest_path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.portfolio.shortest_path.util.FileWriterTester;

public class ChromosomeGpxIOTest {
	
	private final int TEST_PATH_LENGTH = 4;
	
	private PathBuilder pathBuilder = new PathBuilder();
	
	@Test
	public void accurateReadWrite() {
		String gpxFileName = "test_file.gpx";
		Chromosome chromosome = this.getTestChromosome();
		ChromosomeGpxIO gpx = new ChromosomeGpxIO();
		try {
			gpx.write(chromosome, gpxFileName);
		} catch (IOException e) {
			fail("Couldn't write to gpx file");
		}
		try {
			List<LatLon> writeReadPath = gpx.readFile(gpxFileName);
			List<LatLon> expectedPath = chromosome.getPath();
			StringBuilder filePath = new StringBuilder(pathBuilder.getFilePath("gpx_out/"));
			filePath.append(gpxFileName);
			File file = new File(filePath.toString());
			file.delete();
			assertEquals(expectedPath, writeReadPath);
		} catch (IOException e) {
			fail("Failed to read gpx file");
		}
		
	}
	
	@Test
	public void writeGpxMakesFileTest() {
		new FileWriterTester("gpx_out", "gpx", new ChromosomeGpxIO())
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
