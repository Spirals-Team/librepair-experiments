package com.prussia.test.myspring.util;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilTest {
	
	@Test
	public void testFileLength(){
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("application.properties").getFile());
		System.out.print("file.length() = " + file.length());
		
		Assert.assertTrue(file.length() < 100 * 1024* 1024); // bytes
	}
}
