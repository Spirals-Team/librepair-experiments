package com.economizate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.economizate.conector.ConectorCuenta;
import com.economizate.entidades.Alerta;
import com.economizate.entidades.Cuenta;
import com.economizate.servicios.FactoryAlertas;
import com.economizate.servicios.impl.UsuariosImpl;
import com.economizate.transferencias.ITransferencia;
import com.economizate.transferencias.ProxyTransferencia;
import com.mashape.unirest.http.exceptions.UnirestException;

@RunWith(MockitoJUnitRunner.class)
public class RestTest {
	
	public double saldoAnterior;
	public double saldoActual;
	private Cuenta cuenta;
	public Alerta alerta;
	
	@Mock
	ITransferencia transferenciaMock;
	
	@Before
	public void generarCuenta() {
		cuenta = new ConectorCuenta().nuevaCuenta();
		
		saldoAnterior = cuenta.getTotal();
		
		cuenta.addObserver(new ObservadorVistaTest(saldoAnterior));
		
		//Mockito mocks
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void generarTransferenciaConMonto100ConExito() throws UnirestException{
		//comportamiento de mock
		when(transferenciaMock.transferir(Double.valueOf(100), "pepa@gmail.com")).thenReturn(Boolean.TRUE);
		
		//transferir returna true si el servicio responde ok
		boolean result = transferenciaMock.transferir(Double.valueOf(100), "pepa@gmail.com");
		
		assertTrue("Transferencia realizada", result);
	}
	
	@Test
	public void generarTransferenciaConMailNoexisteYRecibirErrorArgumentoIlegal() throws UnirestException{
		//No necesito comportamiento de mock
		
		try {
			//valido mail
			String mailDestinatario = new UsuariosImpl().buscarUsuarioPorEmail("pepa@Gmail.com").getEmail();
			
			//ejecutar retorna http status 
			int result = transferenciaMock.ejecutar(Double.valueOf(150), mailDestinatario);
			assertTrue("Mail existente: ", mailDestinatario != null);
			assertTrue("Transferencia realizada con status: ", result == 201);
		}catch(IllegalArgumentException e) {
			assertTrue("Mail INexistente: ", e.getMessage().equals("Email no coincidente: " + "pepa@Gmail.com"));
		}
	}
	
	@Test
	public void generarTransferenciaConMonto100YConExitoConAlarmaRoja(){
		
		ITransferencia transferencia = new ProxyTransferencia(cuenta);
		boolean sinConexion = true;
		
		try {
			//transferir returna true si el servicio responde ok
			boolean result = transferencia.transferir(Double.valueOf(100), "pepa@gmail.com");
			
			sinConexion = false;
			assertTrue("Transferencia realizada: ", result);
			assertTrue("La alerta generada es de tipo roja: ", alerta.getMensaje().equals("Ha superado el 95%"));
		}catch(UnirestException e) {
			assertTrue("Test sin conexión a destinatario: ", sinConexion);
		}
	}
	
	@Test
	public void generarTransferenciaConMonto100YRecibirStatus201ConAlarmaRoja(){
		ITransferencia transferencia = new ProxyTransferencia(cuenta);
		boolean sinConexion = true;
		
		try {
			//ejecutar retorna http status 
			int result = transferencia.ejecutar(Double.valueOf(100), "pepa@gmail.com");
			
			sinConexion = false;
			assertTrue("Transferencia realizada con status: ", result == 201);
			assertTrue("La alerta generada es de tipo roja: ", alerta.getMensaje().equals("Ha superado el 95%"));
			
		}catch(UnirestException e) {
			assertTrue("Test sin conexión a destinatario: ", sinConexion);
		}
	}
	
	@Test
	public void generarTransferenciaConMonto50YRecibirStatus201ConAlarmaVerde(){
		ITransferencia transferencia = new ProxyTransferencia(cuenta);
		boolean sinConexion = true;
		
		try {
			//ejecutar retorna http status 
			int result = transferencia.ejecutar(Double.valueOf(50), "pepaGonzalez@gmail.com");
			
			sinConexion = false;
			assertTrue("Transferencia realizada con status: ", result == 201);
			assertTrue("La alerta generada es de tipo verde: ", alerta.getMensaje().equals("Transacción OK"));
			
		}catch(UnirestException e) {
			assertTrue("Test sin conexión a destinatario: ", sinConexion);
		}
	}
	
	@Test
	public void generarTransferenciaConMonto150YRecibirStatus201ConAlarmaNegra(){
		ITransferencia transferencia = new ProxyTransferencia(cuenta);
		boolean sinConexion = true;
		
		try {
			//ejecutar retorna http status 
			int result = transferencia.ejecutar(Double.valueOf(150), "pepa@gmail.com");
			
			sinConexion = false;
			assertTrue("Transferencia realizada con status: ", result == 201);
			assertTrue("La alerta generada es de tipo negra: ", alerta.getMensaje().equals("Supera el saldo total"));
			
		}catch(UnirestException e) {
			assertTrue("Test sin conexión a destinatario: ", sinConexion);
		}
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
