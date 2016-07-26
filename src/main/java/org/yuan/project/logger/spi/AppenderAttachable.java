package org.yuan.project.logger.spi;

import java.util.Iterator;

import org.yuan.project.logger.Appender;

public interface AppenderAttachable {

	/**
	 * 添加附加器
	 * @param appender
	 */
	void addAppender(Appender appender);
	
	/**
	 * 删除附加器
	 * @param name
	 */
	void delAppender(String name);
	
	/**
	 * 删除附加器
	 * @param appender
	 */
	void delAppender(Appender appender);
	
	/**
	 * 删除所有附加器
	 */
	void delAllAppenders();
	
	/**
	 * 是否包含附加器
	 * @param appender
	 */
	boolean hasAppender(Appender appender);
	
	/**
	 * 获取附加器
	 * @param name
	 */
	Appender getAppender(String name);
	
	/**
	 * 获取所有附加器
	 */
	Iterator<Appender> getAllAppenders();
	
}
