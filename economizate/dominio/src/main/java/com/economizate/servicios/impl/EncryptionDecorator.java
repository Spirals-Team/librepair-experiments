package com.economizate.servicios.impl;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.economizate.servicios.DataSource;
import com.economizate.servicios.IEncryption;

public class EncryptionDecorator extends DataSourceDecorator {

	IEncryption encriptador;
	
	byte[] data;
	
	public EncryptionDecorator(DataSource source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public EncryptionDecorator(DataSource source, IEncryption encriptacion) {
		super(source);
		this.encriptador = encriptacion;
	}

	@Override
    public void writeData(String data) {
        super.writeData(encode(data));
    }

    @Override
    public String readData() {
        return decode(super.readData());
    }

    public String encode(String data) {
    	this.data = this.encriptador.encrypt(data);
    	return new String(this.data);
    }

    public String decode(String data) {
        
        return this.encriptador.decrypt(this.data);
    }
}