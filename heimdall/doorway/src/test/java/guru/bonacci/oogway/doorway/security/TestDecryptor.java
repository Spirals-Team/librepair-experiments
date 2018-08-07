package guru.bonacci.oogway.doorway.security;

import guru.bonacci.oogway.doorway.security.Decryptor;

public class TestDecryptor implements Decryptor {

	@Override
	public String decrypt(String encryptedInput) {
		return encryptedInput;
	}
}
