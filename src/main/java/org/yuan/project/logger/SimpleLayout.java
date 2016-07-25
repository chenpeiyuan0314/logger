package org.yuan.project.logger;

import org.yuan.project.logger.spi.LoggingEvent;

public class SimpleLayout extends Layout {

	@Override
	public String format(LoggingEvent event) {
		StringBuffer sb = new StringBuffer();
		sb.append(event.getLevel().toString());
		sb.append(" - ");
		sb.append(event.getMessage());
		sb.append(LINE_SEP);
		return sb.toString();
	}

}
