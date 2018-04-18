package com.economizate.servicios.impl;

import com.economizate.conector.ConectorAlerta;
import com.economizate.entidades.Alerta;
import com.economizate.servicios.IAlertas;

public class AlertaAmarilla implements IAlertas {
	
	ConectorAlerta conector = new ConectorAlerta();
		
	public AlertaAmarilla(double saldoAnterior, double saldoActual) {
		
	}
	
	public AlertaAmarilla() {
	}

	@Override
	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		/*Alerta alert = conector.crearAlerta(saldoAnterior, saldoActual);*/
		return conector.crearAlertaAmarilla(saldoAnterior, saldoActual);
	}

	@Override
	public boolean muestraAlerta() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMensaje() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
