package com.economizate;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.xml.bind.ValidationException;

import junit.framework.Assert;

import org.junit.Test;

import com.economizate.conector.ConectorCuenta;
import com.economizate.datos.ListaMovimientos;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.BaseWriter;
import com.economizate.servicios.IConversor;
import com.economizate.servicios.IConversorMovimiento;
import com.economizate.servicios.IParserRegistro;
import com.economizate.servicios.LoaderFromFile;
import com.economizate.servicios.LoaderToFile;
import com.economizate.servicios.impl.ConversorMovimientoSinCuota;
import com.economizate.servicios.impl.ConvertListaMovimientosToString;
import com.economizate.servicios.impl.ExcelWriter;
import com.economizate.servicios.impl.LoaderMovimientosFromFile;
import com.economizate.servicios.impl.LoaderMovimientosToFile;
import com.economizate.servicios.impl.MovimientosSheet;
import com.economizate.servicios.impl.ParserRegistroFechaSinCuota;
import com.economizate.servicios.impl.PdfWriter;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.TXTWriter;
import com.economizate.servicios.impl.TransformadorMovimientos;

public class GenerarArchivoMovimientosTest {

	private String rutaArchivos = Propiedad.getInstance().getPropiedad("resourcesTesting");
	
	private LoaderFromFile<MovimientoMonetario> importador;
	
	private LoaderToFile<MovimientoMonetario> generadorArchivo;
	
	private IParserRegistro parser;
	
	private ConectorCuenta conector = new ConectorCuenta();
	
	private void importarArchivo(String nombreArchivo) throws IOException, ParseException {
		importador = new LoaderMovimientosFromFile(nombreArchivo);
		parser = new ParserRegistroFechaSinCuota();
		importador.cargarDatos(parser);
	}
	
	private boolean verificacionMovimientosArchivo(String nombreArchivo) throws ValidationException, IOException, ParseException {
		importarArchivo(nombreArchivo);
		if(conector.getMovimientos().getTodos().size() != importador.getDatos().size()) 
			return false;
		for (MovimientoMonetario mov : importador.getDatos()) {
			if(!conector.getMovimientos().getTodos().contains(mov))
				return false;
		}
		return true;
	}
	
	private boolean existeArchivo(String nombreArchivo) {
		Path path = Paths.get(nombreArchivo);
		return Files.exists(path);
	}
	
	private boolean escribirArchivo(String nombreArchivo) throws IOException, ParseException {
		generadorArchivo = new LoaderMovimientosToFile();		
		generadorArchivo.cargarDatos(conector.getMovimientos().getTodos());
		generadorArchivo.generarArchivo(nombreArchivo);
		//writer.write();
		return existeArchivo(nombreArchivo);
	}
	
	private void eliminarArchivo(String nombreArchivo) {
		File file = new File(nombreArchivo);
		if(existeArchivo(nombreArchivo)) {
			file.delete();
		}       
	}
	
	//Corresponde caso 1 de criterios de aceptación US 4
	@Test
	public void generarTxtHistorialMovimientos() throws IOException, ValidationException, ParseException {
		String nombreArchivo = rutaArchivos + "movimientos.txt";
		eliminarArchivo(nombreArchivo);
		assertFalse(existeArchivo(nombreArchivo));		
		//assertTrue(escribirArchivo(nombreArchivo));
		generadorArchivo = new LoaderMovimientosToFile();		
		generadorArchivo.cargarDatos(conector.getMovimientos().getTodos(), new ConversorMovimientoSinCuota(";"));
		generadorArchivo.generarArchivo(nombreArchivo);
		/*BaseWriter writer = new TXTWriter(nombreArchivo);
		IConversorMovimiento conversor = new ConversorMovimientoSinCuota(";");
		writer.write(ConvertListaMovimientosToString.getRegistros(conector.getMovimientos().getTodos(), conversor));*/
		assertTrue(existeArchivo(nombreArchivo));
		assertTrue(verificacionMovimientosArchivo(nombreArchivo));
		
	}	
	
	//Corresponde caso 2 de criterios de aceptación US 4
	@Test
	public void generarExcelHistorialMovimientos() throws IOException, ParseException, ValidationException {
		
		String nombreArchivo = rutaArchivos + "movimientos.xlsx";
		eliminarArchivo(nombreArchivo);
		assertFalse(existeArchivo(nombreArchivo));
		assertTrue(escribirArchivo(nombreArchivo));
		assertTrue(verificacionMovimientosArchivo(nombreArchivo));
	}

	//Corresponde caso 3 de criterios de aceptación US 4
	@Test
	public void generarPdfHistorialMovimientos() throws IOException, ParseException {
				
		String nombreArchivo = rutaArchivos + "movimientos.pdf";
		eliminarArchivo(nombreArchivo);
		assertFalse(existeArchivo(nombreArchivo));
		//BaseWriter writer = new PdfWriter(nombreArchivo, new TransformadorMovimientos(new ListaMovimientos().getMovimientos(), "Movimientos.xsl"));		
		Assert.assertTrue(escribirArchivo(nombreArchivo));
	}
	
	
}