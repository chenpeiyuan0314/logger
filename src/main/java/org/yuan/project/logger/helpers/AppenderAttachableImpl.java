package org.yuan.project.logger.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.yuan.project.logger.Appender;
import org.yuan.project.logger.spi.AppenderAttachable;
import org.yuan.project.logger.spi.LoggingEvent;

public class AppenderAttachableImpl implements AppenderAttachable {

	@Override
	public void addAppender(Appender appender) {
		if(!list.contains(appender)) {
			list.add(appender);
		}
	}

	@Override
	public void delAppender(String name) {
		if(name == null) {
			return;
		}
		
		for(Appender item : list) {
			if(item.getName().equals(name)) {
				list.remove(item);
				break;
			}
		}
	}

	@Override
	public void delAppender(Appender appender) {
		if(appender != null) {
			list.remove(appender);
		}
	}

	@Override
	public void delAllAppenders() {
		list.clear();
	}

	@Override
	public boolean hasAppender(Appender appender) {
		if(appender == null) {
			return false;
		}
		
		return list.contains(appender);
	}

	@Override
	public Appender getAppender(String name) {
		if(name == null) {
			return null;
		}
		
		for(Appender item : list) {
			if(item.getName().equals(name)) {
				return item;
			}
		}
		
		return null;
	}

	@Override
	public Iterator<Appender> getAllAppenders() {
		return list.iterator();
	}
	
	public int doAppenders(LoggingEvent event) {
		int count = 0;
		
		for(Appender appender : list) {
			appender.doAppend(event);
		}
		
		return count;
	}

	private List<Appender> list = new ArrayList<Appender>();
}
