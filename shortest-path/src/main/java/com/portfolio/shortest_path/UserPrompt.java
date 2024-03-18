package com.portfolio.shortest_path;

public class UserPrompt {
	
	public String getUserInfo() {
		return this.startBlurb() +
				this.commandList() +
				this.optionsList();
	}
	
	private String startBlurb() {
		return """
			Please input a file using the generate command. Afterwards you can list the latest
			generation using the list command, and see the path by using the print command.
			
			You can then choose to output the path to a gpx file, and save the current best
			path to a tps file for further processing in the future.
			
			""";
	}
	
	private String commandList() {
		return """
			Commands
			 generate [-f tsp_file] [-h] -n int
			 list
			 print [-i int]
			 write
			
			""";
	}
	
	private String optionsList() {
		return """
			Options
			 -f Location of the tsp file
			 
			 -h Headless mode, does not print out computation (increases performance)
			 
			 -n Number of generations (more generations increases score space exploration
			 at the expense of computation time)
			 
			 -i Index of a chromosome to display
			""";
	}
}
