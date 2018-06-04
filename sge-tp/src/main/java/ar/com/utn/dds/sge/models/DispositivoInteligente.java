package ar.com.utn.dds.sge.models;

import java.util.ArrayList;

import ar.com.utn.dds.sge.observer.sujetoObservable;

public class DispositivoInteligente extends Dispositivo implements sujetoObservable{
	private ArrayList<Sensor> sensores;
	private ArrayList<Integer> medicionesTemperatura;
	private Integer temperatura;
	private Integer intesidadLuminica;
	private Integer humedad;
	private boolean movimiento;

	


	
	
	
	public DispositivoInteligente(String tipo, String nombre, Float consumo, Boolean estado) {
		super();
		sensores = new ArrayList<Sensor>();
		medicionesTemperatura = new ArrayList<Integer>();
	}
	
	
	public Integer getHumedad() {
		return humedad;
	}

	public void setHumedad(Integer humedad) {
		this.humedad = humedad;
		this.notificar();
	}

	public ArrayList<Sensor> getObservadores() {
		return sensores;
	}

	public void agregarObservadores(Sensor sensor) {
		(this.sensores).add(sensor);
	}
	
	public void quitarObservadores(Cliente sensor) {
		(this.sensores).remove(sensor);
	}
	
	
	public Integer getTemperatura() {
		return temperatura;
	}

	
	public void setTemperatura(Integer temperatura) {
		//Al aumentar la temperatura mas de lo estipulado se le da aviso
		//al sensor
		this.notificar();
		this.temperatura = temperatura;
	}

	public ArrayList<Integer> getMedicionesTemperatura() {
		return medicionesTemperatura;
	}

	public void setMedicionesTemperatura(ArrayList<Integer> medicionesTemperatura) {
		this.medicionesTemperatura = medicionesTemperatura;
	}

	public Integer getIntesidadLuminica() {
		return intesidadLuminica;
	}

	public void setIntesidadLuminica(Integer intesidadLuminica) {
		this.intesidadLuminica = intesidadLuminica;
		this.notificar();
	}

	public boolean isMovimiento() {
		return movimiento;
	}

	public void setMovimiento(boolean movimiento) {
		this.movimiento = movimiento;
		this.notificar();
	}
	
	public void notificar() {
		// TODO Auto-generated method stub
		for(Sensor s:sensores) {
			s.actualizar(this);
		}
		
	}
	
		
}
