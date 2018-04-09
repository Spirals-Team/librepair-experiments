package ar.edu.untref.aydoo;

public class Tablero {

	private final int ancho = 5;
	private final int largo = 5;
	private Casillero[][] casilleros;
	
	public Tablero() {
		casilleros = new Casillero[ancho][largo];
	}
	
	public int cantidadColumnas() {
		return this.ancho;
	}

	public int cantidadFilas() {
		return this.largo;
	}

	public boolean hayBarcoEnPosicion(int columna, int fila) {
		// TODO Auto-generated method stub
		Casillero unCasillero = this.casilleros[fila][columna];

		return unCasillero.estaVacio();
	}
	
	

}
