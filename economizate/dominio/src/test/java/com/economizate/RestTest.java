package com.economizate;

import static org.junit.Assert.assertTrue;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import com.economizate.AlertaTest.ObservadorVistaTest;
import com.economizate.conector.ConectorCuenta;
import com.economizate.entidades.Alerta;
import com.economizate.entidades.Cuenta;
import com.economizate.servicios.FactoryAlertas;
import com.economizate.transferencias.ITransferencia;
import com.economizate.transferencias.ProxyTransferencia;

public class RestTest {
	
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
	public void generarTransferenciaConMonto100YConExito(){
		ITransferencia transferencia = new ProxyTransferencia(cuenta);
		
		//transferir returna true si el servicio responde ok
		boolean result = transferencia.transferir(Double.valueOf(100), "pepaGonzalez@gmail.com");
		
		assertTrue("Transferencia realizada: ", result);
		assertTrue("La alerta generada es de tipo roja: ", alerta.getMensaje().equals("Ha superado el 95%"));
	}
	
	@Test
	public void generarTransferenciaConMonto100YRecibirStatus201(){
		ITransferencia transferencia = new ProxyTransferencia(cuenta);
		
		//ejecutar retorna http status 
		int result = transferencia.ejecutar(Double.valueOf(100), "pepaGonzalez@gmail.com");
		
		assertTrue("Transferencia realizada con status: ", result == 201);
		assertTrue("La alerta generada es de tipo roja: ", alerta.getMensaje().equals("Ha superado el 95%"));
	}
	
	
	//Observador para alertas
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
