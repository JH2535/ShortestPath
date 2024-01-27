package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TSPFileStream extends FileInputStream {

	public TSPFileStream(String filePath) throws FileNotFoundException {
		super(filePath);
	}

}
