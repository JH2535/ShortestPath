package com.portfolio.shortest_path;

import java.io.IOException;

public abstract class ChromosomeWriter {
	
	protected String fileExtension;
	
	public abstract void write(Chromosome chromosome, String fileName) throws IOException;
	
	public void write(Chromosome chromosome) throws IOException {
		String hashCode = Integer.toString(chromosome.hashCode());
		long time = System.currentTimeMillis();
		String timeString = Long.toString(time);
		StringBuilder fileName = new StringBuilder();
		fileName.append(hashCode);
		fileName.append("_");
		fileName.append(timeString);
		fileName.append(String.format(".%s", this.fileExtension));
		this.write(chromosome, fileName.toString());
	}
	
	
	

}
