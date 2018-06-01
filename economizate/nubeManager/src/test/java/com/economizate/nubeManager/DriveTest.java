package com.economizate.nubeManager;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.Test;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.model.File;

public class DriveTest {
	
	private static final String UBICACION_INGRESO_MENSUAL= "src/test/resources/";
	
	//US1
	@Test
	public void conectarConGoogleDriveYSubirArchivo() throws IOException {
		ConnectorDrive drive = new ConnectorDrive();
		
		//subo archivo: true si es exitoso
		boolean resultado = drive.upload(UBICACION_INGRESO_MENSUAL + "ingreso-mensual.csv");
		assertTrue("el archivo fue subido: ", resultado);
	}
	
	//US1
	@Test
	public void conectarConDriveYSubirArchivoOkConChequeoDeIdArchivo() throws IOException {
		ConnectorDrive drive = new ConnectorDrive();
		
		//Subo archivo: retorna id de archivo en GoogleDrive
		String id = drive.uploadId(UBICACION_INGRESO_MENSUAL + "ingreso-mensual.csv");
		
		//Lo busco en el Drive por su id
		List<File> archivos = ReaderDrive.leerArchivosDrive(drive);
		File nuevo = buscarFilePorId(archivos, id);
		
		assertTrue("Busco el archivo subido al Drive: ", nuevo.getId().equals(id));
		
	}
	
	//US2
	@Test
	public void conectarConGoogleDriveYSubirArchivoSinInternet() throws java.net.UnknownHostException {
		ConnectorDrive drive = new ConnectorDrive();
		boolean resultado = false;
		
		try {
			resultado = drive.upload(UBICACION_INGRESO_MENSUAL + "ingreso-mensual.csv");
			assertTrue("el archivo fue subido: ", resultado);
		}catch(Exception e) {
			assertTrue("el archivo NO fue subido: ", resultado==false);
		}
	}
	
	//US3
	@Test
	public void conectarConGoogleDriveConCredecialesInexistentes() throws IOException, GeneralSecurityException{
		String ubicacionCredencalesErroneas = "src/resources";
		
		Credential credenciales = null;
		
		try{
			ConnectorDrive.getCredentials(ubicacionCredencalesErroneas);
			assertTrue("Tengo credenciales: ", credenciales != null);
		}catch(Exception e) {
			assertTrue("No tengo credenciales: ", credenciales == null);
		}
	}
	
	//US4
	@Test
	public void buscarReportesInexistentesParaSubirAlDriveYObtenerArchivoInvalido() throws GeneralSecurityException{
		ConnectorDrive drive = new ConnectorDrive(); 
		final String UBICACION_ERRONEA = "inexistente";
		
		try {
			//subo archivo: true si es exitoso
			boolean resultado = drive.upload(UBICACION_ERRONEA + "ingreso-mensual.csv");
			
			fail("Encontro el archivo cuando no debería");
		}catch(IOException e) {
			assertTrue("test que evalúa la ubicación del archivo: ", true);
		}
	}
	
	private static File buscarFilePorId(List<File> archivos, String id) {
		File nuevo = null;
		for(File f : archivos) {
			if (f.getId().equals(id))
				nuevo = f;
		}
		return nuevo;
	}

}
