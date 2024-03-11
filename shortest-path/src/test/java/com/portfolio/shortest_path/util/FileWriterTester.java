package com.portfolio.shortest_path.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.portfolio.shortest_path.Chromosome;
import com.portfolio.shortest_path.PathBuilder;
import com.portfolio.shortest_path.TSPFileWriter;
import com.portfolio.shortest_path.ChromosomeWriter;

public class FileWriterTester {
	
	private String dirName;
	private String fileExtension;
	private PathBuilder pathBuilder = new PathBuilder();
	private ChromosomeWriter writer;
	
	public FileWriterTester(String dirName, String fileExtension, ChromosomeWriter writer) {
		this.dirName = dirName;
		this.fileExtension = fileExtension;
		this.writer = writer;
	}
	
	
	public void writeMakesFile(Chromosome chromosome) {
		String dirPath = pathBuilder.getFilePath(this.dirName);
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		int preNumFiles = 0;
		if (files != null) {
			preNumFiles = files.length;
		}
		try {
			this.writer.write(chromosome);
			files = dir.listFiles();
			this.removeFileFor(dir, chromosome);
			int postNumFiles = files.length;
			assertEquals(preNumFiles + 1, postNumFiles);
		} catch(IOException e) {
			fail(String.format("Didn't write to %s file", this.fileExtension));
		}
		
	}
	
	private void removeFileFor(File dir, Chromosome chromosome) throws IOException {
		String hashString = Integer.toString(chromosome.hashCode());
		String patternString = String.format("(-?\\d+?)_(\\d+?)(?:\\.%s)", this.fileExtension);
		Pattern filePattern = Pattern.compile(patternString);
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

}
