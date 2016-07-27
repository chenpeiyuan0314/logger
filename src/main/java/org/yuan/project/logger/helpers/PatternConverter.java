package org.yuan.project.logger.helpers;

import org.yuan.project.logger.spi.LoggingEvent;

public abstract class PatternConverter {
	
	protected PatternConverter() {
		this(Integer.MIN_VALUE, Integer.MAX_VALUE, false);
	}
	
	protected PatternConverter(int min, int max, boolean isLeft) {
		this.min = min;
		this.max = max;
		this.isLeft = isLeft;
	}

	//-------------------------------------------------
	// 公有方法
	//-------------------------------------------------
	public void format(StringBuffer sb, LoggingEvent event) {
		String tmp = convert(event);
		
		if(tmp == null) {
			if(min > 0) {
				pad(sb, min);
			}
			return;
		}
		
		int len = tmp.length();
		if(len > max) {
			sb.append(tmp.substring(len - max));
			return;
		}
		if(len < min) {
			if(isLeft) {
				sb.append(tmp);
				pad(sb, min - len);
			} else {
				pad(sb, min - len);
				sb.append(tmp);
			}
			return;
		}
		sb.append(tmp);
	}
	
	public PatternConverter getNext() {
		return next;
	}

	public void setNext(PatternConverter next) {
		this.next = next;
	}

	//-------------------------------------------------
	// 公共接口
	//-------------------------------------------------
	public abstract String convert(LoggingEvent event);
	
	//-------------------------------------------------
	// 私有方法
	//-------------------------------------------------
	private void pad(StringBuffer sb, int length) {
		for(int i=0; i<length; i++) {
			sb.append(' ');
		}
	}
	
	//-------------------------------------------------
	// 成员属性
	//-------------------------------------------------
	private int min;
	private int max;
	private boolean isLeft;
	private PatternConverter next;
}
