package com.economizate;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.economizate.conector.ConectorSaldo;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.Criterio;
import com.economizate.servicios.impl.AndCriterio;
import com.economizate.servicios.impl.EgresoCriterio;
import com.economizate.servicios.impl.IngresoCriterio;
import com.economizate.servicios.impl.OrCriterio;
import com.economizate.servicios.impl.RangoFechaCriterio;
import com.economizate.servicios.impl.SaldosImpl;

public class CriteriosFiltroTest {
	

	private ConectorSaldo conectorSaldo = new ConectorSaldo();
	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
	
	@Test
	public void filtrarMovimientosPorFechaYObtenerListaOK() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		Date desde = formater.parse("20180301");
		Date hasta = formater.parse("20180501");
		Criterio criterio = new RangoFechaCriterio(desde, hasta);
		List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(lista);
		 
		assertTrue("Lista filtrada por fecha ", listaFiltrada.get(0).getFecha().before(hasta) &&
				listaFiltrada.get(0).getFecha().after(desde));
	}
	
	@Test
	public void filtrarMovimientosPorIngresoYObtenerListaIngresosOK() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();
		Criterio criterio = new IngresoCriterio();
		List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(lista);
		assertTrue("Lista filtrada por ingresos ", listaFiltrada.get(0).getImporte() > 0);
	}
	
	@Test
	public void filtrarMovimientosPorEgresoYObtenerListaEgresosOK() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();
		Criterio criterio = new EgresoCriterio();
		List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(lista);
		
		assertTrue("Lista filtrada por egresos ", listaFiltrada.get(0).getImporte() < 0);
	}

	@Test
	public void filtrarMovimientosPorFechaEIngresosYObtenerListaOK() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();
		
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
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();
		int cantidadTotal = lista.size();
		
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterio = new OrCriterio(criterioIngresos, criterioEgresos);
		List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(lista);
		
		assertTrue("Lista filtrada por ingresos y egresos ", listaFiltrada.size() == cantidadTotal);
	}
	
	@Test
	public void filtrarMovimientosFechaDesde01042018FechaHasta30042018DateTest() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();
		
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterioRangoFechas = new RangoFechaCriterio(formater.parse("20180401"), formater.parse("20180430"));
		Criterio criterio = new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), criterioRangoFechas);		
		assertTrue(criterio.filtrarMovimientos(lista).size() == 4);
		
	}
	
	@Test
	public void filtrarMovimientosFechaDesde01042018FechaHasta30042018StringTest() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();
		
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterioRangoFechas = new RangoFechaCriterio("01/04/2018", "30/04/2018");
		Criterio criterio = new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), criterioRangoFechas);		
		System.out.println(new SaldosImpl().obtenerTotalMovimientos(criterio.filtrarMovimientos(lista)));
		assertTrue(new SaldosImpl().obtenerTotalMovimientos(criterio.filtrarMovimientos(lista)) == 18463);
		
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void filtrarMovimientosFechaDesde01042018FechaHasta31032018Test() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();
		
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterioRangoFechas = new RangoFechaCriterio(formater.parse("20180401"), formater.parse("20180331"));
		Criterio criterio = new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), criterioRangoFechas);		
		criterio.filtrarMovimientos(lista);
		
	}
	
	
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde01042018FechaHasta310420xxTest() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();		
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		
		new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), new RangoFechaCriterio("01/04/2018", "31/04/20xx")).filtrarMovimientos(lista);		

	}
	
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde010418FechaHasta31042018Test() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();		
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterioRangoFechas = new RangoFechaCriterio("01/04/18", "30/04/2018");
		Criterio criterio = new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), criterioRangoFechas);		
		criterio.filtrarMovimientos(lista);		
	}
	
	@Test (expected=ParseException.class)
	public void filtrarMovimientosFechaDesde29022018FechaHasta31042018Test() throws ParseException {
		List<MovimientoMonetario> lista = conectorSaldo.nuevoSaldo().getMovimientos();		
		Criterio criterioIngresos = new IngresoCriterio();
		Criterio criterioEgresos = new EgresoCriterio();
		Criterio criterioRangoFechas = new RangoFechaCriterio("29/02/2018", "30/04/2018");
		Criterio criterio = new AndCriterio(new OrCriterio(criterioIngresos, criterioEgresos), criterioRangoFechas);		
		criterio.filtrarMovimientos(lista);		
	}
	
}
