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

import com.economizate.servicios.DataSource;

public class Cifrado extends DataSourceDecorator {

	public Cifrado(DataSource source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	
}

