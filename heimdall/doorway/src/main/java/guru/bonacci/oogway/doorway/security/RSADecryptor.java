package guru.bonacci.oogway.doorway.security;

import java.security.PrivateKey;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSADecryptor implements Decryptor {

	private PrivateKey privateKey;

	public RSADecryptor(PrivateKey key) {
    	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.privateKey = key;
	}

	@Override
	public String decrypt(String encryptedInput) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedInput)));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
