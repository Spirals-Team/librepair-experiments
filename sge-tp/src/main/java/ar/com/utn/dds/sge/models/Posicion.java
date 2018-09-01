package ar.com.utn.dds.sge.models;

public class Posicion {
	
	/**
	 * Retorna distancia a otra posicion
	 * @param otraPosicion
	 * @return
	 */
	public static Double getDistancia(Double unaX, Double unaY, Double otraX, Double otraY) {
		Double dist = Math.sqrt (Math.pow((unaX - otraX), 2) +
				   	 Math.pow((unaY - otraY), 2));
		return dist;
	}

}
