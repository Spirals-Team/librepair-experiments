package com.economizate;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.economizate.conector.ConectorCuenta;
import com.economizate.entidades.Movimientos;
import com.economizate.servicios.Criterio;
import com.economizate.servicios.impl.AndCriterio;
import com.economizate.servicios.impl.EgresoCriterio;
import com.economizate.servicios.impl.IngresoCriterio;
import com.economizate.servicios.impl.OrCriterio;
import com.economizate.servicios.impl.RangoFechaCriterio;

public class GenerarListaMovimientos {

	private ConectorCuenta conectorCuenta = new ConectorCuenta();
	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		
	
	private Criterio crearCriterioMovimientosEntreFecha(String fechaDesde, String fechaHasta) throws ParseException {
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterioRangoFechas = new RangoFechaCriterio(fechaDesde, fechaHasta);
		Criterio criterio = new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), criterioRangoFechas);	
		return criterio;
	}
	
	@Test
	public void filtrarMovimientosFechaDesde01042018FechaHasta30042018DateTest() throws ParseException {
		
		Criterio criterio = crearCriterioMovimientosEntreFecha("01/04/2018", "30/04/2018");		
		Movimientos movimientos = conectorCuenta.getMovimientos().filtrarPorCriterio(criterio);
		
		assertTrue(movimientos.getTodos().size() == 4);
	}
	
	@Test
	public void filtrarMovimientosFechaDesde01042018FechaHasta30042018Test() throws ParseException {
			

		Movimientos movimientos = conectorCuenta.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/2018", "30/04/2018"));
		assertTrue(movimientos.getTodos().size() == 4);
		assertTrue(movimientos.getTotal() == 18463);
		
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void filtrarMovimientosFechaDesde01042018FechaHasta31032018Test() throws ParseException {
		
		conectorCuenta.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/2018", "31/03/2018"));
	}
	
	
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde01042018FechaHasta310420xxTest() throws ParseException {
			
		conectorCuenta.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/2018", "31/04/20xx"));
	}
	
	
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde010418FechaHasta31042018Test() throws ParseException {
		
		conectorCuenta.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/18", "30/04/2018"));
	}
	
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde29022018FechaHasta31042018Test() throws ParseException {
		
		conectorCuenta.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("29/02/2018", "30/04/2018"));
	}

}
