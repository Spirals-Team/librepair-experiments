package ar.edu.untref.aydoo;

import org.junit.Assert;
import org.junit.Test;


public class TestIntegral {
	
	@Test
	public void realizarUnPlazoFijoTradicionalDeberiaDevolver100DeGanancia() {	  
		Inversor jorge = new Inversor();
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(90,10, 1000);
		double valorEsperado = 100;
		
		jorge.realizarInversion(plazoFijoTradicional);		
		double valorObtenido = jorge.getGanancias();
		
		Assert.assertEquals(valorEsperado, valorObtenido, 0.1);
	}
}
