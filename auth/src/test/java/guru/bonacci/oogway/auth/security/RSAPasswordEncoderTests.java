package guru.bonacci.oogway.auth.security;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RSAPasswordEncoderTests {

	PasswordEncoder passwordEncoder;

	PrivateKey privateKey;

	@BeforeEach
	public void generateKeys()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, IOException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		System.out.println("private:");
		System.out.println(privateKey.toString());

		System.out.println("public:");
		System.out.println(publicKey.toString());

		passwordEncoder = new RSAPasswordEncoder(publicKey);
	}

	@Test
	public void shouldMatch() throws Exception {
		String plainPassword = "Hello World!";

		String encryptedPassword = passwordEncoder.encode(plainPassword);
		assertThat(passwordEncoder.matches(plainPassword, encryptedPassword), is(true));

		String decryptedPassword = decryptMessage(encryptedPassword, privateKey);
		assertThat(decryptedPassword, is(plainPassword));

	}

	// Decrypt using RSA private key
	private static String decryptMessage(String encryptedText, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
	}
}