package com.economizate;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.xml.bind.ValidationException;
import javax.xml.transform.TransformerException;

import junit.framework.Assert;

import org.junit.Test;

import com.economizate.datos.ListaMovimientos;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.BaseWriter;
import com.economizate.servicios.IConversorMovimiento;
import com.economizate.servicios.IParserRegistro;
import com.economizate.servicios.LoaderFromFile;
import com.economizate.servicios.impl.ConversorMovimientoConCuota;
import com.economizate.servicios.impl.ConversorMovimientoSinCuota;
import com.economizate.servicios.impl.ConvertListaMovimientosToString;
import com.economizate.servicios.impl.ConvertObjetoToXML;
import com.economizate.servicios.impl.ExcelWriter;
import com.economizate.servicios.impl.LoaderMovimientosFromFile;
import com.economizate.servicios.impl.MovimientosSheet;
import com.economizate.servicios.impl.ParserRegistroConCuota;
import com.economizate.servicios.impl.ParserRegistroFechaSinCuota;
import com.economizate.servicios.impl.PdfWriter;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.TXTWriter;
import com.economizate.servicios.impl.TransformadorMovimientos;

public class WriterTest {

	private String rutaArchivos = Propiedad.getInstance().getPropiedad("resourcesTesting");
	
	private LoaderFromFile<MovimientoMonetario> importador;
	
	private IParserRegistro parser;
	
	private ConvertObjetoToXML convert = new ConvertObjetoToXML();
	
	private ConvertListaMovimientosToString convert2 = new ConvertListaMovimientosToString();
	
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
	public void writeTxtMovimientos() throws IOException, ValidationException, ParseException {
		
		String nombreArchivo = rutaArchivos + "prueba_writer.txt";
		BaseWriter writer = new TXTWriter(nombreArchivo);
		IConversorMovimiento conversor = new ConversorMovimientoConCuota(";");
		writer.write(ConvertListaMovimientosToString.getRegistros(new ListaMovimientos().getMovimientos(), conversor));
		assertTrue(existeArchivo(nombreArchivo));
				
	}
	
	@Test (expected = NullPointerException.class) 
	public void writeTxtMovimientosRutaInvalida() throws IOException{
		
			String nombreArchivo = null;
			BaseWriter writer = new TXTWriter(nombreArchivo);
			writer.write();
			
			Paths.get(nombreArchivo);		
	}
	
	@Test 
	public void writePdfMovimientosExcepcionTransformerCatcheada() throws IOException {
				
		String nombreArchivo = "src/test/resources/prueba_writer.pdf";
		BaseWriter writer = new PdfWriter(nombreArchivo, new TransformadorMovimientos(new ListaMovimientos().getMovimientos(), "movimientos2.xsl"));
		writer.write();
		
	}
	
}
