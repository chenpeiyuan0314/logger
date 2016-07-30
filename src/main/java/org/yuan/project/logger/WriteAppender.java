package org.yuan.project.logger;

import org.apache.log4j.helpers.QuietWriter;
import org.yuan.project.logger.spi.LoggingEvent;

public class WriteAppender extends AbstractAppender {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		
	}

	
	protected boolean flush;
	protected String encoding;
	protected QuietWriter qw;
}
