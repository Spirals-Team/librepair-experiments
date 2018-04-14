package com.prussia.test.myspring.util;

import org.junit.Test;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonBase64Test {

	@Test
	public void testBase64(){
		String enpassword = "V2FzYUBzc2cx";
		
		byte[] bpwd = Base64.decodeBase64(enpassword);
		
		String password = new String(bpwd);
		log.warn("password: {}", password);
		Assert.assertTrue(Base64.isBase64(enpassword.getBytes()));
		
	}
	
	@Test
	public void testBase642(){
		String enpassword = "V2FzYUBzc2cxyz";
		
		byte[] bpwd = Base64.decodeBase64(enpassword);
		
		String password = new String(bpwd);
		log.warn("password: {}", password);
		Assert.assertTrue(Base64.isBase64(enpassword.getBytes()));
	}
	
	@Test
	public void testBase(){
		String enpassword1 = "V2FzYUBzc2cxxy";
		
		byte[] bpwd1 = Base64.decodeBase64(enpassword1);
		
		String password1 = new String(bpwd1);
		log.warn("password1: {}", password1);
		
		
		String enpassword2 = "V2FzYUBzc2cx";
		
		byte[] bpwd2 = Base64.decodeBase64(enpassword2);
		
		String password2 = new String(bpwd2);
		log.warn("password1: {}", password2);
		
		Assert.assertNotEquals(password1, password2);
		
	}
	
}
