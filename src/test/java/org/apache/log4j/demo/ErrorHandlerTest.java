package org.apache.log4j.demo;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.helpers.OnlyOnceErrorHandler;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.varia.FallbackErrorHandler;
import org.junit.Test;


public class ErrorHandlerTest {
	
	//@Test
	public void testErrorHandler() {
		Writer writer = new Writer(){
			@Override
			public void write(char[] cbuf, int off, int len) throws IOException {
				throw new IOException("test error handler");
			}
			@Override
			public void flush() throws IOException {
			}
			@Override
			public void close() throws IOException {
			}
		};
		
		Logger root = Logger.getRootLogger();
		Appender appender = new WriterAppender(null, writer);
		appender.setLayout(new SimpleLayout());
		appender.doAppend(new LoggingEvent(root.getClass().getName(), root, Level.INFO, "test message", null));
		appender.doAppend(new LoggingEvent(root.getClass().getName(), root, Level.INFO, "test message2", null));
	}
	
	@Test
	public void testErrorHandler2() {
		//System.setProperty("log4j.debug", "true");
		Writer writer = new Writer(){
			@Override
			public void write(char[] cbuf, int off, int len) throws IOException {
				throw new IOException("test error handler");
			}
			@Override
			public void flush() throws IOException {
			}
			@Override
			public void close() throws IOException {
			}
		};
		
		Logger root = Logger.getRootLogger();
		Appender appender = new WriterAppender(null, writer);
		ErrorHandler errorHandler = new FallbackErrorHandler();
		errorHandler.setLogger(root);
		errorHandler.setAppender(appender);
		errorHandler.setBackupAppender(new ConsoleAppender(new SimpleLayout()));
		appender.setLayout(new SimpleLayout());
		//appender.setErrorHandler(errorHandler);
		appender.setErrorHandler(new OnlyOnceErrorHandler());
		
		root.addAppender(appender);
		//root.addAppender(new ConsoleAppender(new SimpleLayout()));
		root.info("this is a test");
		root.info("this is a test");
		/*
		appender.doAppend(new LoggingEvent(root.getClass().getName(), root, Level.INFO, "test message", null));
		appender.doAppend(new LoggingEvent(root.getClass().getName(), root, Level.INFO, "test message2", null));
		*/
	}
}
