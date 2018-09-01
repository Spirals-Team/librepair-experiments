package ar.com.utn.dds.sge.test;

import org.junit.Before;
import org.junit.Test;

import com.sun.el.parser.ParseException;

import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;

public class TransformadoresTest {

	CasosDePruebaBuilder prueba = new CasosDePruebaBuilder();
	
	@Before
	public void crearDatosDePrueba() throws ParseException, java.text.ParseException {
		prueba.crearDatosDePrueba();
	}
	
	@Test
	public void transformadorCercanoAClienteTest() {
		
	}
	
	
}
