package org.yuan.project.logger.helpers;

import java.io.FilterWriter;
import java.io.Writer;

import org.yuan.project.logger.spi.ErrorHandler;

public class QuietWriter extends FilterWriter {
	
	public QuietWriter(Writer writer, ErrorHandler errorHandler) {
		super(writer);
		setErrorHandler(errorHandler);
	}
	
	@Override
	public void flush() {
		try {
			super.flush();
		} catch(Exception e) {
			errorHandler.error("Failed to flush writer,", e, ErrorHandler.FLUSH_FAILURE);
		}
	}

	@Override
	public void write(String str) {
		if(str != null) {
			try {
				super.write(str);
			} catch(Exception e) {
				errorHandler.error("Failed to write [" + str + "]", e, ErrorHandler.WRITE_FAILURE);
			}
		}
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		if(errorHandler == null) {
			throw new IllegalArgumentException("Attempted to set null ErrorHandler.");
		}
		
		this.errorHandler = errorHandler;
	}

	//----------------------------------------------------------
	//
	//----------------------------------------------------------
	protected ErrorHandler errorHandler;
}
