package ar.edu.untref.aydoo;

import excepciones.ExcepcionDiasIncorrectos;
import excepciones.ExcepcionInteresIncorrecto;
import excepciones.ExcepcionMontoIncorrecto;
import excepciones.ExcepcionPlazoRealMayorAInicial;

public class PlazoFijoPrecancelable {	
	public double calcularGanancia(int plazoInicial, int plazoReal, double interes, double monto) {
		double gananciaObtenida = 0;
		if(plazoInicial < 30) {
			throw new ExcepcionDiasIncorrectos();
		}
		if(plazoReal < 0) {
			throw new ExcepcionDiasIncorrectos();
		}
		if(monto <= 0) {
			throw new ExcepcionMontoIncorrecto();
		}
		if(interes <= 0) {
			throw new ExcepcionInteresIncorrecto();
		}
		if(plazoReal > plazoInicial) {
			throw new ExcepcionPlazoRealMayorAInicial();
		}

		
		gananciaObtenida = (monto * interes) / 100; 
		if(plazoInicial < plazoReal) {
			gananciaObtenida = gananciaObtenida/2;
		}
		return gananciaObtenida;
	}
}
