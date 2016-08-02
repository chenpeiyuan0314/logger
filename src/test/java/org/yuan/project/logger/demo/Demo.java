package org.yuan.project.logger.demo;

import java.io.FileOutputStream;

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
		//appender.setLevel(Level.ALL);
		//log.addAppender(appender);
		//log.setLevel(Level.ALL);
		
		log.log(Level.INFO, "This is a test message");
	}
	
	//@Test
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
	
	//@Test
	public void demoTag() {
		int i = 0;
		label:
		while(i < 10) {
			switch(i) {
			case 1:
			case 2:
			case 3:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				System.out.println(i);
				break;
			case 4:
				break label;
			}
			i++;
		}
	}
	
	@Test
	public void demoClose() throws Exception {
		FileOutputStream fos = new FileOutputStream("E:/Time.txt");
		fos.close();
		fos.close();
	}
	
}