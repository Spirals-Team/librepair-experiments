package com.economizate.servicios;

public interface IEncryption {

	public byte[] encrypt(String texto);
	
	public String decrypt(byte[] texto);
}
