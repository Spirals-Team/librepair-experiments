package com.economizate;

import static org.junit.Assert.*;


import java.io.IOException;
import org.junit.Test;
import com.economizate.servicios.BaseReader;
import com.economizate.servicios.FactoryReader;


public class ReaderTest {

	BaseReader importador;
	
	@Test (expected = IOException.class)
	public void readArchivoInvalidoConExtension() throws IOException {
		
		importador = FactoryReader.getParseador("C:\\Users\\Desktop\\prueba.doc");
	}
	
	@Test (expected = IOException.class)
	public void readArchivoInvalidoSinExtension() throws IOException {
		
		importador = FactoryReader.getParseador("C:\\Users\\Desktop\\prueba");
	}
	
	@Test
	public void readArchivoCSV() throws IOException {
		
		importador = FactoryReader.getParseador("C:\\Users\\nidibiase\\Desktop\\prueba.csv");
		assertFalse(importador.read().isEmpty());
	}
	
}
