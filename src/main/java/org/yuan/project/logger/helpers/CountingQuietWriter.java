package org.yuan.project.logger.helpers;

import java.io.Writer;

import org.yuan.project.logger.spi.ErrorHandler;

public class CountingQuietWriter extends QuietWriter {

	public CountingQuietWriter(Writer writer, ErrorHandler errorHandler) {
		super(writer, errorHandler);
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public void write(String str) {
		try {
			out.write(str);
			count += str.length();
		} catch(Exception e) {
			errorHandler.error("Write failure.", e, ErrorHandler.WRITE_FAILURE);
		}
	}

	//------------------------------------------------------
	//
	//------------------------------------------------------
	protected long count;
}
