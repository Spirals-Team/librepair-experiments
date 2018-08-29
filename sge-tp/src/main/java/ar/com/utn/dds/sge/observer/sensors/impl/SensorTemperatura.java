package ar.com.utn.dds.sge.observer.sensors.impl;

import ar.com.utn.dds.sge.observer.sensors.ISensor;

/**
 * Clase de ejemplo que representaria un sensor real
 * @author root
 *
 */
public class SensorTemperatura implements ISensor {

	private Float medicion;
	
	@Override
	public Object obtenerValor() {
		
		return medicion;
	}

	/**
	 * @param medicion the medicion to set
	 */
	public void setMedicion(Float medicion) {
		this.medicion = medicion;
	}
	
	

}
