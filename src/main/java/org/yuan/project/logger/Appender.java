package org.yuan.project.logger;

import org.yuan.project.logger.spi.LoggingEvent;

public abstract class Appender {
	
	public Appender() {
		
	}
	
	public Appender(Level level, String name, Layout layout) {
		this.level = level;
		this.name = name;
		this.layout = layout;
	}

	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Layout getLayout() {
		return layout;
	}
	
	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public abstract void doAppend(LoggingEvent event);
	
	private Level level;
	private String name;
	private Layout layout;
}
