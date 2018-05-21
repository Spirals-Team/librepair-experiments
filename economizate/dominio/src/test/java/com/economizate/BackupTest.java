package com.economizate;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.economizate.batch.BackupArchivo;
import com.economizate.batch.BackupTimer;
import com.economizate.batch.EjecutorBackup;
import com.economizate.batch.IBackup;

public class BackupTest {
	
	IBackup backupArchivo;
	List<IBackup> listaBackups;
	EjecutorBackup ejecutor;
	
	@Before
	public void inicializar() {
		borrarArchivosBackups();
	}
	
	@After
	public void restaurar() {
		borrarArchivosBackups();
	}
	
	//Corresponde a criterio de aceptación 1
	@Test
	public void generarDescargaDeArchivoTxtConFrecuencia3SegundosYEspera() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio1/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.TRESSEGUNDOS, listaBackups);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(5000);
		
			try (Stream<Path> files = Files.list(Paths.get("src/test/resources/backup/criterio1/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica", contador == 2);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Corresponde a criterio de aceptación 2
	@Test
	public void generarDescargaDeArchivoTxtConFrecuencia5Minutos() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio2/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.CINCOMINUTOS, listaBackups);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(305000);
		
			try (Stream<Path> files = Files.list(Paths.get("./src/test/resources/backup/criterio2/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica", contador == 2);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Corresponde a criterio de aceptación 3
	@Test
	public void generarDescargaDeArchivoTxtBuscar2ArchivoAntesDe5Minutos() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio3/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.CINCOMINUTOS, listaBackups);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(4000);
		
			try (Stream<Path> files = Files.list(Paths.get("./src/test/resources/backup/criterio3/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica", contador == 1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Corresponde a criterio de aceptación 4
	@Test
	public void generarDescargaDeArchivoTxtConFrecuencia3SegundosSinEsperar() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio4/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.TRESSEGUNDOS, listaBackups);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(2000);
		
			try (Stream<Path> files = Files.list(Paths.get("./src/test/resources/backup/criterio4/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica sin espera", contador == 1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void generarDescargaDeArchivoTxtConFrecuenciaDiaria() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio5/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.DIARIO, listaBackups);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(2000);
		
			try (Stream<Path> files = Files.list(Paths.get("./src/test/resources/backup/criterio5/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica sin espera", contador == 1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void generarDescargaDeArchivoTxtConFrecuenciaSemanal() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio6/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.SEMANAL, listaBackups);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(2000);
		
			try (Stream<Path> files = Files.list(Paths.get("./src/test/resources/backup/criterio6/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica sin espera", contador == 1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void generarDescargaDeArchivoTxtConFrecuenciaMensual() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio7/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.MENSUAL, listaBackups);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(2000);
		
			try (Stream<Path> files = Files.list(Paths.get("./src/test/resources/backup/criterio7/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica sin espera", contador == 1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void generarDescargaDeArchivoTxtConFrecuenciaAnual() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio9/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.ANUAL, listaBackups);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(2000);
		
			try (Stream<Path> files = Files.list(Paths.get("./src/test/resources/backup/criterio9/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica sin espera", contador == 1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void generarDescargaDeArchivoTxtConFrecuenciaDefault() throws IOException {
		backupArchivo = new BackupArchivo("src/test/resources/backup/criterio8/");
		listaBackups = Arrays.asList(backupArchivo);
		
		ejecutor = new EjecutorBackup(BackupTimer.DEFAULT, backupArchivo);
		long contador = 0;
		
		try {
			ejecutor.ejecutar();
			Thread.sleep(4000);
		
			try (Stream<Path> files = Files.list(Paths.get("./src/test/resources/backup/criterio8/"))) {
				contador = files.count();
			}
			assertTrue("Descarga de archivo .txt en forma perdiódica sin espera", contador == 2);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void borrarArchivosBackups() {
		int contador = 1;
		while(contador != 10) {
			for(File file: new java.io.File("./src/test/resources/backup/criterio"+contador).listFiles()) 
			    if (!file.isDirectory()) 
			        file.delete();
			contador ++;
		}
	}

}
