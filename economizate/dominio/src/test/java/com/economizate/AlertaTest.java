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
	
	@Test
	public void alertaControlSaldoAnteriorYSaldoActualOK() {
				
		saldoAnterior = 100.0;
		saldoActual = 20.01;
		Alerta alert = new FactoryAlertas().crearAlerta(saldoAnterior, saldoActual);		
		assertTrue("tiene saldo anterior ok", saldoAnterior == alert.getSaldoAnterior());
		assertTrue("tiene saldo actual ok", saldoActual == alert.getSaldoActual());
	}
	
	@Test
	public void alertaControlSetsSaldoAnteriorYSaldoActualOK() {
				
		saldoAnterior = 100.0;
		saldoActual = 20.01;
		Alerta alert = new FactoryAlertas().crearAlerta(saldoAnterior, saldoActual);		
		
		alert.setSaldoAnterior(99);
		assertTrue("tiene saldo anterior ok", alert.getSaldoAnterior() == 99);
		
		alert.setSaldoActual(40.01);
		assertTrue("tiene saldo actual ok", alert.getSaldoActual() == 40.01);
	}
	
}
