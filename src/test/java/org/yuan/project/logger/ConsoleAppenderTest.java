package org.yuan.project.logger;

import org.junit.Test;
import org.yuan.project.logger.spi.LoggingEvent;

public class ConsoleAppenderTest {

	@Test
	public void test() {
		SimpleLayout layout = new SimpleLayout();
		String target = ConsoleAppender.SYSTEM_ERR;
		ConsoleAppender appender = new ConsoleAppender(layout, target);

		LoggingEvent event = new LoggingEvent(Level.INFO, "test", null, null);
		appender.doAppend(event);
	}
	
}
