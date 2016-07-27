package org.yuan.project.logger;

import org.yuan.project.logger.helpers.PatternConverter;
import org.yuan.project.logger.helpers.PatternParser;
import org.yuan.project.logger.spi.LoggingEvent;

public class PatternLayout extends Layout {
	
	public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
	public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";

	public static final int BUF_SIZE = 256;
	//private static final int MAX_SIZE = 1024;
	
	public PatternLayout() {
		this(DEFAULT_CONVERSION_PATTERN);
	}
	
	public PatternLayout(String pattern) {
		this.pattern = pattern;
		head = new PatternParser(pattern == null ? DEFAULT_CONVERSION_PATTERN : pattern).parse();
	}

	public String getConversionPattern() {
		return pattern;
	}

	public void setConversionPattern(String pattern) {
		this.pattern = pattern;
		head = new PatternParser(pattern).parse();
	}

	@Override
	public String format(LoggingEvent event) {
		StringBuffer sb = new StringBuffer(BUF_SIZE);
		
		PatternConverter node = head;
		while(node != null) {
			sb.append(node.convert(event));
			node = node.getNext();
		}
		
		return sb.toString();
	}
	
	//------------------------------------------------
	// 成员属性
	//------------------------------------------------
	private String pattern;
	private PatternConverter head;
}
