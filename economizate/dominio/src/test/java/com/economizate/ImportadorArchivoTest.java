package com.economizate;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.ValidationException;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.LoaderFromFile;
import com.economizate.servicios.Saldos;
import com.economizate.servicios.impl.LoaderMovimientosFromFile;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.SaldosImpl;

public class ImportadorArchivoTest {

	private String rutaArchivos = Propiedad.getInstance().getPropiedad("resourcesTesting");
	
	private LoaderFromFile<MovimientoMonetario> importador;
	private Saldos cuenta;
	
	private void importarArchivo(String nombreArchivo) throws IOException, ParseException {
		importador = new LoaderMovimientosFromFile(rutaArchivos + nombreArchivo);
		importador.cargarDatos();
	}
	
	private void agregarMovimientosACuenta() throws ValidationException {
		for (MovimientoMonetario mov : importador.getDatos()) {
			cuenta.agregarMovimiento(mov);
		}
	}
	
	@Test
	public void cargarMovimientosDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_ok.txt");
		cuenta  = new SaldosImpl();
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
		importarArchivo("movimientos_formatoImporteInvalido.txt");
	}
	
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosCuotaInvalidoDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoCuotaInvalido.txt");
	}
	
	@Test (expected=ParseException.class)
	public void cargarMovimientosCantCamposInvalidaDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_cantidadCamposInvalida.txt");
	}
	
	@Test
	public void cargarMovimientosDesdeArchivoTxtVacio() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_vacio.txt");
		assertTrue(importador.getDatos().size() == 0);
	}
	
	@Test
	public void cargarMovimientosDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_ok.xlsx");
		assertTrue(importador.getDatos().size() > 0);
	}
	
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosImporteInvalidoDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoImporteInvalido.xlsx");
	}
	
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosCuotaInvalidoDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoCuotaInvalido.xlsx");
	}
	
	@Test (expected=ParseException.class)
	public void cargarMovimientosCantCamposInvalidaDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_cantidadCamposInvalida.xlsx");
	}
	
	@Test
	public void cargarMovimientosDesdeArchivoExcelVacio() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_vacio.xlsx");
		assertTrue(importador.getDatos().size() == 0);
	}
	
}
