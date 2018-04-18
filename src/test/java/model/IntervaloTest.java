package model;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntervaloTest {
	
	Intervalo intervalo;
	
	LocalDateTime antesDeFechaInicio;
	LocalDateTime despuesDeFechaInicio;
	
	LocalDateTime unMsAntes;
	LocalDateTime unMsDespues;
	
	LocalDateTime inicio;
	LocalDateTime fin;
	
    @Before
    public void setUp(){
    	
    	inicio = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
    	fin = LocalDateTime.of(2015, Month.JANUARY, 1, 10, 10, 30);
    	
    	intervalo = new Intervalo(inicio, fin);
    	
    	antesDeFechaInicio = LocalDateTime.of(2013, Month.JANUARY, 1, 10, 10, 30);
    	despuesDeFechaInicio = LocalDateTime.of(2016, Month.JANUARY, 1, 10, 10, 30);
    	
    	unMsAntes = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 29);
    	unMsDespues = LocalDateTime.of(2015, Month.JANUARY, 1, 10, 11, 35);
    	
    }


	@Test
	public void estaEnIntervalo() {
		
		Assert.assertFalse(intervalo.estaEnIntervalo(antesDeFechaInicio));
		Assert.assertFalse(intervalo.estaEnIntervalo(despuesDeFechaInicio));
		
		Assert.assertFalse(intervalo.estaEnIntervalo(unMsAntes));
		Assert.assertFalse(intervalo.estaEnIntervalo(unMsDespues));

		Assert.assertTrue(intervalo.estaEnIntervalo(inicio));
		Assert.assertTrue(intervalo.estaEnIntervalo(fin));
		
	}

}
