package com.portfolio.shortest_path;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TSPFileWriter extends ChromosomeWriter {
	
	private PathBuilder pathBuilder = new PathBuilder();
	private String dir = this.pathBuilder.getFilePath("tsp_out/");

	public TSPFileWriter(String dir) {
		this.dir = dir;
		this.fileExtension = "tsp";
	}
	
	public TSPFileWriter() {
		this.fileExtension = "tsp";
	}

	@Override
	public void write(Chromosome chromosome, String fileName) throws IOException {
		StringBuilder filePath = new StringBuilder(dir);
		filePath.append(fileName);
		
		try {
			FileWriter fileWriter = new FileWriter(filePath.toString());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(this.buildFileString(chromosome, fileName));
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private String buildFileString(Chromosome chromosome, String fileName) {
		List<LatLon> path = chromosome.getPath();
		StringBuilder contents = new StringBuilder();
		this.buildHeader(contents, path, fileName);
		this.writePath(contents, path);
		return contents.toString();
	}
	
	private void buildHeader(StringBuilder contents, List<LatLon> path, String fileName) {
		String name = fileName.substring(0, fileName.length() - 4);
		contents.append(String.format("NAME : %s\n", name));
		contents.append("COMMENT : Generated from shortest-path program\n");
		contents.append("TYPE : TSP\n");
		contents.append(String.format("DIMENSION : %d\n", path.size()));
		contents.append("EDGE_WEIGHT_TYPE : EUC_2D\n");
	}

	private void writePath(StringBuilder contents, List<LatLon> path) {
		contents.append("NODE_COORD_SECTION\n");
		for(int i = 0; i < path.size(); i++) {
			double lat = path.get(i).getLat()* 1000;
			double lon = path.get(i).getLon()* 1000;
			contents.append(String.format("%d %f %f\n", i + 1, lat, lon));
		}
		contents.append("EOF");
	}
}
