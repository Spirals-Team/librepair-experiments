package com.economizate;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.ValidationException;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IParserRegistro;
import com.economizate.servicios.LoaderFromFile;
import com.economizate.servicios.Cuenta;
import com.economizate.servicios.impl.ConvertToMovimiento;
import com.economizate.servicios.impl.LoaderMovimientosFromFile;
import com.economizate.servicios.impl.ParserRegistroConCuota;
import com.economizate.servicios.impl.ParserRegistroFechaSinCuota;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.CuentaImpl;

public class ImportadorArchivoTest {

	private String rutaArchivos = Propiedad.getInstance().getPropiedad("resourcesTesting");
	
	private LoaderFromFile<MovimientoMonetario> importador;
	private Cuenta cuenta;
	private IParserRegistro parser;
	
	private ConvertToMovimiento convert = new ConvertToMovimiento();
	
	private void importarArchivo(String nombreArchivo, IParserRegistro parser) throws IOException, ParseException {
		importador = new LoaderMovimientosFromFile(rutaArchivos + nombreArchivo);
		this.parser = parser;
		importador.cargarDatos(parser);
	}
	
	private void agregarMovimientosACuenta() throws ValidationException {
		for (MovimientoMonetario mov : importador.getDatos()) {
			cuenta.agregarMovimiento(mov);
		}
	}
	
	@Test
	public void cargarMovimientosDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_ok.txt", new ParserRegistroConCuota());
		cuenta  = new CuentaImpl();
		agregarMovimientosACuenta();
		
		Date date= new Date();		
		DateFormat dateFormat = new SimpleDateFormat("MM");
		int mes = Integer.parseInt(dateFormat.format(date));
		dateFormat = new SimpleDateFormat("yyyy");
		int anio = Integer.parseInt(dateFormat.format(date));
		System.out.println(cuenta.obtenerSaldoTotalPorPeriodo(mes, anio));	
		assertTrue(cuenta.obtenerSaldoTotalPorPeriodo(mes, anio) == 17310.5);
	}

	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosImporteInvalidoDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoImporteInvalido.txt", new ParserRegistroConCuota());
	}
	
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosCuotaInvalidoDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoCuotaInvalido.txt", new ParserRegistroConCuota());
	}
	
	@Test (expected=ParseException.class)
	public void cargarMovimientosCantCamposInvalidaDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_cantidadCamposInvalida.txt", new ParserRegistroConCuota());
	}
	
	@Test
	public void cargarMovimientosDesdeArchivoTxtVacio() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_vacio.txt", new ParserRegistroConCuota());
		assertTrue(importador.getDatos().size() == 0);
	}
	
	@Test
	public void cargarMovimientosDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_ok.xlsx", new ParserRegistroConCuota());
		assertTrue(importador.getDatos().size() > 0);
	}
	
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosImporteInvalidoDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoImporteInvalido.xlsx", new ParserRegistroConCuota());
	}
	
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosCuotaInvalidoDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoCuotaInvalido.xlsx", new ParserRegistroConCuota());
	}
	
	@Test (expected=ParseException.class)
	public void cargarMovimientosCantCamposInvalidaDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_cantidadCamposInvalida.xlsx", new ParserRegistroConCuota());
	}
	
	@Test
	public void cargarMovimientosDesdeArchivoExcelVacio() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_vacio.xlsx", new ParserRegistroConCuota());
		assertTrue(importador.getDatos().size() == 0);
	}
	
	@Test (expected=ValidationException.class)
	public void cargarMovimientosDesdeArchivoTxtDescripcionVacia() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_descripcionVacia.txt", new ParserRegistroConCuota());
		assertTrue(importador.getDatos().size() == 0);
	}
	
	@Test (expected=ParseException.class)
	public void cargarMovimientosDesdeArchivoTxtFechaInvalida() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_fechaInvalida.txt", new ParserRegistroFechaSinCuota());
	}
}
