package com.economizate.servicios.impl;

import com.economizate.conector.ConectorAlerta;
import com.economizate.entidades.Alerta;
import com.economizate.servicios.IAlertas;

public class AlertaRoja implements IAlertas {

	ConectorAlerta conector = new ConectorAlerta();
	
	@Override
	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		return conector.crearAlertaRoja(saldoAnterior, saldoActual, 
				Propiedad.getInstance().getPropiedad("mensajeAlerta95Porciento"));
	}

	@Override
	public String getMensaje() {
		return Propiedad.getInstance().getPropiedad("mensajeAlerta95Porciento");
	}
	

	
}
