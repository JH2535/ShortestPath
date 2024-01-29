package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TSPFileReader {

	private Scanner fileScanner;

	public TSPFileReader(String filePath) throws FileNotFoundException {
		this.fileScanner = new Scanner(new File(filePath));
	}

	public Set<LonLat> readLonLats() {
		Set<LonLat> result = new LinkedHashSet<LonLat>();
		Pattern lonLatLinePattern = Pattern.compile("\\d\\s(\\d+\\.\\d+)\\s(\\d+\\.\\d+)");
		while(fileScanner.hasNextLine()) {
			try {
				String line = fileScanner.nextLine();
				Matcher matcher = lonLatLinePattern.matcher(line);
				matcher.find();
				result.add(
					new LonLat(
						Double.valueOf(matcher.group(1)) / 1000f,
						Double.valueOf(matcher.group(2)) / 1000f
					)
				);
			} catch (IllegalStateException e) {
				continue;
			}
		}
		fileScanner.close();
		return result;
	}

}
