package ar.edu.untref.aydoo;

public class Barco {

	private int tamanio;
	private int parteDaniada;

	public Barco(int tamanioBarco) {
		this.tamanio = tamanioBarco;
		this.parteDaniada = 0;
	}

	public int obtenerTamanio() {		
		return this.tamanio;
	}

	public void tocado() {
		if(this.tamanio > this.parteDaniada) {
			this.parteDaniada++;
		}		
	}

	public boolean estaHundido() {
		return this.parteDaniada == this.tamanio;
	}
	
}
