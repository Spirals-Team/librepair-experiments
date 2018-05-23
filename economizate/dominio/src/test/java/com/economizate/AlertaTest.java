package com.economizate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import com.economizate.conector.ConectorCuenta;
import com.economizate.entidades.Alerta;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.FactoryAlertas;
import com.economizate.servicios.impl.Propiedad;

public class AlertaTest {

	public double saldoAnterior;
	public double saldoActual;
	private Cuenta cuenta;
	public Alerta alerta;
	
	@Before
	public void generarCuenta() {
		cuenta = new ConectorCuenta().nuevaCuenta();
		
		saldoAnterior = cuenta.getTotal();
		
		cuenta.addObserver(new ObservadorVistaTest(saldoAnterior));
	}
	
	@Test
	public void generarAlertaTipoRojaAlRegistrarEgresoSuperiorAl95Porciento() {
		MovimientoMonetario egreso = 
				new MovimientoMonetario("Gasto shopping", "Renovar ropa", Double.parseDouble("-96"), new Date());
		
		cuenta.getMovimientos().agregarMovimiento(egreso);
		cuenta.modificarTotal(cuenta.getMovimientos().getTotal());
		
		cuenta.setTotal(cuenta.getTotal());
		
		assertTrue("La alerta generada es de tipo roja: ", alerta.getMensaje().equals("Ha superado el 95%"));
	}
	
	
	
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
	
	
	public class ObservadorVistaTest implements Observer{
		double saldoAnterior;
		FactoryAlertas creadorAlertas = new FactoryAlertas();
		
		public ObservadorVistaTest(double saldoAnterior) {
			this.saldoAnterior = saldoAnterior;
		};

		@Override
		public void update(Observable arg0, Object arg1) {
			alerta = creadorAlertas.crearAlerta(saldoAnterior, (Double) arg1);
		}
	}
}
