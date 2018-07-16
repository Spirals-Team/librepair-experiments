package br.com.rodolfo.pontointeligente.api.utils;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * PasswordutilsTest
 */
public class PasswordutilsTest {

    private static final String SENHA = "123456";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    

    @Test
    public void testSenhaNula() {

        assertNull(Passwordutils.gerarBCrypt(null));        
    }

    @Test
    public void testGerarSenhaHash() {
        
        String hash = Passwordutils.gerarBCrypt("123456");

        assertTrue(bCryptPasswordEncoder.matches(SENHA, hash));
    }

}