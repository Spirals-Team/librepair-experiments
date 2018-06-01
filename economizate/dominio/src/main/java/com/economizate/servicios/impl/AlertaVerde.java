package com.economizate.servicios.impl;

import com.economizate.conector.ConectorAlerta;
import com.economizate.entidades.Alerta;
import com.economizate.servicios.IAlertas;

public class AlertaVerde implements IAlertas {

	ConectorAlerta conector = new ConectorAlerta();
	
	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		return conector.crearAlertaVerde(saldoAnterior, saldoActual, 
				Propiedad.getInstance().getPropiedad("mensajeAlertaVerde"));
				//"Transacción OK");
	}
}
