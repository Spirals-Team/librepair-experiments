package com.economizate.servicios.impl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

public class Cifrado {

	public Cifrado() throws FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException {
		OutputStream out = new FileOutputStream("somefile");
		out = new BufferedOutputStream(out); // buffer before writing
		out = new CipherOutputStream(out,        // encrypt all data
		         Cipher.getInstance("DES/CBC/PKCS5Padding"));
		out = new ZipOutputStream(out);      // compress all data
		//To write our own decorator, we could do:
	}
	
	
	
}

