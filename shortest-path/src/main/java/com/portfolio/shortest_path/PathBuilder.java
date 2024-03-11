package com.portfolio.shortest_path;

public class PathBuilder {
	
	private String absoluteLocation = "/src/main/java/com/portfolio/data/";
	
	private void prependUserDir(StringBuilder relativePath) {
		String currentDirectory = System.getProperty("user.dir");
		relativePath.insert(0, currentDirectory);
	}
	
	private StringBuilder getFilePathBuilder(String fileName) {
		StringBuilder pathToFile = new StringBuilder(this.absoluteLocation);
		pathToFile.append(fileName);
		this.prependUserDir(pathToFile);
		return pathToFile;
	}

	public String getFilePath(String fileName) {
		return this.getFilePathBuilder(fileName).toString();
	}
}
