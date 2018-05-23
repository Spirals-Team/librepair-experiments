package com.economizate;

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


public class DispararAlertaTest {
	
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
	
	//Corresponde caso 1 de criterios de aceptación US 1 
	@Test
	public void generarAlertaTipoRojaAlRegistrarEgresoSuperiorAl95Porciento() {
		MovimientoMonetario egreso = 
				new MovimientoMonetario("Gasto shopping", "Renovar ropa", Double.parseDouble("-96"), new Date());
		
		actualizarSaldo(egreso);
		
		assertTrue("La alerta generada es de tipo roja: ", alerta.getMensaje()
				.equals(Propiedad.getInstance().getPropiedad("mensajeAlerta95Porciento")));
	}
	
	//Corresponde caso 2 de criterios de aceptación US 1 
	@Test
	public void generarAlertaTipoAmarillaAlRegistrarEgresoSuperiorAl80Porciento() {
		MovimientoMonetario egreso = 
				new MovimientoMonetario("Gasto shopping", "Renovar ropa", Double.parseDouble("-80"), new Date());
		
		actualizarSaldo(egreso);
		
		assertTrue("La alerta generada es de tipo amarilla: ", alerta.getMensaje()
				.equals(Propiedad.getInstance().getPropiedad("mensajeAlerta80Porciento")));
	}
		
	//Corresponde caso 3 de criterios de aceptación US 1 
	@Test
	public void generarAlertaTipoNegraAlRegistrarEgresoSuperiorAl100Porciento() {
		MovimientoMonetario egreso = 
				new MovimientoMonetario("Gasto shopping", "Renovar ropa", Double.parseDouble("-101"), new Date());
		
		actualizarSaldo(egreso);
		assertTrue("La alerta generada es de tipo negra: ", alerta.getMensaje()
				.equals(Propiedad.getInstance().getPropiedad("mensajeAlerta100Porciento")));
	}
		
	//Corresponde caso 4 de criterios de aceptación US 1 
	@Test
	public void generarAlertaTipoVerdeAlRegistrarEgresoInferiorAl80Porciento() {
		MovimientoMonetario egreso = 
				new MovimientoMonetario("Gasto shopping", "Renovar ropa", Double.parseDouble("-79"), new Date());
		
		actualizarSaldo(egreso);
		
		assertTrue("La alerta generada es de tipo verde: ", alerta.getMensaje()
				.equals(Propiedad.getInstance().getPropiedad("mensajeAlertaVerde")));
	}
	
	private void actualizarSaldo(MovimientoMonetario egreso) {
		cuenta.getMovimientos().agregarMovimiento(egreso);
		cuenta.modificarTotal(cuenta.getMovimientos().getTotal());
		//función con notify al observer
		cuenta.setTotal(cuenta.getTotal());
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
