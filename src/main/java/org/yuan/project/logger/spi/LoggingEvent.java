package org.yuan.project.logger.spi;

import org.yuan.project.logger.Level;
import org.yuan.project.logger.Logger;

public class LoggingEvent {
	
	public LoggingEvent(Level level, String message, Logger logger, Throwable throwable) {
		this.level = level;
		this.message = message;
		this.logger = logger;
		this.timestamp = System.currentTimeMillis();
		if(throwable != null) {
			this.throwbleInfo = new ThrowableInformation(throwable, logger);
		}
	}

	public String getMessage() {
		return message;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public String getThreadName() {
		return threadName;
	}

	public ThrowableInformation getThrowbleInfo() {
		return throwbleInfo;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public LocationInfo getLocationInfo() {
		return locationInfo;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public static long getStartTime() {
		return startTime;
	}

	private Level level;
	private String message;
	private Logger logger;
	private String threadName;
	private final long timestamp;
	private LocationInfo locationInfo;
	private ThrowableInformation throwbleInfo;
	
	private static long startTime = System.currentTimeMillis();
}
