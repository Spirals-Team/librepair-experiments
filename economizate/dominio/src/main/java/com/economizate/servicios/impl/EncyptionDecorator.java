package com.economizate.servicios.impl;

import java.io.ByteArrayOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.economizate.servicios.DataSource;

public class EncyptionDecorator extends DataSourceDecorator {

	public EncyptionDecorator(DataSource source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeData(String data){
		try {
			String s = "Hello there. How are you? Have a nice day.";

		    // Generate key
		    KeyGenerator kgen = KeyGenerator.getInstance("AES");
		    kgen.init(128);
		    SecretKey aesKey = kgen.generateKey();

		    // Encrypt cipher
		    Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);

		    // Encrypt
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
		    cipherOutputStream.write(data.getBytes());
		    cipherOutputStream.flush();
		    
		    cipherOutputStream.close();
		    this.wrappee.writeData(new String(outputStream.toByteArray()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Override
	public String readData(){
		return null;
	}
}
