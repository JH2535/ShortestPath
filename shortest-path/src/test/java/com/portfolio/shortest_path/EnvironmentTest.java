package com.portfolio.shortest_path;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.portfolio.shortest_path.util.PathBuilder;

public class EnvironmentTest {
	
	private PathBuilder pathBuilder = new PathBuilder();
	
	@Test
	public void nextGenerationTest() {
		try {
			int seed = 443;
			Environment environment = new Environment(pathBuilder.getFilePath("lu980.tsp"), 443);
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
}
