package org.apache.log4j.demo;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.log4j.config.PropertyGetter;
import org.apache.log4j.config.PropertyPrinter;
import org.junit.Test;

public class PropertyGetterTest {

	@Test
	public void test() throws Exception {
		Person person = new Person();
		person.setAge("30");
		person.setSix("male");
		person.setName("yuan");
		
		/*
		PropertyGetter getter = new PropertyGetter(person);
		getter.getProperties(new PropertyCallback() {
			@Override
			public void foundProperty(Object obj, String prefix, String name, Object value) {
				System.out.println(name + "=" + value);
			}
		}, "");
		*/
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		PropertyGetter.getProperties(person, new PropertyPrinter(pw), "");
		pw.flush();
		
		new PropertyPrinter(pw);
		pw.flush();
		
		pw.close();
	}
	
	public static class Person {
		public String getSix() {
			return six;
		}
		public void setSix(String six) {
			this.six = six;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		private String six;
		private String age;
		private String name;
	}
}


