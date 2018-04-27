package com.economizate;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import javax.xml.bind.ValidationException;
import junit.framework.Assert;
import org.junit.Test;
import com.economizate.datos.ListaMovimientos;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.BaseWriter;
import com.economizate.servicios.IConversorMovimiento;
import com.economizate.servicios.IParserRegistro;
import com.economizate.servicios.LoaderFromFile;
import com.economizate.servicios.impl.ConversorMovimientoSinCuota;
import com.economizate.servicios.impl.ConvertListaMovimientosToString;
import com.economizate.servicios.impl.ExcelWriter;
import com.economizate.servicios.impl.LoaderMovimientosFromFile;
import com.economizate.servicios.impl.MovimientosSheet;
import com.economizate.servicios.impl.ParserRegistroFechaSinCuota;
import com.economizate.servicios.impl.PdfWriter;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.TXTWriter;
import com.economizate.servicios.impl.TransformadorMovimientos;

public class GenerarArchivoMovimientosTest {

	private String rutaArchivos = Propiedad.getInstance().getPropiedad("resourcesTesting");
	
	private LoaderFromFile<MovimientoMonetario> importador;
	
	private IParserRegistro parser;
	
	private void importarArchivo(String nombreArchivo) throws IOException, ParseException {
		importador = new LoaderMovimientosFromFile(nombreArchivo);
		parser = new ParserRegistroFechaSinCuota();
		importador.cargarDatos(parser);
	}
	
	private boolean verificacionMovimientosArchivo(String nombreArchivo) throws ValidationException, IOException, ParseException {
		importarArchivo(nombreArchivo);
		if(new ListaMovimientos().getMovimientos().size() != importador.getDatos().size()) 
			return false;
		for (MovimientoMonetario mov : importador.getDatos()) {
			if(!new ListaMovimientos().getMovimientos().contains(mov))
				return false;
		}
		return true;
	}
	
	private boolean existeArchivo(String nombreArchivo) {
		Path path = Paths.get(nombreArchivo);
		return Files.exists(path);
	}
	
	private boolean escribirArchivo(String nombreArchivo, BaseWriter writer) throws IOException {
		writer.write();
		return existeArchivo(nombreArchivo);
	}
	
	@Test
	public void generarExcelHistorialMovimientos() throws IOException, ParseException, ValidationException {
		
		String nombreArchivo = rutaArchivos + "movimientos.xlsx";
		assertTrue(escribirArchivo(nombreArchivo, new ExcelWriter(nombreArchivo, new MovimientosSheet(new ListaMovimientos().getMovimientos()))));
		assertTrue(verificacionMovimientosArchivo(nombreArchivo));
	}
	
	@Test
	public void generarTxtHistorialMovimientos() throws IOException, ValidationException, ParseException {
		
		String nombreArchivo = rutaArchivos + "movimientos.txt";
		BaseWriter writer = new TXTWriter(nombreArchivo);
		IConversorMovimiento conversor = new ConversorMovimientoSinCuota(";");
		writer.write(ConvertListaMovimientosToString.getRegistros(new ListaMovimientos().getMovimientos(), conversor));
		assertTrue(existeArchivo(nombreArchivo));
		assertTrue(verificacionMovimientosArchivo(nombreArchivo));
	}	
	
	@Test
	public void generarPdfMovimientos() throws IOException {
				
		String nombreArchivo = rutaArchivos + "movimientos.pdf";
		BaseWriter writer = new PdfWriter(nombreArchivo, new TransformadorMovimientos(new ListaMovimientos().getMovimientos(), "movimientos.xsl"));		
		Assert.assertTrue(escribirArchivo(nombreArchivo, writer));
	}

}
