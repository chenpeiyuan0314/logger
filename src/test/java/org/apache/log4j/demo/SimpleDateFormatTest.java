package org.apache.log4j.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class SimpleDateFormatTest {

	@Test
	public void test() {
		Locale.setDefault(Locale.ENGLISH);
		String month = "'.'yyyy-MM";
		String week = "yyyy-ww";
		String day = "yyyy-MM-dd";
		String half_day = "yyyy-MM-dd-a";
		String hour = "yyyy-MM-dd.HH";
		String minute = "yyyy-MM-dd.HHmm";
		String[] patterns = {month, week, day, half_day, hour, minute};
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat();
		for(int i=0; i<patterns.length; i++) {
			sdf.applyPattern(patterns[i]);
			System.out.println(sdf.format(date));
		}
	}
}
