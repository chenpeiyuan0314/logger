package org.yuan.project.logger;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.helpers.QuietWriter;
import org.yuan.project.logger.spi.LoggingEvent;

public class WriterAppender extends AbstractAppender {
	
	public WriterAppender() {
		flush = true;
	}
	
	public WriterAppender(Layout layout, OutputStream os) {
		this(layout, new OutputStreamWriter(os));
	}
	
	public WriterAppender(Layout layout, Writer writer) {
		this.layout = layout;
		this.setWriter(writer);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		
	}
	
	protected boolean flush;
	protected String encoding;
	protected QuietWriter qw;
}
