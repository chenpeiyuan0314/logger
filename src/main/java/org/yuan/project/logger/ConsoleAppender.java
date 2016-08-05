package org.yuan.project.logger;

import org.yuan.project.logger.spi.LoggingEvent;

public class ConsoleAppender extends AbstractAppender {
	
	public ConsoleAppender() {
		
	}

	public ConsoleAppender(Level level, String name, Layout layout) {
		super(level, name, layout);
	}

	@Override
	public void doAppend(LoggingEvent event) {
		if(getLevel().isGreaterOrEqual(event.getLevel())) {
			return;
		}
		
		System.out.print(this.getLayout().format(event));
	}

}
