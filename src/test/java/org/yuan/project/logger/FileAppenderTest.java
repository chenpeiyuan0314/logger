package org.yuan.project.logger;

import org.junit.Test;

public class FileAppenderTest {

	@Test
	public void test() throws Exception {
		Logger root = Logger.getRootLogger();
		FileAppender appender = new FileAppender(new SimpleLayout(), "E:/log.log", true, false, 1024*8);
		root.addAppender(appender);
		
		root.log(Level.INFO, "This is a test.");
	}
}
