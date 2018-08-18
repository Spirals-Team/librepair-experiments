package ar.com.utn.dds.sge.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;
import ar.com.utn.dds.sge.observer.sensors.impl.SensorTemperatura;

public class SensoresTest {

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
	public void testSuperaTemperaturaMaxima() {
		// Creacion Sensor de prueba
		SensorTemperatura sensorTemp = new SensorTemperatura();
		sensorTemp.setMedicion(35f);

		assertTrue(prueba.reglaTempMaxima.getCondicion().comparar((Comparable) sensorTemp.obtenerValor()));
	}

	@Test
	public void testNoSuperaTemperaturaMaxima() {
		// Creacion Sensor de prueba
		SensorTemperatura sensorTemp = new SensorTemperatura();
		sensorTemp.setMedicion(29f);

		assertFalse(prueba.reglaTempMaxima.getCondicion().comparar((Comparable) sensorTemp.obtenerValor()));
	}

	@Test
	public void testDebajoTemperaturaMinima() {
		// Creacion Sensor de prueba
		SensorTemperatura sensorTemp = new SensorTemperatura();
		sensorTemp.setMedicion(20f);

		assertTrue(prueba.reglaTempMinima.getCondicion().comparar((Comparable) sensorTemp.obtenerValor()));
	}

	@Test
	public void testEncimaTemperaturaMinima() {
		// Creacion Sensor de prueba
		SensorTemperatura sensorTemp = new SensorTemperatura();
		sensorTemp.setMedicion(35f);

		assertFalse(prueba.reglaTempMinima.getCondicion().comparar((Comparable) sensorTemp.obtenerValor()));
	}

}
