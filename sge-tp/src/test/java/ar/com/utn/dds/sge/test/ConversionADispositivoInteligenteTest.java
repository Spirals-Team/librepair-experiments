package ar.com.utn.dds.sge.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;
import ar.com.utn.dds.sge.models.DispositivoAdaptado;

public class ConversionADispositivoInteligenteTest {
	CasosDePruebaBuilder prueba = new CasosDePruebaBuilder();

	/**
	 * Se crean todas las entidades que se deberían crear en memoria luego de
	 * parsear el archivo json.
	 * 
	 * @throws ParseException
	 */

	@Before
	public void crearDatosDePrueba() throws ParseException {
		prueba.crearDatosDePrueba();
	}

	@Test
	public void ConversionDeDispositivoEstandarADispositivoInteligenteTest() {
		System.out.println("Dispositivo estandar:");
		System.out.println(prueba.dispositivoEstandar.getTipo());
		DispositivoAdaptado dispositivoAdaptado = new DispositivoAdaptado(prueba.dispositivoEstandar);
		System.out.println("Luego de adaptar el dispositivo le setteo la temperatura en 30 grados.");
		dispositivoAdaptado.setTemperatura(30.00f);
		System.out.println("Temperatura actual de dispositivo adaptado:");
		System.out.println(dispositivoAdaptado.getTemperatura());
		assertEquals(dispositivoAdaptado.getTemperatura(), 30.00f, 0.1f);
	}

	@Test
	public void ConversionDeDispositivoEstandarADispositivoInteligenteDeUnClienteTest() {
		System.out.println("Dispositivo estandar:");
		System.out.println(prueba.cliente1.getDispositivos().get(0).getTipo());
		prueba.cliente1.adaptarDispositivoEstandar(prueba.dispositivoEstandar);
		System.out.println("Dispositivo Adaptado:");
		System.out.println(prueba.cliente1.getDispositivos().get(3).getTipo());
		DispositivoAdaptado dispositivoAdaptado = new DispositivoAdaptado(prueba.dispositivoEstandar);
		assertEquals(prueba.cliente1.getDispositivos().get(3), dispositivoAdaptado);
	}

	@Test
	public void PuntajeDelClienteLuegoDeAdaptarUnDispositivoEstandarTest() {
		System.out.println("Puntaje del cliente antes de adaptar un dispositivo estandar:");
		System.out.println(prueba.cliente1.getPuntos());
		prueba.cliente1.adaptarDispositivoEstandar(prueba.dispositivoEstandar);
		System.out.println("Puntaje actual del cliente:");
		System.out.println(prueba.cliente1.getPuntos());
		assertEquals(prueba.cliente1.getPuntos(), 10);
	}
}
