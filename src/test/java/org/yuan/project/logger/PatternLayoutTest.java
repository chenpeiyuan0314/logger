package org.yuan.project.logger;

import org.junit.Assert;
import org.junit.Test;
import org.yuan.project.logger.spi.LoggingEvent;

public class PatternLayoutTest {

	@Test
	public void test() {
		Logger logger = Logger.getLogger("a.b.c");
		PatternLayout pl = new PatternLayout("%c %p - %m%n");
		String str = pl.format(new LoggingEvent(Level.DEBUG, "This is a test", logger, null));
		
		StringBuffer sb = new StringBuffer();
		sb.append("a.b.c");
		sb.append(" ");
		sb.append(Level.DEBUG.toString());
		sb.append(" - ");
		sb.append("This is a test");
		sb.append(Layout.LINE_SEP);
		
		Assert.assertEquals(sb.toString(), str);
	}
}
