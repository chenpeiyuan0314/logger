package org.apache.log4j.demo;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

public class MDCTest {

	@Test
	public void testMDC() {
		Logger logger = Logger.getLogger("none");
		logger.addAppender(new ConsoleAppender(new PatternLayout("%c [%X{key1}] - %m%n")));
		logger.setLevel(Level.ALL);
		logger.setAdditivity(true);
		
		MDC.put("key1", "one");
		MDC.put("key2", "two");
		MDC.put("key3", "three");
		
		logger.info("test ndc");
	}
}
