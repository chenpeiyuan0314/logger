package org.yuan.project.logger;

public class Level {
	//-------------------------------------------------------
	// 级别常量数值
	//-------------------------------------------------------
	public static final int OFF_INT = Integer.MAX_VALUE;
	public static final int FATAL_INT = 60000;
	public static final int ERROR_INT = 50000;
	public static final int WARN_INT = 40000;
	public static final int INFO_INT = 30000;
	public static final int DEBUG_INT = 20000;
	public static final int TRACE_INT = 10000;
	public static final int ALL_INT = Integer.MIN_VALUE;
	
	//-------------------------------------------------------
	// 级别常量对象
	//-------------------------------------------------------
	public static final Level OFF = new Level(OFF_INT, "OFF");
	public static final Level FATAL = new Level(FATAL_INT, "FATAL");
	public static final Level ERROR = new Level(ERROR_INT, "ERROR");
	public static final Level WARN = new Level(WARN_INT, "WARN");
	public static final Level INFO = new Level(INFO_INT, "INFO");
	public static final Level DEBUG = new Level(DEBUG_INT, "DEBUG");
	public static final Level TRACE = new Level(TRACE_INT, "TRACE");
	public static final Level ALL = new Level(ALL_INT, "ALL");
	
	//-------------------------------------------------------
	//
	//-------------------------------------------------------
	protected Level(int levelVal, String levelStr) {
		this.levelStr = levelStr;
		this.levelVal = levelVal;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Level) {
			Level level = (Level)obj;
			if(levelStr.equals(level.toString()) 
				&& levelVal == level.toInt()) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isGreaterOrEqual(Level level) {
		if(levelVal >= level.levelVal) {
			return true;
		}
		return false;
	}
	
	public int toInt() {
		return levelVal;
	}
	
	public String toString() {
		return levelStr;
	}
	
	public static Level toLevel(int levelVal) {
		return toLevel(levelVal, DEBUG);
	}
	
	public static Level toLevel(String levelStr) {
		return toLevel(levelStr, DEBUG);
	}
	
	public static Level toLevel(int levelVal, Level levelDef) {
		switch(levelVal) {
		case ALL_INT :
			return ALL;
		case TRACE_INT :
			return TRACE;
		case DEBUG_INT :
			return DEBUG;
		case INFO_INT :
			return INFO;
		case WARN_INT :
			return WARN;
		case ERROR_INT :
			return ERROR;
		case FATAL_INT : 
			return FATAL;
		case OFF_INT :
			return OFF;
		default:
			return levelDef;
		}
	}
	
	public static Level toLevel(String levelStr, Level levelDef) {
		if(levelStr == null) {
			return levelDef;
		}
		
		if(levelStr.equalsIgnoreCase("ALL")) 
			return ALL;
		if(levelStr.equalsIgnoreCase("TRACE")) 
			return TRACE;
		if(levelStr.equalsIgnoreCase("DEBUG"))
			return DEBUG;
		if(levelStr.equalsIgnoreCase("INFO"))
			return INFO;
		if(levelStr.equalsIgnoreCase("WARN"))
			return WARN;
		if(levelStr.equalsIgnoreCase("ERROR"))
			return ERROR;
		if(levelStr.equalsIgnoreCase("FATAL"))
			return FATAL;
		if(levelStr.equalsIgnoreCase("OFF"))
			return OFF;
		
		return levelDef;
	}
	
	//-------------------------------------------------------
	// 成员属性
	//-------------------------------------------------------
	private int levelVal;
	private String levelStr;
}
