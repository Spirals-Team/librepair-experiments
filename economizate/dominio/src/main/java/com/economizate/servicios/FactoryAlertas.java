package com.economizate.servicios;

import com.economizate.entidades.Alerta;
import com.economizate.servicios.impl.AlertaAmarilla;
import com.economizate.servicios.impl.AlertaRoja;
import com.economizate.servicios.impl.AlertaVerde;
import com.economizate.servicios.impl.Propiedad;

public class FactoryAlertas {

	private IAlertas alertaAmarilla = new AlertaAmarilla();
	private IAlertas alertaRoja = new AlertaRoja();
	private IAlertas alertaVerde = new AlertaVerde();
	
	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		double porcentajeRestante = getPorcentajeGasto(saldoAnterior, saldoActual);
		if( porcentajeRestante >=  Double.parseDouble(Propiedad.getInstance().getPropiedad("porcentajeAlertaRoja")))
			return alertaRoja.crearAlerta(saldoAnterior, saldoActual);
		else if(porcentajeRestante >= Double.parseDouble(Propiedad.getInstance().getPropiedad("porcentajeAlertaAmarilla")))
			return alertaAmarilla.crearAlerta(saldoAnterior, saldoActual);
		else
			return alertaVerde.crearAlerta(saldoAnterior, saldoActual);
	}
	
	private double getPorcentajeGasto(double saldoAnterior, double saldoActual) {
		return - ((double)saldoActual * 100 / saldoAnterior) + 100;
	}
}
