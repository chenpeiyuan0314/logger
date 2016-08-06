package org.apache.log4j.demo;

import java.util.Properties;

import org.apache.log4j.config.PropertySetter;
import org.junit.Test;

public class PropertySetterTest {

	@Test
	public void test() {
		Person person = new Person();
		Properties props = new Properties();
		props.setProperty("six", "male");
		props.setProperty("age", "30");
		props.setProperty("name", "chen");
		
		PropertySetter.setProperties(person, props, "");
		System.out.println(person);
	}
	
	public static class Person {
		public String getSix() {
			return six;
		}
		public void setSix(String six) {
			this.six = six;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isMarried() {
			return married;
		}
		public void setMarried(boolean married) {
			this.married = married;
		}

		private String six;
		private int age;
		private String name;
		private boolean married;
		
		@Override
		public String toString() {
			return "Person [six=" + six + ", age=" + age + ", name=" + name + ", married=" + married + "]";
		}
	}
}
