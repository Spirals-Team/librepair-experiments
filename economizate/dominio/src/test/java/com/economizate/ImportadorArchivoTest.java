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
import com.economizate.entidades.Movimientos;
import com.economizate.servicios.IParserRegistro;
import com.economizate.servicios.LoaderFromFile;
import com.economizate.servicios.Cuenta;
import com.economizate.servicios.impl.LoaderMovimientosFromFile;
import com.economizate.servicios.impl.ParserRegistroConCuota;
import com.economizate.servicios.impl.ParserRegistroFechaSinCuota;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.CuentaImpl;

public class ImportadorArchivoTest {

	private String rutaArchivos = Propiedad.getInstance().getPropiedad("resourcesTesting");
	
	private LoaderFromFile<MovimientoMonetario> importador;
	private Cuenta cuenta = new CuentaImpl();
	private IParserRegistro parser;
		
	
	private Movimientos cargarMovimientos() {		
		Movimientos movimientos = new Movimientos();
		movimientos.agregarMovimiento(new MovimientoMonetario("Luz", "Servicio", -742.0, 2));
		movimientos.agregarMovimiento(new MovimientoMonetario("Gas", "Servicio", -325.0, 0));
		movimientos.agregarMovimiento(new MovimientoMonetario("Sueldo", "Sueldo", 25744.0, 0));
		movimientos.agregarMovimiento(new MovimientoMonetario("Tarjeta", "Gastos Generales", -6214.0, 0));
		movimientos.agregarMovimiento(new MovimientoMonetario("Viaje Brasil", "Vacaciones", -1152.5, 5));
		return movimientos;
	}
	
	
	private int getCantidadCuotas() {
		int cuotas = 0;
		for(MovimientoMonetario mov : cargarMovimientos().getTodos()) {
			cuotas += mov.getCantidadCuotas();
		}
		return cuotas;
	}
	
	private void importarArchivo(String nombreArchivo, IParserRegistro parser) throws IOException, ParseException {
		importador = new LoaderMovimientosFromFile(rutaArchivos + nombreArchivo);
		this.parser = parser;
		importador.cargarDatos(parser);
	}
	
	private boolean verificarMovimientosArchivo() {
		for(MovimientoMonetario mov : cargarMovimientos().getTodos()) {
			if(!cuenta.obtenerMovimientos().getTodos().contains(mov)) {
				return false;
			}
		}
		return true;
	}
	
	private void agregarMovimientosACuenta() throws ValidationException {
		for (MovimientoMonetario mov : importador.getDatos()) {
			cuenta.agregarMovimiento(mov);
		}
	}
	
	private int getFechaActual(String format) {
		Date date= new Date();		
		DateFormat dateFormat = new SimpleDateFormat(format);
		return Integer.parseInt(dateFormat.format(date));
	}
	
	//Corresponde caso 1 de criterios de aceptación US 2 
	@Test
	public void cargarMovimientosDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_ok.txt", new ParserRegistroConCuota());
		assertTrue(cuenta.obtenerSaldoTotalPorPeriodo(getFechaActual("MM"), getFechaActual("yyyy")) == 0);
		agregarMovimientosACuenta();		
		assertTrue(cuenta.obtenerSaldoTotalPorPeriodo(getFechaActual("MM"), getFechaActual("yyyy")) == 17310.5);
		assertTrue(verificarMovimientosArchivo());
		assertTrue(cuenta.obtenerMovimientos().getTodos().size() == cargarMovimientos().getTodos().size() + getCantidadCuotas());
	}

	//Corresponde caso 2 de criterios de aceptación US 2 
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosImporteInvalidoDesdeArchivoTxt() throws IOException, ParseException, ValidationException {				
		importarArchivo("movimientos_formatoImporteInvalido.txt", new ParserRegistroConCuota());
	}
	
	//Corresponde caso 3 de criterios de aceptación US 2 
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosCuotaInvalidoDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoCuotaInvalido.txt", new ParserRegistroConCuota());
	}
	
	//Corresponde caso 4 de criterios de aceptación US 2 
	@Test (expected=ParseException.class)
	public void cargarMovimientosCantCamposInvalidaDesdeArchivoTxt() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_cantidadCamposInvalida.txt", new ParserRegistroConCuota());
	}
	
	//Corresponde caso 5 de criterios de aceptación US 2 
	@Test
	public void cargarMovimientosDesdeArchivoTxtVacio() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_vacio.txt", new ParserRegistroConCuota());
		agregarMovimientosACuenta();
		assertTrue(cuenta.obtenerSaldoTotalPorPeriodo(getFechaActual("MM"), getFechaActual("yyyy")) == 0);
	}
	
	//Corresponde caso 6 de criterios de aceptación US 2 
	@Test
	public void cargarMovimientosDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_ok.xlsx", new ParserRegistroConCuota());
		assertTrue(cuenta.obtenerSaldoTotalPorPeriodo(getFechaActual("MM"), getFechaActual("yyyy")) == 0);
		agregarMovimientosACuenta();		
		assertTrue(cuenta.obtenerSaldoTotalPorPeriodo(getFechaActual("MM"), getFechaActual("yyyy")) == 17310.5);
		assertTrue(verificarMovimientosArchivo());
		assertTrue(cuenta.obtenerMovimientos().getTodos().size() == cargarMovimientos().getTodos().size() + getCantidadCuotas());
	}
	
	//Corresponde caso 7 de criterios de aceptación US 2 
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosImporteInvalidoDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoImporteInvalido.xlsx", new ParserRegistroConCuota());
	}
	
	//Corresponde caso 8 de criterios de aceptación US 2
	@Test (expected=NumberFormatException.class)
	public void cargarMovimientosCuotaInvalidoDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_formatoCuotaInvalido.xlsx", new ParserRegistroConCuota());
	}
	
	//Corresponde caso 9 de criterios de aceptación US 2
	@Test (expected=ParseException.class)
	public void cargarMovimientosCantCamposInvalidaDesdeArchivoExcel() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_cantidadCamposInvalida.xlsx", new ParserRegistroConCuota());
	}
	
	//Corresponde caso 10 de criterios de aceptación US 2
	@Test
	public void cargarMovimientosDesdeArchivoExcelVacio() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_vacio.xlsx", new ParserRegistroConCuota());
		
		assertTrue(cuenta.obtenerSaldoTotalPorPeriodo(getFechaActual("MM"), getFechaActual("yyyy")) == 0);
	}
	
	//Corresponde caso 11 de criterios de aceptación US 2
	@Test (expected=ValidationException.class)
	public void cargarMovimientosDesdeArchivoTxtDescripcionVacia() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_descripcionVacia.txt", new ParserRegistroConCuota());
		assertTrue(importador.getDatos().size() == 0);
	}
	
	//Corresponde caso 12 de criterios de aceptación US 2
	@Test (expected=ParseException.class)
	public void cargarMovimientosDesdeArchivoTxtFechaInvalida() throws IOException, ParseException, ValidationException {		
		importarArchivo("movimientos_fechaInvalida.txt", new ParserRegistroFechaSinCuota());
	}
}
