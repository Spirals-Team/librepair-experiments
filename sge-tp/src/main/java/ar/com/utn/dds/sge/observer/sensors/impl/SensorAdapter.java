package ar.com.utn.dds.sge.observer.sensors.impl;

import java.util.List;

import ar.com.utn.dds.sge.observer.sensors.ISensor;
import ar.com.utn.dds.sge.observer.sensors.SensorObserver;
import ar.com.utn.dds.sge.observer.sensors.SensorSGE;

public class SensorAdapter extends SensorSGE {

	private ISensor sensor;
	
	public SensorAdapter(String magnitud, int periodoEnMilis, List<SensorObserver> observadores, ISensor sensor) {
		super(magnitud, periodoEnMilis, observadores);
		this.sensor = sensor;
	}

	@Override
	public Comparable getValor() {
		//Se obtiene medicion
		Object medicion = sensor.obtenerValor();
		
		//Se interpreta medicion segun el formato si es xml, json, bits, etc.
		//TODO: Terminar metodo
		
		return null;
	}

}
