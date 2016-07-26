package org.yuan.project.logger;

import org.yuan.project.logger.spi.LoggerRepository;
import org.yuan.project.logger.spi.RootLogger;

public class LoggerManager {

	static {
		ConsoleAppender appender = new ConsoleAppender();
		appender.setLevel(Level.ALL);
		appender.setLayout(new SimpleLayout());
		Logger root = new RootLogger();
		root.setLevel(Level.DEBUG);
		root.addAppender(appender);
		repos = new Hierarchy(root);
	}
	
	/**
	 * 获取记录器仓库
	 * @return
	 */
	public static LoggerRepository getLoggerRepository() {
		return repos;
	}
	
	/**
	 * 获取根记录器
	 * @return
	 */
	public static Logger getRootLogger() {
		return repos.getRootLogger();
	}
	
	/**
	 * 获取记录器
	 * @param name
	 * @return
	 */
	public static Logger getLogger(String name) {
		return repos.getLogger(name);
	}
	
	private static LoggerRepository repos;
}
