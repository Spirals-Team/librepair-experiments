package ar.edu.untref.aydoo;

import excepciones.ExcepcionDiasIncorrectos;
import excepciones.ExcepcionInteresIncorrecto;
import excepciones.ExcepcionMontoIncorrecto;

public class PlazoFijoTradicional extends Inversion{
	private double plazo;
	private double interes;
	private double monto;
	
	public PlazoFijoTradicional(double plazoIngresado, double interesIngresado, double montoIngresado) {
		this.plazo = plazoIngresado;
		this.interes = interesIngresado;
		this.monto = montoIngresado;
	}
	
	@Override
	public double calcularGanancia() {
		double gananciaObtenida = 0;
		if(plazo < 30) {
			throw new ExcepcionDiasIncorrectos();
		}
		if(monto <= 0) {
			throw new ExcepcionMontoIncorrecto();
		}
		if(interes <= 0) {
			throw new ExcepcionInteresIncorrecto();
		}
		
		double porcentajeEnDias = (this.plazo / 365);
		gananciaObtenida = ((this.monto * this.interes) / 100) * porcentajeEnDias;
		return gananciaObtenida;
	}
}
	