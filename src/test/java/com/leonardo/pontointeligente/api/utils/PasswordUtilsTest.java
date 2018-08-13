package com.leonardo.pontointeligente.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilsTest {

	private static final String SENHA = "56789abcd";
	private final BCryptPasswordEncoder bCryptyPasswordEncoder = new BCryptPasswordEncoder();
	
	@Test
	public void testSenhaNula() {
		assertNull(PasswordUtils.gerarBCrypt(null));
	}
	
	@Test
	public void testGerarHashSenha() {
		String hash = PasswordUtils.gerarBCrypt(SENHA);
		
		assertTrue(bCryptyPasswordEncoder.matches(SENHA, hash));
	}
	
}
