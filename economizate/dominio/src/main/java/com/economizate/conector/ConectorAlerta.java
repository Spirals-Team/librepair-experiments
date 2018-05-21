package com.economizate.conector;

import com.economizate.entidades.Alerta;

public class ConectorAlerta {
	
	private Alerta alertaAmarilla;
	private Alerta alertaRoja;
	private Alerta alertaVerde;
	private Alerta alertaNegra;
	
	public Alerta crearAlertaAmarilla(double saldoAnterior, double saldoActual, String mensaje) {
		if(alertaAmarilla == null) {
			alertaAmarilla = new Alerta(saldoAnterior, saldoActual);
			alertaAmarilla.setMensaje(mensaje);
		}
		return alertaAmarilla;
	}
	
	public Alerta crearAlertaRoja(double saldoAnterior, double saldoActual, String mensaje) {
		if(alertaRoja == null) {
			alertaRoja = new Alerta(saldoAnterior, saldoActual);
			alertaRoja.setMensaje(mensaje);
		}
		return alertaRoja;
	}
	
	public Alerta crearAlertaNegra(double saldoAnterior, double saldoActual, String mensaje) {
		if(alertaNegra == null) {
			alertaNegra = new Alerta(saldoAnterior, saldoActual);
			alertaNegra.setMensaje(mensaje);
		}
		return alertaNegra;
	}
	
	public Alerta crearAlertaVerde(double saldoAnterior, double saldoActual, String mensaje) {
		if(alertaVerde == null) {
			alertaVerde = new Alerta(saldoAnterior, saldoActual);
			alertaVerde.setMensaje(mensaje);
		}
		return alertaVerde;
	}

}
