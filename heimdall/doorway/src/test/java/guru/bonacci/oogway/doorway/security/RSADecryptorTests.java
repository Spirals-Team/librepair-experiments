package guru.bonacci.oogway.doorway.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

public class RSADecryptorTests {

	Decryptor decryptor;

	PublicKey publicKey;

	@BeforeEach
	public void generateKeys()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, IOException {

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		publicKey = keyPair.getPublic();

		System.out.println("private:");
		System.out.println(privateKey.toString());

		System.out.println("public:");
		System.out.println(publicKey.toString());

		decryptor = new RSADecryptor(privateKey);
	}

	@Test
	public void shouldDecryptMatch() throws Exception {
		String plainText = "Hello World!";

		String encryptedText = encryptMessage(plainText, publicKey);
		String decryptedText = decryptor.decrypt(encryptedText);
		assertThat(decryptedText, is(plainText));

	}

	// Encrypt using RSA public key
	private static String encryptMessage(String plainText, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
	}
}