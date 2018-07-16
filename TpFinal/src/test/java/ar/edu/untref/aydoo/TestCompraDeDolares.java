package ar.edu.untref.aydoo;

import org.junit.Assert;
import org.junit.Test;

import excepciones.ExcepcionCotizacionDolarIncorrecta;
import excepciones.ExcepcionMontoMenorACotizacionDolar;

public class TestCompraDeDolares {
	@Test
	public void realizarLaCompraDeDolaresCon1000PesosCotizacionInicial20CotizacionFinal28DeberiaDevolverGananciaDe400() {	  
		CompraDeDolares comprarDolares = new CompraDeDolares();
		double valorEsperado = 400;
		
		double valorObtenido = comprarDolares.calcularGanancia(1000, 20, 28);
		
		Assert.assertEquals(valorEsperado, valorObtenido, 0.1);
	}

	@Test
	public void realizarLaCompraDeDolaresCon10PesosCotizacionInicial20DeberiaLanzarExcepcionMontoMenorACotizacionDolar() {	  
		CompraDeDolares comprarDolares = new CompraDeDolares();
		
		try {
			comprarDolares.calcularGanancia(10, 20, 28);
		}catch (ExcepcionMontoMenorACotizacionDolar e){

	    }		
	}
	
	@Test
	public void realizarLaCompraDeDolaresConCotizacionInicial0DeberiaLanzarExcepcionCotizacionDolarIncorrecta() {	  
		CompraDeDolares comprarDolares = new CompraDeDolares();
		
		try {
			comprarDolares.calcularGanancia(10, 0, 28);
		}catch (ExcepcionCotizacionDolarIncorrecta e){

	    }		
	}
	
	@Test
	public void realizarLaCompraDeDolaresConCotizacionFinal0DeberiaLanzarExcepcionCotizacionDolarIncorrecta() {	  
		CompraDeDolares comprarDolares = new CompraDeDolares();
		
		try {
			comprarDolares.calcularGanancia(10, 0, 28);
		}catch (ExcepcionCotizacionDolarIncorrecta e){

	    }		
	}
}