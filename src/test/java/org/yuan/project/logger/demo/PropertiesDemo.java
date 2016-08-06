package org.yuan.project.logger.demo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PropertiesDemo {

	public static void main(String[] args) throws Exception {
		System.setProperty("log4j.debug", "true");
		
		String file = "log4j.properties";
		PropertyConfigurator.configure(file);
		
		Logger root = Logger.getRootLogger();
		root.info("info message");
	}
	
}
