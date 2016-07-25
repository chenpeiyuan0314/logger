package org.yuan.project.logger;

import org.yuan.project.logger.spi.LoggingEvent;

public abstract class Layout {

	public static final String LINE_SEP = System.getProperty("line.separator");
	
	public abstract String format(LoggingEvent event);
	
}
