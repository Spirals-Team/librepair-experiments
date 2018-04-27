package com.economizate;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.economizate.servicios.impl.CuentaImpl;

public class CriteriosFiltroTest {
	
	private ConectorCuenta conectorCuenta = new ConectorCuenta();
	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
	
	private Criterio crearCriterioEntreFecha(String fechaDesde, String fechaHasta) throws ParseException {
		Criterio criterioRangoFechas = new RangoFechaCriterio(fechaDesde, fechaHasta);	
		return criterioRangoFechas;
	}
	
	private Criterio crearCriterioMovimientosEntreFecha(String fechaDesde, String fechaHasta) throws ParseException {
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterio = new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), crearCriterioEntreFecha(fechaDesde, fechaHasta));	
		return criterio;
	}
	@Test
	public void filtrarMovimientosFechaDesde01042018FechaHasta30042018Test() throws ParseException {
			
		Movimientos movimientos = conectorCuenta.getMovimientos().filtrarPorCriterio(crearCriterioMovimientosEntreFecha("01/04/2018", "30/04/2018"));
		assertTrue(movimientos.getTodos().size() == 4);
		assertTrue(movimientos.getTotal() == 18463);		
	}
	
	/*
	@Test
	public void filtrarMovimientosPorFechaYObtenerListaOK() throws ParseException {
		
		
		Movimientos movimientos = conectorCuenta.getMovimientos().filtrarPorCriterio(crearCriterioEntreFecha("20180301", "20180501"));
				 
		assertTrue("Lista filtrada por fecha ", listaFiltrada.get(0).getFecha().before(hasta) &&
				listaFiltrada.get(0).getFecha().after(desde));
	}
	
	@Test
	public void filtrarMovimientosPorIngresoYObtenerListaIngresosOK() throws ParseException {
		List<MovimientoMonetario> lista = conectorCuenta.getMovimientos();
		Criterio criterio = new IngresoCriterio();
		List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(lista);
		assertTrue("Lista filtrada por ingresos ", listaFiltrada.get(0).getImporte() > 0);
	}
	
	@Test
	public void filtrarMovimientosPorEgresoYObtenerListaEgresosOK() throws ParseException {
		List<MovimientoMonetario> lista = conectorCuenta.getMovimientos();
		Criterio criterio = new EgresoCriterio();
		List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(lista);
		
		assertTrue("Lista filtrada por egresos ", listaFiltrada.get(0).getImporte() < 0);
	}

	@Test
	public void filtrarMovimientosPorFechaEIngresosYObtenerListaOK() throws ParseException {
		List<MovimientoMonetario> lista = conectorCuenta.getMovimientos();
		
		Date desde = formater.parse("20180301");
		Date hasta = formater.parse("20180501");
		Criterio criterioFechas = new RangoFechaCriterio(desde, hasta);
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterio = new AndCriterio(criterioIngresos, criterioFechas);
		List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(lista);
		 
		assertTrue("Lista filtrada por ingresos y fecha ", listaFiltrada.get(0).getFecha().before(hasta) &&
				listaFiltrada.get(0).getFecha().after(desde) && listaFiltrada.get(0).getImporte() > 0);
	}
	
	@Test
	public void filtrarMovimientosPorEgresosOIngresosYObtenerListaOK() throws ParseException {
		List<MovimientoMonetario> lista = conectorCuenta.getMovimientos();
		int cantidadTotal = lista.size();
		
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterio = new OrCriterio(criterioIngresos, criterioEgresos);
		List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(lista);
		
		assertTrue("Lista filtrada por ingresos y egresos ", listaFiltrada.size() == cantidadTotal);
	}*/
	
		
}
