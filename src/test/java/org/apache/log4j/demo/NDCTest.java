package org.apache.log4j.demo;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

public class NDCTest {

	@Test
	public void testNDC() {
		Logger logger = Logger.getLogger("none");
		logger.setLevel(Level.ALL);
		logger.addAppender(new ConsoleAppender(new PatternLayout("%c [%x] - %m%n")));
		
		NDC.push("one");
		NDC.push("two");
		NDC.push("three");
		
		logger.info("ndc test");
	}
}
