package org.yuan.project.logger.helpers;

import java.util.ArrayList;
import java.util.List;

import org.yuan.project.logger.Layout;
import org.yuan.project.logger.spi.LoggingEvent;

public class PatternParser {
	
	public static final char ESCAPE_CHAR = '%';
	
	public static final int DOT_STATE = 1000;
	public static final int MIN_STATE = 1001;
	public static final int MAX_STATE = 1002;
	public static final int LIT_STATE = 1003;
	public static final int VER_STATE = 1004;

	public static final int FULL_LOCATION_CONVERTER = 2000;
	public static final int METHOD_LOCATION_CONVERTER = 2001;
	public static final int CLASS_LOCATION_CONVERTER = 2002;
	public static final int LINE_LOCATION_CONVERTER = 2003;
	public static final int FILE_LOCATION_CONVERTER = 2004;
	
	public static final int RELATIVE_TIME_CONVERTER = 2000;
	public static final int THREAD_CONVERTER = 2001;
	public static final int LEVEL_CONVERTER = 2002;
	public static final int NDC_CONVERTER = 2003;
	public static final int MESSAGE_CONVERTER = 2004;
	
	public PatternParser(String pattern) {
		this.pattern = pattern;
		state = LIT_STATE;
		min = Integer.MIN_VALUE;
		max = Integer.MAX_VALUE;
		isLeft = false;
		list = new ArrayList<PatternConverter>();
	}
	
	public PatternConverter parse() {
		StringBuffer sb = new StringBuffer();
		
		int i = 0;
		while(i < pattern.length()) {
			char c = pattern.charAt(i++);
			
			switch(state) {
			case LIT_STATE:
				if(i == pattern.length()) {
					sb.append(c);
					continue;
				}
				
				if(c == ESCAPE_CHAR) {
					switch(pattern.charAt(i)) {
					case ESCAPE_CHAR:
						sb.append(c);
						i++;
						break;
					case 'n':
						sb.append(Layout.LINE_SEP);
						i++;
						break;
					default:
						if(sb.length() > 0) {
							list.add(new LiteralPatternConverter(sb.toString()));
							sb.setLength(0);
						}
						sb.append(c);
						state = VER_STATE;
						break;	
					}
				} else {
					sb.append(c);
				}
				break;
			case VER_STATE:
				sb.append(c);
				switch(c) {
				case '-':
					isLeft = true;
					break;
				case '.':
					state = DOT_STATE;
					break;
				default:
					if(c >= '0' && c <= '9') {
						min = c - '0';
					} else {
						converter(c);
					}
					break;
				}
				break;
			case DOT_STATE:
				if(c >= '0' && c <= '9') {
					max = c - '0';
					state = MAX_STATE;
				}
				break;
			case MIN_STATE:
				if(c >= '0' && c <= '9') {
					min = min * 10;
					min = min + c - '0';
				} else {
					if(c == '.') {
						state = DOT_STATE;
					} else {
						converter(c);
					}
				}
				break;
			case MAX_STATE:
				if(c >= '0' && c <= '9') {
					max = max * 10;
					max = max + c - '0';
				} else {
					converter(c);
				}
				break;
			}
		}
		
		if(sb.length() > 0) {
			list.add(new LiteralPatternConverter(sb.toString()));
		}
		
		PatternConverter node = null;
		for(PatternConverter item : list) {
			if(node == null) {
				node = item;
				continue;
			}
			node.setNext(item);
			node = item;
		}
		
		return node;
	}
	
	private void converter(char c) {
		switch(c) {
		case 'c':
			break;
		case 'C':
			break;
		case 'd':
			break;
		case 'l':
			break;
		case '':
			break;
		case '':
			break;
		case '':
			break;
		}
	}
	
	//------------------------------------------------
	// 格式转换类
	//------------------------------------------------
	
	private class LiteralPatternConverter extends PatternConverter {
		private String value;
		
		public LiteralPatternConverter(String value) {
			this.value = value;
		}
		
		@Override
		public void format(StringBuffer sb, LoggingEvent event) {
			sb.append(value);
		}

		@Override
		public String convert(LoggingEvent event) {
			return value;
		}
	}
	
	private class BasicPatternConverter extends PatternConverter {
		private int type;
		
		public BasicPatternConverter(int min, int max, boolean isLeft, int type) {
			super(min, max, isLeft);
			this.type = type;
		}

		@Override
		public String convert(LoggingEvent event) {
			switch(type) {
			case RELATIVE_TIME_CONVERTER :
				return String.valueOf(event.getTime - LoggingEvent.getStartTime());
			case THREAD_CONVERTER :
				return null;
			case LEVEL_CONVERTER :
				return event.getLevel().toString();
			case NDC_CONVERTER :
				return null;
			case MESSAGE_CONVERTER : 
				return event.getMessage();
			}
			return null;
		}
	}
	
	private class LocationPatternConverter extends PatternConverter {
		private int type;
		
		public LocationPatternConverter(int min, int max, boolean isLeft, int type) {
			super(min, max, isLeft);
			this.type = type;
		}

		@Override
		public String convert(LoggingEvent event) {
			switch(type) {
			case FULL_LOCATION_CONVERTER :
				return null;
			case METHOD_LOCATION_CONVERTER :
				return null;
			case CLASS_LOCATION_CONVERTER :
				return event.getLevel().toString();
			case LINE_LOCATION_CONVERTER :
				return null;
			case FILE_LOCATION_CONVERTER : 
				return event.getMessage();
			}
			return null;
		}
	}
	
	//------------------------------------------------
	// 成员属性
	//------------------------------------------------
	private int state;
	private String pattern;
	private int min;
	private int max;
	private boolean isLeft;
	private List<PatternConverter> list;
}
