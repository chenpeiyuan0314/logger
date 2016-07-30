package org.yuan.project.logger;

import org.yuan.project.logger.helpers.AppenderAttachableImpl;
import org.yuan.project.logger.spi.LoggingEvent;

public class Logger {

	protected Logger(String name) {
		this.name = name;
		aai = new AppenderAttachableImpl();
	}
	
	public static Logger getLogger(String name) {
		return LoggerManager.getLogger(name);
	}
	
	public static Logger getRootLogger() {
		return LoggerManager.getRootLogger();
	}
	
	public String getName() {
		return name;
	}
	
	public void addAppender(Appender appender) {
		aai.addAppender(appender);
	}
	
	public void log(Level level, String message) {
		if(getEffectiveLevel().isGreaterOrEqual(level)) {
			return;
		}
		
		for(Logger log = this; log != null; log = log.getParent()) {
			if(log.aai != null) {
				log.aai.doAppenders(new LoggingEvent(level, message, this, null));
			}
		}
	}
	
	public Level getEffectiveLevel() {
		for(Logger log = this; log != null; log = log.parent) {
			if(log.level != null) {
				return log.level;
			}
		}
		return null;
	}
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public Logger getParent() {
		return parent;
	}

	protected void setParent(Logger parent) {
		this.parent = parent;
	}

	//-------------------------------------------------------------
	// 成员属性
	//-------------------------------------------------------------
	private String name;
	private Level level;
	private Logger parent;
	private AppenderAttachableImpl aai;
}
