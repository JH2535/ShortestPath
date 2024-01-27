package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import main.TSPFileStream;

class TSPFileStreamTest {

	@Test
	void opensValidFile() {
		try {
			TSPFileStream tspFileStream = new TSPFileStream(this.getValidFilePath());
			tspFileStream.close();
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
	}
	
	private String getValidFilePath() {
		String currentDirectory = System.getProperty("user.dir");
		StringBuilder pathBuilder = new StringBuilder(currentDirectory);
		pathBuilder.append("\\src\\data\\lu980.tsp");
		return pathBuilder.toString();
	}
	
	@Test
	void doesNotOpenInvalidFile() {
		assertThrows(FileNotFoundException.class,
				() -> {
					new TSPFileStream(this.getInvalidFilePath());
				}
		);
		
	}
	
	private String getInvalidFilePath() {
		return "NotAPathValidPath";
	}

}
