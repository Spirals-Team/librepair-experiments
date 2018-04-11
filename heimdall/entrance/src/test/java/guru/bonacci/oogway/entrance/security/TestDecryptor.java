package guru.bonacci.oogway.entrance.security;

public class TestDecryptor implements Decryptor {

	@Override
	public String decrypt(String encryptedInput) {
		return encryptedInput;
	}
}
