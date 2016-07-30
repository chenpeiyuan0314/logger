package org.yuan.project.logger.spi;

public abstract class Filter implements OptionHandler {

	public static final int DENY = -1;
	public static final int NEUTRAL = 0;
	public static final int ACCEPT = 1;
	
	//---------------------------------------------------
	//
	//---------------------------------------------------
	
	public abstract int decide(LoggingEvent event);
	
	//---------------------------------------------------
	//
	//---------------------------------------------------
	public void setNext(Filter next) {
		this.next = next;
	}
	
	public Filter getNext() {
		return next;
	}
	
	//---------------------------------------------------
	// 
	//---------------------------------------------------
	private Filter next;
}
