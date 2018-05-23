package com.economizate;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.economizate.servicios.DataSource;
import com.economizate.servicios.IEncryption;
import com.economizate.servicios.impl.AESEncrypt;
import com.economizate.servicios.impl.DESEncrypt;
import com.economizate.servicios.impl.EncyptionDecorator;
import com.economizate.servicios.impl.FileDataSource;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.RSAEncrypt;

public class EncriptadoTest {


	@Test
	public void encriptarTest() throws FileNotFoundException {
		
        IEncryption encriptador = new AESEncrypt();
        String texto = "Hola Mundo";
        byte[] textoEncriptado = encriptador.encrypt(texto);
		String textoDesencriptado = encriptador.decrypt(textoEncriptado);
		assertTrue(texto.equals(textoDesencriptado));
	}
	
	/*@Test
	public void encriptarDESTest() throws FileNotFoundException {
		
		
        IEncryption encriptador = new DESEncrypt();
        String texto = "Hola Mundo";
        byte[] textoEncriptado = encriptador.encrypt(texto);
		String textoDesencriptado = encriptador.decrypt(textoEncriptado);
		assertTrue(texto.equals(textoDesencriptado));
	}*/
	
	@Test
	public void encriptarRSATest() throws FileNotFoundException {
		
        IEncryption encriptador = new RSAEncrypt();
        String texto = "Hola Mundo";
        byte[] textoEncriptado = encriptador.encrypt(texto);
		String textoDesencriptado = encriptador.decrypt(textoEncriptado);
		assertTrue(texto.equals(textoDesencriptado));
	}
}
