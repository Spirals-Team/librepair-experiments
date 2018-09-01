package ar.com.utn.dds.sge.test;

import org.junit.Before;
import org.junit.Test;

import com.sun.el.parser.ParseException;

import static org.junit.Assert.*;

import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;

public class ZonasTest {
	
	CasosDePruebaBuilder prueba = new CasosDePruebaBuilder();
	
	@Before
	public void crearDatosDePrueba() throws ParseException, java.text.ParseException {
		prueba.crearDatosDePrueba();
	}
	
	@Test
	public void perteneceTransformadorALaZonaTest() {
		assertTrue(prueba.zona1.perteneceTransformadorALaZona(prueba.transformador1));
	}
	
	@Test
	public void noPerteneceTransformadorALaZonaTest() {
		assertFalse(prueba.zona2.perteneceTransformadorALaZona(prueba.transformador1));
	}
	
	@Test
	public void consumoFinalDeLaZonaTest() {
		prueba.zona1.agregarTransformador(prueba.transformador1);
		prueba.zona1.agregarTransformador(prueba.transformador2);
		assertEquals(prueba.zona1.consumo(), 0.0, 0.1);
	}
}
