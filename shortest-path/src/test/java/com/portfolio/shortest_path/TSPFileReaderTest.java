package com.portfolio.shortest_path;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.portfolio.shortest_path.util.PathBuilder;


class TSPFileReaderTest {
	
	private PathBuilder pathBuilder = new PathBuilder();

	@Test
	public void opensValidFileTest() {
		try {
			new TSPFileReader(pathBuilder.getFilePath("lu980.tsp"));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void invalidFilePathTest() {
		assertThrows(FileNotFoundException.class,
			() -> {
				new TSPFileReader(this.getInvalidFilePath());
			}
		);

	}

	private String getInvalidFilePath() {
		return "NotAPathValidPath";
	}

	@Test
	public void readLatLonsTest() {
		Set<LatLon> expectedResults = new LinkedHashSet<LatLon>();
		Collections.addAll(
			expectedResults,
			new LatLon(49.5255556, 5.9405556),
			new LatLon(49.5255556, 5.9405556),
			new LatLon(49.7388889, 6.3450000),
			new LatLon(49.6083333, 6.4058333),
			new LatLon(49.7966667, 6.1555556)
		);
		try {
			TSPFileReader tspFileStream = new TSPFileReader(pathBuilder.getFilePath("test.tsp"));
			Set<LatLon> actualResults = tspFileStream.readLonLats();

			assertEquals(expectedResults, actualResults, "Not extracting same lon lats as expected from test.tsp");
		} catch(Exception e) {
			fail(e.getMessage());
		}


	}
}
