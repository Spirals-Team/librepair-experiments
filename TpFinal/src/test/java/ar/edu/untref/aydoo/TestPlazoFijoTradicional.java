package ar.edu.untref.aydoo;

import org.junit.Assert;
import org.junit.Test;

import excepciones.ExcepcionDiasIncorrectos;
import excepciones.ExcepcionInteresIncorrecto;
import excepciones.ExcepcionMontoIncorrecto;

public class TestPlazoFijoTradicional {
	@Test
	public void realizarUnPlazoFijoTradicionalDeberiaDevolver50000DeGananciaCuandoElMontoEs50000YElInteresEs10() {	  
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(365, 10, 500000);
		double valorEsperado = 50000;
		
		double valorObtenido = plazoFijoTradicional.calcularGanancia();
		
		Assert.assertEquals(valorEsperado, valorObtenido, 0.1);
	}
	
	@Test	
	public void realizarUnPlazoFijoTradicionalDeberiaDevolver12500DeGananciaCuandoElMontoEs5000YElInteresEs25() {	  
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(90, 40, 250000);
		double valorEsperado = 24657.5;
		
		double valorObtenido = plazoFijoTradicional.calcularGanancia();
		
		Assert.assertEquals(valorEsperado, valorObtenido, 0.1);
	}

	@Test
	public void realizarUnPlazoFijoTradicionalDe10DiasDeberiaLanzarExcepcionDiasIncorrectos() {	  
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(10, 25, 50000);
		
		try {
			plazoFijoTradicional.calcularGanancia();
		}catch (ExcepcionDiasIncorrectos e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoTradicionalDe29DiasDeberiaLanzarExcepcionDiasIncorrectos() {	  
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(29, 25, 50000);
		
		try {
			plazoFijoTradicional.calcularGanancia();
		}catch (ExcepcionDiasIncorrectos e){

	    }
	}

	@Test
	public void realizarUnPlazoFijoTradicionalDeMonto0DeberiaLanzarExcepcionMontoIncorrecto() {	  
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(50, 25, 0);
		
		try {
			plazoFijoTradicional.calcularGanancia();
		}catch (ExcepcionMontoIncorrecto e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoTradicionalDeMonto1NegativoDeberiaLanzarExcepcionMontoIncorrecto() {	  
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(50, 25, -1);
		
		try {
			plazoFijoTradicional.calcularGanancia();
		}catch (ExcepcionMontoIncorrecto e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoTradicionalDeInteres0DeberiaLanzarExcepcionInteresIncorrecto() {	  
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(50, 0, 1000);
		
		try {
			plazoFijoTradicional.calcularGanancia();
		}catch (ExcepcionInteresIncorrecto e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoTradicionalDeInteres1NegativoDeberiaLanzarExcepcionInteresIncorrecto() {	  
		PlazoFijoTradicional plazoFijoTradicional = new PlazoFijoTradicional(50, -1, 1000);
		
		try {
			plazoFijoTradicional.calcularGanancia();
		}catch (ExcepcionInteresIncorrecto e){

	    }
	}
}
