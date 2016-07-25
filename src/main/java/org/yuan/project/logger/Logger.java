package org.yuan.project.logger;

import java.util.ArrayList;
import java.util.List;

import org.yuan.project.logger.spi.LoggingEvent;

public class Logger {

	protected Logger(String name) {
		this.name = name;
		appenderList = new ArrayList<Appender>();
	}
	
	public static Logger getLogger(String name) {
		return new Logger(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void addAppender(Appender appender) {
		appenderList.add(appender);
	}
	
	public void log(Level level, String message) {
		if(this.level.isGreaterOrEqual(level)) {
			return;
		}
		
		for(Appender appender : appenderList) {
			appender.doAppend(new LoggingEvent(level, message));
		}
	}
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	private String name;
	private Level level;
	private List<Appender> appenderList;
}
