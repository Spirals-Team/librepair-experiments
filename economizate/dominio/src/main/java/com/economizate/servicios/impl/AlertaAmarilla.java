package com.economizate.servicios.impl;

import com.economizate.conector.ConectorAlerta;
import com.economizate.entidades.Alerta;
import com.economizate.servicios.IAlertas;

public class AlertaAmarilla implements IAlertas {
	
	ConectorAlerta conector = new ConectorAlerta();
	
	public AlertaAmarilla() {
	}

	@Override
	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		return conector.crearAlertaAmarilla(saldoAnterior, saldoActual, 
				Propiedad.getInstance().getPropiedad("mensajeAlerta80Porciento"));
	}

}
