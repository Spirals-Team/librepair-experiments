package com.economizate.datos;

import java.util.Observable;

import com.economizate.entidades.Alerta;
import com.economizate.entidades.Cuenta;
import com.economizate.servicios.FactoryAlertas;

public class CuentaObserver implements java.util.Observer {

	private Cuenta cuenta;
	FactoryAlertas factoryAlertas = new FactoryAlertas();
	private Alerta alerta;;
	Double totalActual;
	Double totalAnterior;
	private Double porcentajeSaldo;
	
	public CuentaObserver(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		totalAnterior = ((Cuenta)arg0).getTotal();
		totalActual = ((Cuenta)arg0).getTotal() + (Double)arg1;
		porcentajeSaldo = totalActual * 100 / totalAnterior;
	}

	public Alerta getAlerta() {
		alerta = factoryAlertas.crearAlerta(totalAnterior, totalActual);
		return alerta;
	}
}
