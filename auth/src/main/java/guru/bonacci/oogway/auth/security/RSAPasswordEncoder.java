package guru.bonacci.oogway.auth.security;

import java.security.PublicKey;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.security.crypto.password.PasswordEncoder;

public class RSAPasswordEncoder implements PasswordEncoder {

	private PublicKey publicKey;

	public RSAPasswordEncoder(PublicKey key) {
    	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.publicKey = key;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			//https://rdist.root.org/2009/10/06/why-rsa-encryption-padding-is-critical/
			Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(rawPassword.toString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}
}
