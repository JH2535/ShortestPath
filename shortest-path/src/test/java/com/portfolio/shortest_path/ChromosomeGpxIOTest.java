package com.portfolio.shortest_path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class ChromosomeGpxIOTest {
	
	private final int TEST_PATH_LENGTH = 4;
	
	private PathBuilder pathBuilder = new PathBuilder();
	
	@Test
	public void writeGpxMakesFileTest() {
		String dirPath = pathBuilder.getFilePath("gpx_out");
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		int preNumFiles = 0;
		if (files != null) {
			preNumFiles = files.length;
		}
		ChromosomeGpxIO gpxIo = new ChromosomeGpxIO();
		try {
			gpxIo.writeGpx(this.getTestChromosome());
			files = dir.listFiles();
			this.removeFileFor(dir, this.getTestChromosome());
			int postNumFiles = files.length;
			assertEquals(preNumFiles + 1, postNumFiles);
		} catch(IOException e) {
			fail("Didn't write to gpx file");
		}
		
	}
	
	private void removeFileFor(File dir, Chromosome chromosome) throws IOException {
		String hashString = Integer.toString(chromosome.hashCode());
		Pattern filePattern = Pattern.compile("(-?\\d+?)_(\\d+?)(?:\\.gpx)");
		long time = System.currentTimeMillis();
		File[] filesInDir = dir.listFiles();
		int indexToDelete = -1;
		for(int i = 0; i < filesInDir.length; i++) {
			String fileName = filesInDir[i].getName();
			Matcher match = filePattern.matcher(fileName);
			boolean isGenerated = match.find();
			if(isGenerated) {
				String currentHashString = match.group(1);
				String currentTimeStampString = match.group(2);
				boolean matchesHash = hashString.equals(currentHashString);
				long currentTimeStamp = Long.parseLong(currentTimeStampString);
				boolean inRange = currentTimeStamp > time - 60000;
				indexToDelete = (matchesHash && inRange)? i : -1;
				if (indexToDelete > -1) {
					break;
				}
			}
		}
		if (indexToDelete > -1) {
			filesInDir[indexToDelete].delete();
			return;
		}
		throw new IOException("Could not find test file");
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
