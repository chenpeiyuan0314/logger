package org.yuan.project.logger.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		
		return list.get(0);
	}
	
	private void converter(char c) {
		PatternConverter pc = null;
		
		switch(c) {
		case 'c':
			pc = new CategoryPatternConverter(min, max, isLeft, extractPrecisionOption());
			break;
		case 'C':
			pc = new ClassNamePatternConverter(min, max, isLeft, extractPrecisionOption());
			break;
		case 'd':
			String format = extractOption();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				df = new SimpleDateFormat(format);
			} catch(Exception e) {
				LogLog.warn("date pattern '" + format + "' is wrong");
			}
			pc = new DatePatternConverter(min, max, isLeft, df);
			break;
		case 'l':
			pc = new LocationPatternConverter(min, max, isLeft, FULL_LOCATION_CONVERTER);
			break;
		case 'L':
			pc = new LocationPatternConverter(min, max, isLeft, LINE_LOCATION_CONVERTER);
			break;
		case 'F':
			pc = new LocationPatternConverter(min, max, isLeft, FILE_LOCATION_CONVERTER);
			break;
		case 'm':
			pc = new BasicPatternConverter(min, max, isLeft, MESSAGE_CONVERTER);
			break;
		case 'M':
			pc = new LocationPatternConverter(min, max, isLeft, METHOD_LOCATION_CONVERTER);
			break;
		case 'p':
			pc = new BasicPatternConverter(min, max, isLeft, LEVEL_CONVERTER);
			break;
		case 'r':
			pc = new BasicPatternConverter(min, max, isLeft, RELATIVE_TIME_CONVERTER);
			break;
		case 't':
			pc = new BasicPatternConverter(min, max, isLeft, THREAD_CONVERTER);
			break;
		case 'x':
		case 'X':
			sb.setLength(0);
		default:
			pc = new LiteralPatternConverter(sb.toString());
			break;
		}
		
		list.add(pc);
		sb.setLength(0);
		state = LIT_STATE;
		reset();
	}
	
	private String extractOption() {
		if((i < pattern.length()) && (pattern.charAt(i) == '{')) {
			int end = pattern.indexOf('}', i);
			if(end > i) {
				String r = pattern.substring(i + 1, end);
				i = end + 1;
				return r;
			}
		}
		return null;
	}
	
	private int extractPrecisionOption() {
		String opt = extractOption();
		int r = 0;
		if(opt != null) {
			try {
				r = Integer.parseInt(opt);
				r = r <= 0 ? 0 : r;
				if(r <= 0) {
					
				}
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return r;
	}
	
	private void reset() {
		min = Integer.MIN_VALUE;
		max = Integer.MAX_VALUE;
		isLeft = false;
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
				return String.valueOf(event.getTimestamp() - LoggingEvent.getStartTime());
			case THREAD_CONVERTER :
				return event.getThreadName();
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
				return event.getLocationInfo().getFullInfo();
			case METHOD_LOCATION_CONVERTER :
				return event.getLocationInfo().getMethodName();
			case LINE_LOCATION_CONVERTER :
				return event.getLocationInfo().getLineNumber();
			case FILE_LOCATION_CONVERTER : 
				return event.getLocationInfo().getFileName();
			}
			return null;
		}
	}
	
	private abstract class NamedPatternConverter extends PatternConverter {
		private int precision;
		
		public NamedPatternConverter(int min, int max, boolean isLeft, int precision) {
			super(min, max, isLeft);
			this.precision = precision;
		}
		
		public abstract String getFullyQualifiedName(LoggingEvent event);
		
		public String convert(LoggingEvent event) {
			String tmp = getFullyQualifiedName(event);
			if(precision <= 0) {
				return tmp;
			} else {
				int len = tmp.length();
				int end = len - 1;
				for(int i = precision; i>0; i--) {
					end = tmp.lastIndexOf('.', end - 1);
					if(end == -1) {
						return tmp;
					}
				}
				return tmp.substring(end + 1, len);
			}
		}
	}
	
	private class ClassNamePatternConverter extends NamedPatternConverter {
		public ClassNamePatternConverter(int min, int max, boolean isLeft, int precision) {
			super(min, max, isLeft, precision);
		}

		@Override
		public String getFullyQualifiedName(LoggingEvent event) {
			return event.getLocationInfo().getClassName();
		}
	}
	
	private class CategoryPatternConverter extends NamedPatternConverter {
		public CategoryPatternConverter(int min, int max, boolean isLeft, int precision) {
			super(min, max, isLeft, precision);
		}

		@Override
		public String getFullyQualifiedName(LoggingEvent event) {
			return event.getLogger().getName();
		}
	}
	
	private class DatePatternConverter extends PatternConverter {
		private DateFormat df;
		
		public DatePatternConverter(int min, int max, boolean isLeft, DateFormat df) {
			super(min, max, isLeft);
			this.df = df;
		}

		@Override
		public String convert(LoggingEvent event) {
			Date date = new Date(event.getTimestamp());
			String converted = null;
			try {
				converted = df.format(date);
			} catch(Exception e) {
				e.printStackTrace();
			}
			return converted;
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
	private StringBuffer sb = new StringBuffer();
	private int i = 0;
}
