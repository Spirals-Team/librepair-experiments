package ar.edu.untref.aydoo;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class TestCasillero {
	
	@Test
	public void casilleroVacioDeberiaEstarVacio() {
		Casillero casillero = new Casillero();
		
		Assert.assertTrue(casillero.estaVacio());
	}
	
	@Test
	public void casilleroConBarcoNoEstaVacio() {
		Casillero casillero = new Casillero();
		Barco barco = new Barco(23);
		casillero.ponerBarco(barco);
		
		Assert.assertFalse(casillero.estaVacio());
	}

}
