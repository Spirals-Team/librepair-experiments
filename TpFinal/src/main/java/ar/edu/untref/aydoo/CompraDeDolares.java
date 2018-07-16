package ar.edu.untref.aydoo;

import excepciones.ExcepcionCotizacionDolarIncorrecta;
import excepciones.ExcepcionMontoMenorACotizacionDolar;

public class CompraDeDolares {
	public double calcularGanancia(double montoPesosInicial, double cotizacionInicial, double cotizacionFinal) {
		double gananciaObtenida = 0;	
		if(montoPesosInicial < cotizacionInicial) {
			throw new ExcepcionMontoMenorACotizacionDolar();
		}
		if(cotizacionInicial <= 0 || cotizacionFinal <= 0) {
			throw new ExcepcionCotizacionDolarIncorrecta();
		}
		double montoDolares = montoPesosInicial / cotizacionInicial;
		double montoPesosFinal = montoDolares * cotizacionFinal;
		gananciaObtenida = montoPesosFinal - montoPesosInicial;
		return gananciaObtenida;
	}
}
