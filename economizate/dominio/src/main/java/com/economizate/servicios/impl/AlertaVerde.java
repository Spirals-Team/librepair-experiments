package com.economizate.servicios.impl;

import com.economizate.conector.ConectorAlerta;
import com.economizate.entidades.Alerta;
import com.economizate.servicios.IAlertas;

public class AlertaVerde implements IAlertas {

	ConectorAlerta conector = new ConectorAlerta();
	
	@Override
	public boolean muestraAlerta() {
		return false;
	}

	@Override
	public String getMensaje() {
		return "";
	}

	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		// TODO Auto-generated method stub
		return conector.crearAlertaVerde(saldoAnterior, saldoActual);
	}
}
