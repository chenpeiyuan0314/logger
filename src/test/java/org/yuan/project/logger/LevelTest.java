package org.yuan.project.logger;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class LevelTest {

	@Test
	public void testIsGreaterOrEqual() {
		Level[] levels = {Level.ALL, Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL, Level.OFF};
		
		for(int i=0; i<levels.length; i++) {
			Level src = levels[i];
			for(int j=0; j<levels.length; j++) {
				Level dst = levels[j];
				if(i >= j) {
					Assert.assertTrue(src.isGreaterOrEqual(dst));
				} else {
					Assert.assertFalse(src.isGreaterOrEqual(dst));
				}
			}
		}
	}
	
	@Test
	public void testToLevel() {
		Level[] levels = {Level.ALL, Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL, Level.OFF};
	
		for(Level level : levels) {
			Assert.assertEquals(level, Level.toLevel(level.toInt()));
			Assert.assertEquals(level, Level.toLevel(level.toString()));
		}
	}
	
	@Test
	public void testToLevel2() {
		int[] vals = {
			//Level.ALL_INT - 1,
			Level.ALL_INT + 1,
			Level.TRACE_INT - 1,
			Level.TRACE_INT + 1,
			Level.DEBUG_INT - 1,
			Level.DEBUG_INT + 1,
			Level.INFO_INT - 1,
			Level.INFO_INT + 1,
			Level.WARN_INT - 1,
			Level.WARN_INT + 1,
			Level.ERROR_INT - 1,
			Level.ERROR_INT + 1,
			Level.FATAL_INT - 1,
			Level.FATAL_INT + 1,
			Level.OFF_INT - 1,
			//Level.OFF_INT + 1,
		};
		String[] strs = {null, "", "all ", "debug ", "trace ", "info ", "warn ", "error ", "fatal ", "off "};
	
		for(int val : vals) {
			Assert.assertEquals(Level.DEBUG, Level.toLevel(val));
		}
		for(String str : strs) {
			Assert.assertEquals(Level.DEBUG, Level.toLevel(str));
		}
	}
	
	@Test
	public void testEquals() {
		Level[] levels = {Level.ALL, Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL, Level.OFF};
		
		for(int i=0; i<levels.length; i++) {
			Level src = levels[i];
			for(int j=0; j<levels.length; j++) {
				Level dst = levels[j];
				if(i == j) {
					Assert.assertTrue(src.equals(dst));
				} else {
					Assert.assertFalse(src.equals(dst));
				}
			}
		}
		
		Object[] objs = {new Date(), null, new Object()};
		for(Object obj : objs) {
			Assert.assertFalse(Level.ALL.equals(obj));
		}
	}
}
