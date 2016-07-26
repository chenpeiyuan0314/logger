package org.yuan.project.logger.spi;

import org.yuan.project.logger.Logger;

public interface LoggerRepository {

	Logger getRootLogger();
	
	Logger getLogger(String name);
}
