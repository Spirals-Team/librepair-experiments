package com.economizate;

import static org.junit.Assert.*;

import java.util.Observer;

import org.junit.Test;

import com.economizate.datos.CuentaObserver;
import com.economizate.entidades.Alerta;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.FactoryAlertas;
import com.economizate.servicios.IAlertas;
import com.economizate.servicios.impl.Propiedad;

public class DispararAlertaTestNico {

	FactoryAlertas factoryAlertas = new FactoryAlertas();
	IAlertas alertasService;
	Alerta alerta;

	@Test
	public void generarAlertaTipo95PorcientoTest() {

		Cuenta subject = new Cuenta();
		subject.setTotalSinObserver(100.0);
		Observer observer = new CuentaObserver(subject);
		subject.agregarObserver(observer);
		subject.agregarMovimiento(new MovimientoMonetario("Luz", "Abril", -98.0, 0));

		assertEquals(((CuentaObserver) observer).getAlerta().getMensaje(),
				Propiedad.getInstance().getPropiedad("mensajeAlerta95Porciento"));		
	}
	
	@Test
	public void generarAlertaTipo80PorcientoTest() {

		Cuenta subject = new Cuenta();
		subject.setTotalSinObserver(100.0);
		Observer observer = new CuentaObserver(subject);
		subject.agregarObserver(observer);
		subject.agregarMovimiento(new MovimientoMonetario("Luz", "Abril", -80.0, 0));

		assertEquals(((CuentaObserver) observer).getAlerta().getMensaje(),
				Propiedad.getInstance().getPropiedad("mensajeAlerta80Porciento"));		
	}

	@Test
	public void generarAlertaTipoTransaccionOkPorcientoTest() {

		Cuenta subject = new Cuenta();
		subject.setTotalSinObserver(100.0);
		Observer observer = new CuentaObserver(subject);
		subject.agregarObserver(observer);
		subject.agregarMovimiento(new MovimientoMonetario("Luz", "Abril", -79.0, 0));

		assertEquals(((CuentaObserver) observer).getAlerta().getMensaje(),
				Propiedad.getInstance().getPropiedad("mensajeAlertaVerde"));		
	}
}
