package ar.com.utn.dds.sge.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sun.el.parser.ParseException;

import ar.com.utn.dds.sge.commands.ActivarModoCalor;
import ar.com.utn.dds.sge.commands.ActivarModoFrio;
import ar.com.utn.dds.sge.commands.Actuador;
import ar.com.utn.dds.sge.commands.ModificarIntensidadLuminica;
import ar.com.utn.dds.sge.commands.ModificarTemperatura;
import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;
import ar.com.utn.dds.sge.exceptions.FieldValidationException;

public class ActuadoresTest {

	CasosDePruebaBuilder prueba = new CasosDePruebaBuilder();

	/**
	 * Se crean todas las entidades que se deberían crear en memoria luego de
	 * parsear el archivo json.
	 * 
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */

	@Before
	public void crearDatosDePrueba() throws ParseException, java.text.ParseException {
		prueba.crearDatosDePrueba();
	}

	@Test
	public void pasarAModoCalorTest() {
		System.out.println("La temperatura anterior del dispositivo es:");
		System.out.println(prueba.dispositivoInteligente.getTemperatura());
		prueba.actuador = new Actuador(new ActivarModoCalor(prueba.dispositivoInteligente));
		prueba.actuador.run();
		System.out.println("El modo calor settea la temperatura en 30 grados.");
		System.out.println("La temperatura actual del dispositivo es:");
		System.out.println(prueba.dispositivoInteligente.getTemperatura());
		assertEquals(prueba.dispositivoInteligente.getTemperatura(), 30.00f, 0.1f);
	}

	@Test
	public void pasarAModoFrioTest() {
		System.out.println("La temperatura anterior del dispositivo es:");
		System.out.println(prueba.dispositivoInteligente.getTemperatura());
		prueba.actuador = new Actuador(new ActivarModoFrio(prueba.dispositivoInteligente));
		prueba.actuador.run();
		System.out.println("El modo frio settea la temperatura en 18 grados.");
		System.out.println("La temperatura actual del dispositivo es:");
		System.out.println(prueba.dispositivoInteligente.getTemperatura());
		assertEquals(prueba.dispositivoInteligente.getTemperatura(), 18.00f, 0.1f);
	}

	@Test
	public void cambiarTemperaturaTest() {
		System.out.println("La temperatura anterior del dispositivo es:");
		System.out.println(prueba.dispositivoInteligente.getTemperatura());
		prueba.actuador = new Actuador(new ModificarTemperatura(prueba.dispositivoInteligente, 24.00f));
		prueba.actuador.run();
		System.out.println("La temperatura actual del dispositivo es:");
		System.out.println(prueba.dispositivoInteligente.getTemperatura());
		assertEquals(prueba.dispositivoInteligente.getTemperatura(), 24.00f, 0.1f);
	}

	@Test
	public void cambiarIntesidadLuminicaTest() {
		System.out.println("La intensidad luminica anterior del dispositivo es:");
		System.out.println(prueba.dispositivoInteligente.getIntesidadLuminica());
		prueba.actuador = new Actuador(new ModificarIntensidadLuminica(prueba.dispositivoInteligente, 25));
		prueba.actuador.run();
		System.out.println("La intensidad luminica actual del dispositivo es:");
		System.out.println(prueba.dispositivoInteligente.getIntesidadLuminica());
		assertEquals(prueba.dispositivoInteligente.getIntesidadLuminica(), new Integer(25));
	}

	@Test(expected = FieldValidationException.class)
	public void volverAPrenderDispositivoTest() {
		prueba.dispositivoInteligente.prender();
	}

}
