package ar.edu.untref.aydoo;

import org.junit.Assert;
import org.junit.Test;



public class TestTablero {
	
	@Test
	public void sfsdf() {
		Tablero tablero = new Tablero();
		
		for(int columna = 0; columna < tablero.cantidadColumnas(); columna++) {
			for(int fila = 0; fila < tablero.cantidadFilas(); fila++) {
				Assert.assertFalse(tablero.hayBarcoEnPosicion(columna, fila));
			}
		}
	}

}
