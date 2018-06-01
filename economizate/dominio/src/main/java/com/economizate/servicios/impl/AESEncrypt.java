package com.economizate.servicios.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.poi.EmptyFileException;

import com.economizate.servicios.IEncryption;

public class AESEncrypt implements IEncryption {

	private static final String ALGORITHM = "AES"; 
	
	private static byte[] keyVal = {
        0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
};
	
	private Key key;
	private Cipher c;
	
	public AESEncrypt() {
		
        try {
        	key = generateKeyFromString("ABDVS");
			c = Cipher.getInstance(ALGORITHM);
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] encrypt(String texto) {
		String encryptedVal = null;
		
	    try {
	        
	        c.init(Cipher.ENCRYPT_MODE, key);
	        byte[] encValue = c.doFinal(texto.getBytes());
	        encryptedVal = Base64.getEncoder().encodeToString(encValue);
	    } catch(Exception ex) {
	        System.out.println("The Exception is=" + ex);
	    }

	    return encryptedVal.getBytes();
	}

	@Override
	public String decrypt(byte[] texto) {
		String decryptedValue = null;

	    try {
	        c.init(Cipher.DECRYPT_MODE, key);
	        byte[] decorVal = Base64.getDecoder().decode(texto);
	        byte[] decValue = c.doFinal(decorVal);
	        decryptedValue = new String(decValue);
	    } catch(Exception ex) {
	        System.out.println("The Exception is=" + ex);
	    }

	    return decryptedValue;
	}
	
	private Key generateKeyFromString(final String secKey) throws Exception {
	    final Key key = new SecretKeySpec(keyVal, ALGORITHM);
	    return key;
	}

	
}
