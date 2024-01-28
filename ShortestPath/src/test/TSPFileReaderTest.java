package test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import main.LonLat;
import main.TSPFileReader;

class TSPFileReaderTest {

	@Test
	void opensValidFileTest() {
		try {
			TSPFileReader tspFileStream = new TSPFileReader(this.getValidFilePath());
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

	private String constructPathTo(String relativePath) {
		String currentDirectory = System.getProperty("user.dir");
		StringBuilder pathBuilder = new StringBuilder(currentDirectory);
		pathBuilder.append(relativePath);
		return pathBuilder.toString();
	}

	private String getValidFilePath() {
		return this.constructPathTo("\\src\\data\\lu980.tsp");
	}

	@Test
	void doesNotOpenInvalidFileTest() {
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
	void readLonLatsTest() {
		ArrayList<LonLat> expectedResults = new ArrayList<>();
		Collections.addAll(
			expectedResults,
			new LonLat(49.5255556, 5.9405556),
			new LonLat(49.5255556, 5.9405556),
			new LonLat(49.7388889, 6.3450000),
			new LonLat(49.6083333, 6.4058333),
			new LonLat(49.7966667, 6.1555556)
		);
		try {
			TSPFileReader tspFileStream = new TSPFileReader(this.getTestPath());
			ArrayList<LonLat> actualResults = tspFileStream.readLonLats();

			assertEquals(expectedResults, actualResults, "Not extracting same lon lats as expected from test.tsp");
		} catch(Exception e) {
			fail(e.getMessage());
		}


	}

	private String getTestPath() {
		return this.constructPathTo("\\src\\data\\test.tsp");
	}

}
