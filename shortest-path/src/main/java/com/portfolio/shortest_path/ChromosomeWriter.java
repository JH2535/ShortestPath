package com.portfolio.shortest_path;

import java.io.IOException;

public interface ChromosomeWriter {
	
	public void write(Chromosome chromosome) throws IOException;
	public void write(Chromosome chromosome, String fileName) throws IOException;

}
