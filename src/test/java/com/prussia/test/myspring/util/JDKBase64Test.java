package com.prussia.test.myspring.util;

import org.junit.Test;

import java.util.Base64;

import org.junit.Assert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDKBase64Test {

	@Test
	public void testBase64() {
		String enpassword = "V2FzYUBzc2cx";

		byte[] bpwd = Base64.getDecoder().decode(enpassword.getBytes());

		String password = new String(bpwd);
		log.warn("password: {}", password);

	}

	@Test
	public void testBase642() {
		String enpassword = "V2FzYUBzc2cxyz";

		byte[] bpwd = Base64.getDecoder().decode(enpassword.getBytes());

		String password = new String(bpwd);
		log.warn("password: {}", password);
	}
	
	@Test
	public void testBase64Comparation() {
		String enpassword1 = "V2FzYUBzc2cx";

		byte[] bpwd1 = Base64.getDecoder().decode(enpassword1.getBytes());

		String password1 = new String(bpwd1);
		log.warn("password: {}", password1);
		
		
		String enpassword2 = "V2FzYUBzc2cx43";

		byte[] bpwd2 = Base64.getDecoder().decode(enpassword2.getBytes());

		String password2 = new String(bpwd2);
		log.warn("password: {}", password2);
		
		Assert.assertNotEquals(password2, password1);
	}
	
	
}
