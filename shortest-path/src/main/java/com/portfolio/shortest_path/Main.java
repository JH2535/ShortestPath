package com.portfolio.shortest_path;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		String currentDirectory = System.getProperty("user.dir");
		StringBuilder pathBuilder = new StringBuilder(currentDirectory);
		pathBuilder.append("/src/main/java/com/portfolio/data/tsp_out/lu980.tsp");
		String filePath = pathBuilder.toString();
		
		try {
			Environment environent = new Environment(filePath);
			Set<Chromosome> generation = environent.getNextGeneration(100);
			double bestScore = Double.MAX_VALUE;
			Chromosome best = new Chromosome(new HashSet<>(), 0);
			for(int i = 0; i < 100; i++) {
				for(Chromosome current: generation) {
					if(current.computeScore() < bestScore) {
						best = current;
						bestScore = current.computeScore();
					}
				}
				System.out.println("Best score is: " + best.computeScore());
				generation = environent.getNextGeneration(100);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
