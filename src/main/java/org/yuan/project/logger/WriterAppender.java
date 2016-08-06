package org.yuan.project.logger;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.yuan.project.logger.helpers.LogLog;
import org.yuan.project.logger.helpers.QuietWriter;
import org.yuan.project.logger.spi.LoggingEvent;

public class WriterAppender extends AbstractAppender {
	
	public WriterAppender() {}
	
	public WriterAppender(Layout layout, OutputStream os) {
		this(layout, new OutputStreamWriter(os));
	}

	public WriterAppender(Layout layout, Writer writer) {
		this.setLayout(layout);
		this.setWriter(writer);
	}

	@Override
	public synchronized void close() {
		if(this.closed) {
			return;
		}
		
		this.closed = true;
		reset();
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		if(!checkEntryConditions()) {
			return;
		}
		
		subAppend(event);
	}
	
	protected boolean checkEntryConditions() {
		if(this.closed) {
			LogLog.warn("Not allowed to write to a closed appender.");
			return false;
		}
		
		if(this.qw == null) {
			this.getErrorHandler();
			return false;
		}
		
		if(this.getLayout() == null) {
			this.getErrorHandler();
			return false;
		}
		
		return true;
	}
	
	protected void closeWriter() {
		if(qw != null) {
			try {
				qw.close();
			} catch(IOException e) {
				if(e instanceof InterruptedIOException) {
					Thread.currentThread().interrupt();
				}
				LogLog.error("Could not close " + qw, e);
			}
		}
	}
	
	public boolean isFlush() {
		return flush;
	}

	public void setFlush(boolean flush) {
		this.flush = flush;
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public synchronized void setWriter(Writer writer) {
		reset();
		this.qw = new QuietWriter(writer, getErrorHandler());
	}

	protected void reset() {
		closeWriter();
		this.qw = null;
	}

	protected boolean shouldFlush(final LoggingEvent event) {
		return flush;
	}
	
	protected void subAppend(LoggingEvent event) {
		this.qw.write(this.getLayout().format(event));
		
		if(shouldFlush(event)) {
			this.qw.flush();
		}
	}
	
	protected OutputStreamWriter createWriter(OutputStream os) {
		OutputStreamWriter retval = null;
		
		String enc = getEncoding();
		if(enc != null) {
			try {
				retval = new OutputStreamWriter(os, enc);
			} catch(IOException e) {
				if(e instanceof InterruptedIOException) {
					Thread.currentThread().interrupt();
				}
				
				LogLog.warn("Error initializing output writer.");
				LogLog.warn("Unsupported encoding?");
			}
		}
		
		if(retval == null) {
			retval = new OutputStreamWriter(os);
		}
		
		return retval;
	}
	
	//-----------------------------------------------------
	//
	//-----------------------------------------------------
	protected boolean flush = true;
	protected String encoding;
	protected QuietWriter qw;
}
