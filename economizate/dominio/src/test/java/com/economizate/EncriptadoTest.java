package com.economizate;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.economizate.loader.MyClassLoader;
import com.economizate.servicios.IEncryption;
import com.economizate.servicios.INube;
import com.economizate.servicios.impl.AESEncrypt;
import com.economizate.servicios.impl.RSAEncrypt;
import com.economizate.servicios.impl.TXTWriter;

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
	
	@Ignore@Test
	public void driveEncriptado() throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException, IOException {
		IEncryption encriptador = new RSAEncrypt();
        String texto = "Hola Mundooooooooooooo";
        byte[] textoEncriptado = encriptador.encrypt(texto);
        new TXTWriter("registro-encriptado.csv", textoEncriptado.toString()).write();
        File file = new File("registro-encriptado.csv");
        
        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
	    MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
	    Class myObjectClass = classLoader.loadClass("ConnectorDrive");
	    classLoader.loadClass("NubeEnum");
	    classLoader.loadClass("NubePropiedades");
	    INube drive = (INube) myObjectClass.newInstance();
	      
	    drive.upload(file.getAbsolutePath());
        
        
        
	}
}
