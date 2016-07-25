package org.yuan.project.logger.spi;

import org.yuan.project.logger.Level;

public class LoggingEvent {
	
	public LoggingEvent(Level level, String message) {
		this.level = level;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public Level getLevel() {
		return level;
	}
	
	private Level level;
	private String message;
}
