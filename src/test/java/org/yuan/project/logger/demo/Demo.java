package org.yuan.project.logger.demo;

import org.junit.Test;
import org.yuan.project.logger.Appender;
import org.yuan.project.logger.ConsoleAppender;
import org.yuan.project.logger.Layout;
import org.yuan.project.logger.Level;
import org.yuan.project.logger.Logger;
import org.yuan.project.logger.SimpleLayout;

public class Demo {
	
	@Test
	public void demo01() {
		Logger log = Logger.getLogger("demo");
		Layout layout = new SimpleLayout();
		Appender appender = new ConsoleAppender();
		appender.setLayout(layout);
		appender.setLevel(Level.ALL);
		log.addAppender(appender);
		log.setLevel(Level.OFF);
		
		log.log(Level.INFO, "This is a test message");
	}
	
}
