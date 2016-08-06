package org.yuan.project.logger.helpers;

import java.io.PrintStream;

public class LogLog {
	
	private static boolean quietMode = false;
	private static boolean debugMode = true;
	
	private static final String PREFIX_DEBUG = "log4j:DEUBG ";
	private static final String PREFIX_ERROR = "log4j:ERROR ";
	private static final String PREFIX_WARN  = "log4j:WARN ";
	
	private static final String QUIET_MODE = "log4j.quiet.mode";
	private static final String DEBUG_MODE = "log4j.debug.mode";
	
	static {
		String mode = null;
		
		// 设置静默模式
		mode = System.getProperty(QUIET_MODE);
		if("true".equalsIgnoreCase(mode)) {
			quietMode = true;
		}
		
		// 设置调试模式
		mode = System.getProperty(DEBUG_MODE);
		if("false".equalsIgnoreCase(mode)) {
			debugMode = false;
		}
	}
	
	public static void debug(String msg) {
		debug(msg, null);
	}
	
	public static void debug(String msg, Throwable th) {
		if(!debugMode) {
			return;
		}
		
		print(PREFIX_DEBUG + msg, th, System.out);
	}
	
	public static void warn(String msg) {
		warn(msg, null);
	}
	
	public static void warn(String msg, Throwable th) {
		print(PREFIX_WARN + msg, th, System.err);
	}
	
	public static void error(String msg) {
		error(msg, null);
	}
	
	public static void error(String msg, Throwable th) {
		print(PREFIX_ERROR + msg, th, System.err);
	}

	private static void print(String m, Throwable t, PrintStream ps) {
		if(quietMode) {
			return;
		}
		
		ps.println(m);
		if(t != null) {
			t.printStackTrace(ps);
		}
	}

	public static void setQuietMode(boolean quietMode) {
		LogLog.quietMode = quietMode;
	}

	public static void setDebugMode(boolean debugMode) {
		LogLog.debugMode = debugMode;
	}
	
	/*
	public static void main(String[] args) {
		print("This is test", new Throwable(), System.out);
		
		//LogLog.setDebugMode(true);
		//LogLog.setQuietMode(true);
		//System.setProperty(QUIET_MODE, "true");
		
		LogLog.debug("quietMode: false, debugMode: true");
		LogLog.warn("quietMode: false, debugMode: true");
		LogLog.error("quietMode: false, debugMode: true");
	}
	*/
}
