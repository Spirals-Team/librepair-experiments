package com.economizate.servicios;

import com.economizate.entidades.Alerta;
import com.economizate.servicios.impl.AlertaAmarilla;
import com.economizate.servicios.impl.AlertaNegra;
import com.economizate.servicios.impl.AlertaRoja;
import com.economizate.servicios.impl.AlertaVerde;
import com.economizate.servicios.IAlertas;

public class FactoryAlertas {

	private IAlertas alertaAmarilla = new AlertaAmarilla();
	private IAlertas alertaRoja = new AlertaRoja();
	private IAlertas alertaVerde = new AlertaVerde();
	private IAlertas alertaNegra = new AlertaNegra();
	
	public Alerta crearAlerta(double saldoAnterior, double saldoActual) {
		double porcentajeRestante = getPorcentajeGasto(saldoAnterior, saldoActual);
		
		if(  porcentajeRestante > 100) {
			return alertaNegra.crearAlerta(saldoAnterior, saldoActual);}
		else if(  porcentajeRestante >= 95)
			return alertaRoja.crearAlerta(saldoAnterior, saldoActual);
		else if(porcentajeRestante >= 80)
			return alertaAmarilla.crearAlerta(saldoAnterior, saldoActual);
		else
			return alertaVerde.crearAlerta(saldoAnterior, saldoActual);
	}
	
	private double getPorcentajeGasto(double saldoAnterior, double saldoActual) {
		return - ((double)saldoActual * 100 / saldoAnterior) + 100;
	}
}
