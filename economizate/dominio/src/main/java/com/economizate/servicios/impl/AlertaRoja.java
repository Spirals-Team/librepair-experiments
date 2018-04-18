package com.economizate.servicios.impl;

import com.economizate.conector.ConectorAlerta;
import com.economizate.entidades.Alerta;
import com.economizate.servicios.IAlertas;

public class AlertaRoja implements IAlertas {

	ConectorAlerta conector = new ConectorAlerta();
	
	@Override
	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		// TODO Auto-generated method stub
		return conector.crearAlertaRoja(saldoAnterior, saldoActual);
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
