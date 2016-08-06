package org.apache.log4j.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class CalendarTest {

	@Test
	public void test() {
		Date date = new Date();
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//sdf.setTimeZone(gmt);
		
		Calendar cal = Calendar.getInstance(gmt);
		cal.setTime(date);
		//cal.setTimeZone(gmt);
		cal.add(Calendar.DATE, 1);
		
		String r1 = sdf.format(date);
		System.out.println(r1);
		String r2 = sdf.format(cal.getTime());
		System.out.println(r2);
	}
}
