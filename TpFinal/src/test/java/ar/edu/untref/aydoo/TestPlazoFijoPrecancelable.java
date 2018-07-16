package ar.edu.untref.aydoo;

import org.junit.Assert;
import org.junit.Test;

import excepciones.ExcepcionDiasIncorrectos;
import excepciones.ExcepcionInteresIncorrecto;
import excepciones.ExcepcionMontoIncorrecto;
import excepciones.ExcepcionPlazoRealMayorAInicial;

public class TestPlazoFijoPrecancelable {	
	@Test
	public void realizarUnPlazoFijoPrecancelableDeberiaDevolver12500DeGananciaCuandoElMontoEs5000YElInteresEs25() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		double valorEsperado = 12500;
		
		double valorObtenido = plazoFijoPrecancelable.calcularGanancia(100, 100, 25, 50000);
		
		Assert.assertEquals(valorEsperado, valorObtenido, 0.1);
	}

	@Test
	public void realizarUnPlazoFijoPrecancelableDeberiaDevolver6250DeGananciaCuandoElMontoEs5000ElInteresEs25PlazoReal30() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		double valorEsperado = 12500;
		
		double valorObtenido = plazoFijoPrecancelable.calcularGanancia(100, 30, 25, 50000);
		
		Assert.assertEquals(valorEsperado, valorObtenido, 0.1);
	}

	@Test
	public void realizarUnPlazoFijoPrecancelableDe10DiasInicialesDeberiaLanzarExcepcionDiasIncorrectos() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		
		try {
			plazoFijoPrecancelable.calcularGanancia(10, 6, 25, 50000);
		}catch (ExcepcionDiasIncorrectos e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoPrecancelableDe29DiasInicialesDeberiaLanzarExcepcionDiasIncorrectos() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		
		try {
			plazoFijoPrecancelable.calcularGanancia(29, 6, 25, 50000);
		}catch (ExcepcionDiasIncorrectos e){

	    }
	}

	@Test
	public void realizarUnPlazoFijoPrecancelableDeMonto0DeberiaLanzarExcepcionMontoIncorrecto() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		
		try {
			plazoFijoPrecancelable.calcularGanancia(100, 30, 25, 0);
		}catch (ExcepcionMontoIncorrecto e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoPrecancelableDeMonto1NegativoDeberiaLanzarExcepcionMontoIncorrecto() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		
		try {
			plazoFijoPrecancelable.calcularGanancia(30, 15, 25, -1);
		}catch (ExcepcionMontoIncorrecto e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoPrecancelableDeInteres0DeberiaLanzarExcepcionInteresIncorrecto() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		
		try {
			plazoFijoPrecancelable.calcularGanancia(100, 100, 0, 50000);
		}catch (ExcepcionInteresIncorrecto e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoPrecancelableDeInteres1NegativoDeberiaLanzarExcepcionInteresIncorrecto() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		
		try {
			plazoFijoPrecancelable.calcularGanancia(100, 100, -1, 50000);
		}catch (ExcepcionInteresIncorrecto e){

	    }
	}
	
	@Test
	public void realizarUnPlazoFijoPrecancelableDe30DiasInicialesY60DiasRealesDeberiaLanzarExcepcionPlazoRealMayorAInicial() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		
		try {
			plazoFijoPrecancelable.calcularGanancia(30, 60, 25, 50000);
		}catch (ExcepcionPlazoRealMayorAInicial e){

	    }
	}

	@Test
	public void realizarUnPlazoFijoPrecancelableDe1NegativoDiasRealesDeberiaLanzarExcepcionDiasIncorrectos() {	  
		PlazoFijoPrecancelable plazoFijoPrecancelable = new PlazoFijoPrecancelable();
		
		try {
			plazoFijoPrecancelable.calcularGanancia(30, -1, 25, 50000);
		}catch (ExcepcionDiasIncorrectos e){

	    }
	}

}
