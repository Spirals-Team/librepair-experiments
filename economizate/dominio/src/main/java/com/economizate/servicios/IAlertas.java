package com.economizate.servicios;

import com.economizate.entidades.Alerta;

public interface IAlertas {

	public Alerta crearAlerta(double saldoAnterior, double saldoActual); 
		
	public String getMensaje();
}
