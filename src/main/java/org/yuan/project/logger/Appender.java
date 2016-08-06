package org.yuan.project.logger;

import org.yuan.project.logger.spi.ErrorHandler;
import org.yuan.project.logger.spi.Filter;
import org.yuan.project.logger.spi.LoggingEvent;

public interface Appender {
	
	/**
	 * add filter
	 * @param Filter
	 */
	void addFilter(Filter Filter);
	
	/**
	 * get head filter
	 * @return
	 */
	Filter getFilter();
	
	/**
	 * clear all filters
	 */
	void delFilters();
	
	/**
	 *	close resource
	 */
	void close();
	
	/**
	 * get appender name
	 * @return
	 */
	String getName();
	
	/**
	 * set appender name
	 */
	void setName(String name);
	
	/**
	 * set appender layout
	 * @param layout
	 */
	void setLayout(Layout layout);
	
	/**
	 * get appender layout
	 * @return
	 */
	Layout getLayout();
	
	/**
	 * set errorHandler
	 * @param errorHandler
	 */
	void setErrorHandler(ErrorHandler errorHandler);

	/**
	 * get errorHandler
	 * @return
	 */
	ErrorHandler getErrorHandler();
	
	/**
	 * append log
	 * @param event
	 */
	public abstract void doAppend(LoggingEvent event);
	
	/**
	 * whether need layout
	 * @return
	 */
	boolean requiresLayout();
}
