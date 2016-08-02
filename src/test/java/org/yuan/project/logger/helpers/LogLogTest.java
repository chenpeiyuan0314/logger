package org.yuan.project.logger.helpers;

public class LogLogTest {

	public static void main(String[] args) {
		//System.setProperty("log4j.quiet.mode", "true");
		//System.setProperty("log4j.debug.mode", "false");
		
		LogLog.debug("debug");
		LogLog.warn("warn");
		LogLog.error("error");
	}
}
