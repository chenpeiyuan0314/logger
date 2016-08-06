package org.yuan.project.logger.demo;

import org.junit.Test;

public class SystemOutErrDemo {

	//@Test
	public void testOut() {
		System.out.println("abc");
		//System.out.close();
		System.out.println("def");;
	}
	
	@Test
	public void testErr() {
		System.err.println("abc");
		System.err.close();
		System.err.flush();
		System.err.println("def");
		System.err.flush();
	}
}
