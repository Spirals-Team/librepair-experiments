package com.economizate.servicios.impl;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.economizate.servicios.IEncryption;

public class DESEncrypt implements IEncryption {

	
	private static Cipher decryptCipher;
	private static final byte[] iv = { 11, 22, 33, 44, 99, 88, 77, 66 };
	
	private static byte[] keyVal = {
        0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
};
	
	@Override
	public byte[] encrypt(String texto) {
		String key = "squirrel123";
		byte[] textEncrypted = null;
		byte[] utf8 = null;

		try {
			//byte[] utf8 = texto.getBytes("UTF8");
			KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		    SecretKey myDesKey = keygenerator.generateKey();
			Cipher desCipher = Cipher.getInstance("DES");
			desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
			
			utf8 = texto.getBytes("UTF8");
			textEncrypted = desCipher.doFinal(utf8);
			//textEncrypted = desCipher.doFinal(utf8);
			
			textEncrypted = Base64.getEncoder().encode(textEncrypted);

			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return textEncrypted;
        //return new String(textEncrypted);
	}

	@Override
	public String decrypt(byte[] texto) {
		String decryptedValue = null;
		byte[] textDecrypted = null;
		byte[] utf8 = null;
	    try {

	    	KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		    SecretKey myDesKey = keygenerator.generateKey();
			Cipher desCipher = Cipher.getInstance("DES");
			desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
			textDecrypted = Base64.getDecoder().decode(texto);
			final byte[] encValue = desCipher.doFinal(textDecrypted);
			
			
			//textDecrypted = desCipher.doFinal(texto.getBytes("UTF-8"));
			return new String(encValue, "UTF8");
	    } catch(Exception ex) {
	        System.out.println("The Exception is=" + ex);
	    }

	    return "";
	}
	
	private Key generateKeyFromString() throws Exception {
	    //final byte[] keyVal = Base64.getDecoder().decode(secKey);
	    final Key key = new SecretKeySpec(keyVal, "DES");
	    return key;
	}

}
