package org.apache.log4j.demo;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Test;

public class LogMFTest {

	@Test
	public void testLogMF() {
		Logger logger = Logger.getLogger("none");
		logger.setLevel(Level.ALL);
		logger.addAppender(new ConsoleAppender(new SimpleLayout()));
		
		LogMF.debug(logger, "{0} {1}", new Object[]{"one", "two"});
	}
}
