package org.yuan.project.logger.demo;

import org.junit.Assert;
import org.junit.Test;
import org.yuan.project.logger.Appender;
import org.yuan.project.logger.ConsoleAppender;
import org.yuan.project.logger.Layout;
import org.yuan.project.logger.Level;
import org.yuan.project.logger.Logger;
import org.yuan.project.logger.SimpleLayout;

public class Demo {
	
	//@Test
	public void demo01() {
		Logger log = Logger.getLogger("demo");
		Layout layout = new SimpleLayout();
		Appender appender = new ConsoleAppender();
		appender.setLayout(layout);
		appender.setLevel(Level.ALL);
		log.addAppender(appender);
		log.setLevel(Level.ALL);
		
		log.log(Level.INFO, "This is a test message");
	}
	
	@Test
	public void demoHierarchy() {
		Logger root = Logger.getRootLogger();
		Logger xyz = Logger.getLogger("x.y.z");
		Assert.assertEquals(root, xyz.getParent());
		
		Logger xy = Logger.getLogger("x.y");
		Assert.assertEquals(root, xy.getParent());
		Assert.assertEquals(xy, xyz.getParent());
		
		Logger x = Logger.getLogger("x");
		Assert.assertEquals(root, x.getParent());
		Assert.assertEquals(x, xy.getParent());
		Assert.assertEquals(xy, xyz.getParent());
	}
	
}
