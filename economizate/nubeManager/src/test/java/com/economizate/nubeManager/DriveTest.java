package com.economizate.nubeManager;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.google.api.services.drive.model.File;

public class DriveTest {
	
	private static final String UBICACION_INGRESO_MENSUAL= "src/main/java/com/economizate/nubeManager/reporte/";
	
	//@Test
	public void conectaConGoogleDriveYSubirArchivo() {
		ConnectorDrive drive = new ConnectorDrive(UBICACION_INGRESO_MENSUAL + "ingreso-mensual.csv");
		
		//subo archivo
		boolean resultado = drive.upload();
		
		assertTrue("el archivo fue subido: ", resultado);
	}
	
	//@Test
	public void conectarConDriveYSubirArchivoOkConChequeoDeIdArchivo() throws IOException {
		ConnectorDrive drive = new ConnectorDrive(UBICACION_INGRESO_MENSUAL + "ingreso-mensual.csv");
		
		//Subo archivo
		String id = drive.uploadId();
		
		//Lo busco en el Drive
		File nuevo = null;
		List<File> archivos = ReaderDrive.leerArchivosDrive(drive);
		for(File f : archivos) {
			if (f.getId().equals(id))
				nuevo = f;
		}
		
		assertTrue("Busco el archivo subido al Drive: ", nuevo.getName().equals("historial-movimientos"));
		
	}

}
