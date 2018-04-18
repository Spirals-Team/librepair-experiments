package com.economizate.servicios;

import com.economizate.entidades.Alerta;
import com.economizate.servicios.impl.AlertaAmarilla;
import com.economizate.servicios.impl.AlertaRoja;
import com.economizate.servicios.impl.AlertaVerde;

public class FactoryAlertas {

	private IAlertas alertaAmarilla = new AlertaAmarilla();
	private IAlertas alertaRoja = new AlertaRoja();
	private IAlertas alertaVerde = new AlertaVerde();
	
	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		double porcentajeRestante = getPorcentajeGasto(saldoAnterior, saldoActual);
		if( porcentajeRestante <= (double) 5)
			/*return new AlertaRoja(saldoAnterior, saldoActual);*/
			return alertaRoja.crearAlerta(saldoAnterior, saldoActual);
		else if(porcentajeRestante <= (double)20)
			//return new AlertaAmarilla(saldoAnterior, saldoActual);
			return alertaAmarilla.crearAlerta(saldoAnterior, saldoActual);
		else
			return alertaVerde.crearAlerta(saldoAnterior, saldoActual);
	}
	
	private double getPorcentajeGasto(double saldoAnterior, double saldoActual) {
		return (double)saldoActual * 100 / saldoAnterior;
	}
}
