package com.prussia.test.myspring.util;

import org.junit.Test;
import org.springframework.security.crypto.codec.Base64;
import org.junit.Assert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringBase64Test {

	@Test
	public void testBase64(){
		String enpassword = "V2FzYUBzc2cx";
		
		byte[] bpwd = Base64.decode(enpassword.getBytes());
		
		String password = new String(bpwd);
		log.warn("password: {}", password);
		Assert.assertTrue(Base64.isBase64(enpassword.getBytes()));
		
	}
	
	@Test
	public void testBase642(){
		String enpassword = "V2FzYUBzc2cxyz";
		
		byte[] bpwd = Base64.decode(enpassword.getBytes());
		
		String password = new String(bpwd);
		log.warn("password: {}", password);
		Assert.assertTrue(Base64.isBase64(enpassword.getBytes()));
	}
}
