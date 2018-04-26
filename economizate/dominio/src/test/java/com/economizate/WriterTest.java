package com.economizate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.Assert;

import org.junit.Test;

import com.economizate.datos.ListaMovimientos;
import com.economizate.servicios.BaseWriter;
import com.economizate.servicios.impl.ExcelWriter;
import com.economizate.servicios.impl.MovimientosSheet;
import com.economizate.servicios.impl.PdfWriter;
import com.economizate.servicios.impl.TXTWriter;
import com.economizate.servicios.impl.TransformadorMovimientos;

public class WriterTest {

	@Test
	public void writeExcelMovimientos() throws IOException {
		
		String nombreArchivo = "src/test/resources/prueba_writer.xlsx";
		BaseWriter writer = new ExcelWriter(nombreArchivo, new MovimientosSheet(new ListaMovimientos().getMovimientos()));
		writer.write();
		Path path = Paths.get(nombreArchivo);
		Assert.assertTrue(Files.exists(path));
	}
	
	@Test
	public void writeTxtMovimientos() throws IOException {
		
		String nombreArchivo = "src/test/resources/prueba_writer.txt";
		BaseWriter writer = new TXTWriter(nombreArchivo);
		writer.write("Hola Mundo");
		Path path = Paths.get(nombreArchivo);
		Assert.assertTrue(Files.exists(path));
	}
	
	@Test (expected = NullPointerException.class) 
	public void writeTxtMovimientosRutaInvalida() throws IOException{
		
			String nombreArchivo = null;
			BaseWriter writer = new TXTWriter(nombreArchivo);
			writer.write();
			
			Paths.get(nombreArchivo);		
	}
	
	@Test
	public void writePdfMovimientos() throws IOException {
				
		String nombreArchivo = "src/test/resources/prueba_writer.pdf";
		BaseWriter writer = new PdfWriter(nombreArchivo, new TransformadorMovimientos(new ListaMovimientos().getMovimientos()));
		writer.write();
		Path path = Paths.get(nombreArchivo);
		Assert.assertTrue(Files.exists(path));
	}
	
}
