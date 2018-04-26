package com.economizate.entidades;

import java.util.Date;


public class Alerta {
	
	private Date fecha;
	private double saldoAnterior;
	private double saldoActual;
	private String mensaje;
	
	public Alerta(double saldoAnterior, double saldoActual) {
		this.saldoAnterior = saldoAnterior;
		this.saldoActual = saldoActual;
	}

	public double getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(double saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public double getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(double saldoActual) {
		this.saldoActual = saldoActual;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
