package org.yuan.project.logger;

import org.apache.log4j.helpers.LogLog;
import org.yuan.project.logger.spi.ErrorHandler;
import org.yuan.project.logger.spi.Filter;
import org.yuan.project.logger.spi.LoggingEvent;
import org.yuan.project.logger.spi.OptionHandler;

public abstract class AbstractAppender implements Appender, OptionHandler {

	@Override
	public void addFilter(Filter filter) {
		if(headFilter == null) {
			headFilter = tailFilter = filter;
			return;
		}
		
		tailFilter.setNext(filter);
		tailFilter = filter;
	}
	
	@Override
	public Filter getFilter() {
		return headFilter;
	}
	
	@Override
	public void delFilters() {
		Filter node = headFilter;
		while(node != null) {
			Filter temp = node.getNext();
			node.setNext(null);
			node = temp;
		}
		
		headFilter = tailFilter = null;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void setLayout(Layout layout) {
		this.layout = layout;
	}
	
	@Override
	public Layout getLayout() {
		return layout;
	}
	
	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}
	
	@Override
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}
	
	@Override
	synchronized
	public void doAppend(LoggingEvent event) {
		if(closed) {
			LogLog.error("Attempted to append to closed appender named [" + name + "].");
			return;
		}
		
		if(!isAsSevereAsThreshold(event.getLevel())) {
			return;
		}
		
		Filter f = headFilter;
		while(f != null) {
			switch(f.decide(event)) {
			case Filter.DENY: 
				return;
			case Filter.ACCEPT: 
				append(event); 
				return;
			case Filter.NEUTRAL: 
				f = f.getNext();
			}
		}
		
		append(event);
	}
	
	@Override
	public void activateOptions() {}
	
	@Override
	public void finalize() throws Throwable {
		if(this.closed) {
			return;
		}
		
		LogLog.debug("Finalizing appender named [" + name + "].");
		close();
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public boolean isAsSevereAsThreshold(Level level) {
		return ((this.level == null) || level.isGreaterOrEqual(this.level));
	}
	
	//-------------------------------------------------------------
	// 
	//-------------------------------------------------------------
	
	protected abstract void append(LoggingEvent event);
	
	//-------------------------------------------------------------
	// 
	//-------------------------------------------------------------
	private Level level;
	private String name;
	private Layout layout;
	private Filter headFilter;
	private Filter tailFilter;
	private ErrorHandler errorHandler;
	
	protected boolean closed = false;
}
