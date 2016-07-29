package org.yuan.project.logger.spi;

import org.yuan.project.logger.Logger;

public class ThrowableInformation {
	
	public ThrowableInformation(Throwable throwable, Logger logger) {
		this.logger = logger;
		this.throwable = throwable;
	}
	
	private Logger logger;
	private Throwable throwable;
}
