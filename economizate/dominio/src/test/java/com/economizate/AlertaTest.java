package com.economizate;

import static org.junit.Assert.*;

import org.junit.Test;

import com.economizate.entidades.Alerta;
import com.economizate.servicios.FactoryAlertas;
import com.economizate.servicios.impl.Propiedad;

public class AlertaTest {

	double saldoAnterior;
	double saldoActual;
	
	@Test
	public void alerta95PorcientoBordeSuperior() {
				
		saldoAnterior = 100.0;
		saldoActual = 4.99;
		Alerta alert = new FactoryAlertas().crearAlerta(saldoAnterior, saldoActual);
		assertTrue(alert.getMensaje().equals(Propiedad.getInstance().getPropiedad("mensajeAlerta95Porciento")));;
	}
	
	@Test
	public void alerta95PorcientoLimite() {
				
		saldoAnterior = 100.0;
		saldoActual = 5.00;
		Alerta alert = new FactoryAlertas().crearAlerta(saldoAnterior, saldoActual);
		
		assertTrue(alert.getMensaje().equals(Propiedad.getInstance().getPropiedad("mensajeAlerta95Porciento")));;
	}
	
	@Test
	public void alerta95PorcientoBordeInferior() {
				
		saldoAnterior = 100.0;
		saldoActual = 5.01;
		Alerta alert = new FactoryAlertas().crearAlerta(saldoAnterior, saldoActual);
		
		assertFalse(alert.getMensaje().equals(Propiedad.getInstance().getPropiedad("mensajeAlerta95Porciento")));;
	}

	@Test
	public void alerta80PorcientoBordeSuperior() {
				
		saldoAnterior = 100.0;
		saldoActual = 19.99;
		Alerta alert = new FactoryAlertas().crearAlerta(saldoAnterior, saldoActual);		
		assertTrue(alert.getMensaje().equals(Propiedad.getInstance().getPropiedad("mensajeAlerta80Porciento")));
	}
	
	@Test
	public void alerta80PorcientoLimite() {
				
		saldoAnterior = 100.0;
		saldoActual = 20;
		Alerta alert = new FactoryAlertas().crearAlerta(saldoAnterior, saldoActual);		
		assertTrue(alert.getMensaje().equals(Propiedad.getInstance().getPropiedad("mensajeAlerta80Porciento")));
	}
	
	@Test
	public void alerta80PorcientoBordeInferior() {
				
		saldoAnterior = 100.0;
		saldoActual = 20.01;
		Alerta alert = new FactoryAlertas().crearAlerta(saldoAnterior, saldoActual);		
		assertFalse(alert.getMensaje().equals(Propiedad.getInstance().getPropiedad("mensajeAlerta80Porciento")));
	}
}
