package com.portfolio.shortest_path;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.Test;



public class EnvironmentTest {
	
	@Test
	public void nextGenerationTest() {
		try {
			int seed = 443;
			Environment environment = new Environment(getValidFilePath(), 443);
			int numChildren = 20;
			Set<Chromosome> nextGeneration = environment.getNextGeneration(numChildren, seed);
			
			boolean expectedSize = nextGeneration.size() == numChildren;
			boolean hasParentsWithin = true;
			for(Chromosome parent: environment.getParents()) {
				hasParentsWithin = hasParentsWithin && nextGeneration.contains(parent);
			}
			assertTrue(expectedSize && hasParentsWithin);
		} catch (FileNotFoundException e) {
			fail("Failed to creat envirionment from test file");
		} catch (NullPointerException e) {
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
		return this.constructPathTo("\\src\\main\\java\\com\\portfolio\\data\\lu980.tsp");
	}

}
