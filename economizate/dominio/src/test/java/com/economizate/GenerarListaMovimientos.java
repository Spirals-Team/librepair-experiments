package com.economizate;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.economizate.conector.ConectorCuenta;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Movimientos;
import com.economizate.servicios.Criterio;
import com.economizate.servicios.impl.AndCriterio;
import com.economizate.servicios.impl.EgresoCriterio;
import com.economizate.servicios.impl.IngresoCriterio;
import com.economizate.servicios.impl.OrCriterio;
import com.economizate.servicios.impl.RangoFechaCriterio;

public class GenerarListaMovimientos {

	private ConectorCuenta conector = new ConectorCuenta();
	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
			
	private Criterio crearCriterioMovimientosEntreFecha(String fechaDesde, String fechaHasta) throws ParseException {
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterioRangoFechas = new RangoFechaCriterio(fechaDesde, fechaHasta);
		Criterio criterio = new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), criterioRangoFechas);	
		return criterio;
	}
	
	private Movimientos cargarMovimientos() throws ParseException {		
		Movimientos movimientos = new Movimientos();
		movimientos.agregarMovimiento(new MovimientoMonetario("Luz", "Servicio", -15.0, formater.parse("20180413")));
		movimientos.agregarMovimiento(new MovimientoMonetario("Gas", "Servicio", -15.0, formater.parse("20180426")));
		movimientos.agregarMovimiento(new MovimientoMonetario("Sueldo", "Sueldo", 95.0, formater.parse("20180418")));
		movimientos.agregarMovimiento(new MovimientoMonetario("Tarjeta", "Gastos Generales", -70.0, formater.parse("20180423")));
		return movimientos;
	}
	
	private boolean verificarMovimientos(Movimientos movimientos ) throws ParseException {
		for(MovimientoMonetario mov : cargarMovimientos().getTodos()) {
			if(!movimientos.getTodos().contains(mov)) {
				return false;
			}
		}
		return true;
	}
	
	//Corresponde caso 1 de criterios de aceptación US 3
	@Test
	public void filtrarMovimientosFechaDesde01042018FechaHasta30042018Test() throws ParseException {
			
		Movimientos movimientos = conector.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/2018", "30/04/2018"));
		assertTrue(movimientos.getTodos().size() == cargarMovimientos().getTodos().size());
		assertTrue(movimientos.getTotal() == -5);
		assertTrue(verificarMovimientos(movimientos));
		
	}
	
	//Corresponde caso 2 de criterios de aceptación US 3
	@Test (expected=IllegalArgumentException.class)
	public void filtrarMovimientosFechaDesde01042018FechaHasta31032018Test() throws ParseException {
		
		conector.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/2018", "31/03/2018"));
	}
	
	//Corresponde caso 3 de criterios de aceptación US 3
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde01042018FechaHasta310420xxTest() throws ParseException {
			
		conector.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/2018", "30/04/20xx"));
	}
	
	//Corresponde caso 4 de criterios de aceptación US 3
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde010418FechaHasta31042018Test() throws ParseException {
		
		conector.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/18", "30/04/2018"));
	}
	
	//Corresponde caso 5 de criterios de aceptación US 3
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde29022018FechaHasta31042018Test() throws ParseException {
		
		conector.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("29/02/2018", "30/04/2018"));
	}

	//Corresponde caso 6 de criterios de aceptación US 3
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde29022018FechaHastaNullTest() throws ParseException {
		
		conector.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("27/02/2018", null));
	}
	
	//Corresponde caso 7 de criterios de aceptación US 3
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesdeVaciaFechaHasta30042018Test() throws ParseException {
		
		conector.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("", "30/04/2018"));
	}
}
