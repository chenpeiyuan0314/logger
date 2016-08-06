package org.yuan.project.logger;

import java.util.Iterator;

import org.apache.log4j.spi.LoggerRepository;
import org.yuan.project.logger.helpers.AppenderAttachableImpl;
import org.yuan.project.logger.spi.AppenderAttachable;
import org.yuan.project.logger.spi.LoggingEvent;

public class Logger implements AppenderAttachable{

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
	
	@Override
	public void delAppender(String name) {
		aai.delAppender(name);
	}

	@Override
	public void delAppender(Appender appender) {
		aai.delAppender(appender);
	}

	@Override
	public void delAllAppenders() {
		aai.delAllAppenders();
	}

	@Override
	public boolean hasAppender(Appender appender) {
		return aai.hasAppender(appender);
	}

	@Override
	public Appender getAppender(String name) {
		return aai.getAppender(name);
	}

	@Override
	public Iterator<Appender> getAllAppenders() {
		return aai.getAllAppenders();
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

	public LoggerRepository getLoggerRepository() {
		return hierarchy;
	}

	public void setLoggerRepository(LoggerRepository hierarchy) {
		this.hierarchy = hierarchy;
	}

	public void closeNestedAppenders() {
		Iterator<Appender> iter = getAllAppenders();
		while(iter.hasNext()) {
			Appender appe = iter.next();
			appe.close();
		}
	}
	
	//-------------------------------------------------------------
	// 成员属性
	//-------------------------------------------------------------
	private String name;
	private Level level;
	private Logger parent;
	private AppenderAttachableImpl aai;
	private LoggerRepository hierarchy;
}
