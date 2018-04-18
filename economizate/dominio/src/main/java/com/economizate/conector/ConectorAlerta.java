package com.economizate.conector;

import com.economizate.entidades.Alerta;
import com.economizate.servicios.impl.Propiedad;

public class ConectorAlerta {
	
	private Alerta alertaAmarilla;
	private Alerta alertaRoja;
	private Alerta alertaVerde;
	
	public Alerta crearAlertaAmarilla(double saldoAnterior, double saldoActual) {
		if(alertaAmarilla == null) {
			alertaAmarilla = new Alerta(saldoAnterior, saldoActual);
			alertaAmarilla.setMensaje(Propiedad.getInstance().getPropiedad("mensajeAlerta80Porciento"));
		}
		return alertaAmarilla;
	}
	
	public Alerta crearAlertaRoja(double saldoAnterior, double saldoActual) {
		if(alertaRoja == null) {
			alertaRoja = new Alerta(saldoAnterior, saldoActual);
			alertaRoja.setMensaje(Propiedad.getInstance().getPropiedad("mensajeAlerta95Porciento"));
		}
		return alertaRoja;
	}
	
	public Alerta crearAlertaVerde(double saldoAnterior, double saldoActual) {
		if(alertaVerde == null) {
			alertaVerde = new Alerta(saldoAnterior, saldoActual);
			alertaVerde.setMensaje("Transaccion OK.");
		}
		return alertaVerde;
	}

}
