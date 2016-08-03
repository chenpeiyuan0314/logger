package org.apache.log4j.demo;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

public class RenameFileTest {

	//@Test
	public void test() throws Exception {
		String file1 = "E:/file1.txt";
		String file2 = "E:/file2.txt";
		
		FileOutputStream fos = new FileOutputStream(file1);
		fos.write("This is a test 1".getBytes());
		fos.close();
		
		File src = new File(file1);
		File dst = new File(file2);
		src.renameTo(dst);
		
		/*
		fos = new FileOutputStream(file1);
		fos.write("This is a test 2".getBytes());
		fos.close();
		*/
	}
	
	@Test
	public void test2() throws Exception {
		File file = new File("E:/file3.txt");
		System.out.println(file.lastModified());
	}
}
