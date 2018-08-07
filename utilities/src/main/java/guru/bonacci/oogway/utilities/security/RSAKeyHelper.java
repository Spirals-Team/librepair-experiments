package guru.bonacci.oogway.utilities.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAKeyHelper {

	private static final String keyDir = "D:/";
	
	public static void main(String[] args) throws Exception {
		KeyPair keypair = genKeyPair();
		// Save to file system
		saveKeyPair(keyDir, keypair);
		// Loads from file system
		KeyPair loaded = loadKeyPair(keyDir);
		System.out.println(loaded.getPrivate());
		System.out.println(loaded.getPublic());
	}

	public static KeyPair genKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(1024);
		return keyGenerator.generateKeyPair();
	}

	public static void saveKeyPair(String dir, KeyPair keyPair) throws IOException {
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
		FileOutputStream fos = new FileOutputStream(dir + "/public.key");
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();

		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
		fos = new FileOutputStream(dir + "/private.key");
		fos.write(pkcs8EncodedKeySpec.getEncoded());
		fos.close();
	}

	public static PrivateKey loadPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		return loadKeyPair(path).getPrivate();
	}

	public static PublicKey loadPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		return loadKeyPair(path).getPublic();
	}

	public static KeyPair loadKeyPair(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// read public key from file
		File filePublicKey = new File(path + "/public.key");
		FileInputStream fis = new FileInputStream(filePublicKey);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();

		// read private key from file
		File filePrivateKey = new File(path + "/private.key");
		fis = new FileInputStream(filePrivateKey);
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();

		// Convert them into KeyPair
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return new KeyPair(publicKey, privateKey);
	}
}