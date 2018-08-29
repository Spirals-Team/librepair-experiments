package ar.com.utn.dds.sge.rules;

import java.util.List;

import ar.com.utn.dds.sge.commands.Actuador;
import ar.com.utn.dds.sge.commands.Comando;
import ar.com.utn.dds.sge.conditions.Condicion;
import ar.com.utn.dds.sge.observer.AlarmaSensor;
import ar.com.utn.dds.sge.observer.sensors.SensorObserver;
import ar.com.utn.dds.sge.observer.sensors.SensorSGE;

public class Regla implements SensorObserver{

	private Condicion condicion;
	
	private List<Comando> acciones;
	
	private AlarmaSensor alarmaSensor;
	
	public Regla(Condicion condicion, List<Comando> acciones) {
		super();
		this.condicion = condicion;
		this.acciones = acciones;
	}

	@Override
	public void actualizar(SensorSGE sensor) {
		Comparable valor = sensor.getValor();
		
		if(condicion.comparar(valor)) {
			//Se cumple condicion de la regla
			this.ejecutarAcciones();
		}
	}

	private void ejecutarAcciones() {
		// TODO: Envia acciones a actuador
		alarmaSensor.notificarActuadores();
	}

	
	/**
	 * @return the condicion
	 */
	public Condicion getCondicion() {
		return condicion;
	}

	/**
	 * @param condicion the condicion to set
	 */
	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}

	/**
	 * @return the acciones
	 */
	public List<Comando> getAcciones() {
		return acciones;
	}

	/**
	 * @param acciones the acciones to set
	 */
	public void setAcciones(List<Comando> acciones) {
		this.acciones = acciones;
	}
	
	public void setAlarmaSensor(List<Actuador> actuadores) {
		for(Actuador actuador : actuadores) {
			this.alarmaSensor.agregarActuador(actuador);
		}
		
	}
	
}
