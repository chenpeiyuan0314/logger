package org.yuan.project.logger.spi;

import org.yuan.project.logger.Appender;
import org.yuan.project.logger.Logger;

public interface ErrorHandler {

	void setLogger(Logger logger);

	void error(String message);

	void error(String message, Exception e, int errorCode);

	void error(String message, Exception e, int errorCode, LoggingEvent event);

	void setAppender(Appender appender);

	void setBackupAppender(Appender appender);

	// ------------------------------------------------------
	//
	// ------------------------------------------------------
	int GENERIC_FAILURE = 0;
	int WRITE_FAILURE = 1;
	int FLUSH_FAILURE = 2;
	int CLOSE_FAILURE = 3;
	int FILE_OPEN_FAILURE = 4;
	int MISSING_LAYOUT = 5;
	int ADDRESS_PARSE_FAILURE = 6;
}
