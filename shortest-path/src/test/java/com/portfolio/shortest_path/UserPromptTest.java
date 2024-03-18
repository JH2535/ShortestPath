package com.portfolio.shortest_path;


import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class UserPromptTest {
	
	@Test
	public void userInfoTest() {
		UserPrompt userPrompt = new UserPrompt();
		String outputString = userPrompt.getUserInfo();
		
		boolean hasStartBlurb = hasStartBlurb(outputString);
		
		int numCommands = 6;
		boolean expectedNumCommands = hasCommands(outputString, numCommands);
		
		
		int numOptions = 6;
		boolean expectedNumOptions = hasOptions(outputString, numOptions);
		
		assertTrue(hasStartBlurb && expectedNumCommands && expectedNumOptions);
	}
	
	private boolean hasOptions(String outputString, int numOptions) {
		Pattern optionsPat = Pattern.compile("Options((?:\\w|\\W)+)");
		Matcher optionsMatch = optionsPat.matcher(outputString);
		optionsMatch.find();
		boolean expectedNumOptions = false;
		try {
			String[] options = optionsMatch.group(1).replace("\n\n", "\n").trim().split("\n");
			expectedNumOptions = options.length == numOptions;
		} catch(IndexOutOfBoundsException e) {
			fail(e.getMessage());
		}
		return expectedNumOptions;
	}

	private boolean hasCommands(String outputString, int numCommands) {
		Pattern commandsPat = Pattern.compile("Commands\\n((?: [a-z]{2,20} ?(?:\\w|\\W)*?\\n)+)(?=Options)");
		Matcher commandsMatch = commandsPat.matcher(outputString);
		commandsMatch.find();
		boolean expectedNumCommands = false;
		try {
			String[] commands = commandsMatch.group(1).split("\n");
			expectedNumCommands = commands.length == numCommands;	
		} catch(IndexOutOfBoundsException e) {
			fail(e.getMessage());
		}
		return expectedNumCommands;
	}

	private boolean hasStartBlurb(String outputString) {
		Pattern startBlurbPat = Pattern.compile("((?:\\w|\\W)+)(?=Commands)");
		Matcher startBlurbMatch = startBlurbPat.matcher(outputString);
		startBlurbMatch.find();
		boolean foundStartBlurb = false;
		try {
			String startBlurb = startBlurbMatch.group(0);
			foundStartBlurb = startBlurb.length() > 20 &&  startBlurb.length() <= 400;
		} catch(IndexOutOfBoundsException e) {
			fail(e.getMessage());
		}
		return foundStartBlurb;
	}
}
