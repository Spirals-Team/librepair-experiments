package ar.com.utn.dds.sge.models;

import ar.com.utn.dds.sge.observer.ISensor;

public class Sensor implements ISensor {
	private Integer temperatura;
	private Integer humedad;
	private Integer intensidadLuminica;
	private boolean movimiento;
	
	
	@Override
	public void actualizar(DispositivoInteligente objeto) {
		// TODO Auto-generated method stub
	}


	public Integer getTemperatura() {
		return temperatura;
	}


	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}


	public Integer getHumedad() {
		return humedad;
	}


	public void setHumedad(Integer humedad) {
		this.humedad = humedad;
	}


	public Integer getIntensidadLuminica() {
		return intensidadLuminica;
	}


	public void setIntensidadLuminica(Integer intensidadLuminica) {
		this.intensidadLuminica = intensidadLuminica;
	}


	public boolean isMovimiento() {
		return movimiento;
	}


	public void setMovimiento(boolean movimiento) {
		this.movimiento = movimiento;
	}
	
	

}
