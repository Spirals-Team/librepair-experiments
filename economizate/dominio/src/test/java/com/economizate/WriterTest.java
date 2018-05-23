package com.economizate;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.xml.bind.ValidationException;

import org.junit.Test;

import com.economizate.datos.ListaMovimientos;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.BaseWriter;
import com.economizate.servicios.FactoryReader;
import com.economizate.servicios.FactoryWriterMovimientos;
import com.economizate.servicios.IConversor;
import com.economizate.servicios.impl.ConversorMovimientoConCuota;
import com.economizate.servicios.impl.ConversorMovimientoSinCuota;
import com.economizate.servicios.impl.ConvertListaMovimientosToString;
import com.economizate.servicios.impl.ExcelWriter;
import com.economizate.servicios.impl.MovimientosSheet;
import com.economizate.servicios.impl.PdfWriter;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.TXTWriter;
import com.economizate.servicios.impl.TransformadorMovimientos;

public class WriterTest {

	private String rutaArchivos = Propiedad.getInstance().getPropiedad("resourcesTesting");
	
	
	private boolean existeArchivo(String nombreArchivo) {
		Path path = Paths.get(nombreArchivo);
		return Files.exists(path);
	}
	
	@Test
	public void writeExcelMovimientos() throws IOException {
		
		String nombreArchivo = rutaArchivos + "prueba_writer.xlsx";
		BaseWriter writer = new ExcelWriter(nombreArchivo, new MovimientosSheet(new ListaMovimientos().getMovimientos()));
		writer.write();
		assertTrue(existeArchivo(nombreArchivo));
	}
	
	@Test
	public void writeTxtMovimientosConCuota() throws IOException, ValidationException, ParseException {
		
		String nombreArchivo = rutaArchivos + "prueba_writer.txt";
		IConversor<MovimientoMonetario> conversor = new ConversorMovimientoConCuota(";");
		BaseWriter writer = new TXTWriter(nombreArchivo, ConvertListaMovimientosToString.getRegistros(new ListaMovimientos().getMovimientos(), conversor));
		
		writer.write();
		assertTrue(existeArchivo(nombreArchivo));
				
	}
	
	@Test
	public void writeTxtMovimientosSinCuota() throws IOException, ValidationException, ParseException {
		
		String nombreArchivo = rutaArchivos + "prueba_writer.txt";
		IConversor<MovimientoMonetario> conversor = new ConversorMovimientoSinCuota(";");
		BaseWriter writer = new TXTWriter(nombreArchivo, ConvertListaMovimientosToString.getRegistros(new ListaMovimientos().getMovimientos(), conversor));		
		writer.write(ConvertListaMovimientosToString.getRegistros(new ListaMovimientos().getMovimientos(), conversor));
		assertTrue(existeArchivo(nombreArchivo));
				
	}
	
	@Test (expected = NullPointerException.class) 
	public void writeTxtMovimientosRutaInvalida() throws IOException{
		
			String nombreArchivo = null;
			BaseWriter writer = new TXTWriter(nombreArchivo, "");
			writer.write();
			
			Paths.get(nombreArchivo);		
	}
	
	@Test 
	public void writePdfMovimientosExcepcionTransformerCatcheada() throws IOException {
				
		String nombreArchivo = "src/test/resources/prueba_writer.pdf";
		BaseWriter writer = new PdfWriter(nombreArchivo, new TransformadorMovimientos(new ListaMovimientos().getMovimientos(), "movimientos2.xsl"));
		writer.write();
		
	}
	
	@Test (expected = IOException.class)
	public void writeArchivoInvalidoSinExtension() throws IOException {
		IConversor<MovimientoMonetario> conversor = new ConversorMovimientoSinCuota(";");
		FactoryWriterMovimientos.getWriter("src/test/resources/prueba", new ListaMovimientos().getMovimientos(), conversor);
	}
	
	@Test (expected = IOException.class)
	public void writeArchivoInvalidoConExtensionInvalida() throws IOException {
		IConversor<MovimientoMonetario> conversor = new ConversorMovimientoSinCuota(";");
		FactoryWriterMovimientos.getWriter("src/test/resources/prueba.csv", new ListaMovimientos().getMovimientos(), conversor);
	}
}
