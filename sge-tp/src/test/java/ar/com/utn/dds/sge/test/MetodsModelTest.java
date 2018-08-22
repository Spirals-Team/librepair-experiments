package ar.com.utn.dds.sge.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;

public class MetodsModelTest {

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
	public void testCantidadDispositivosEncendidos() throws IOException {
		assertEquals(prueba.cliente1.cantDispositivosEncendidos(), 2);
	}

	public void testCantidadDispositivosApagados() throws IOException {
		assertEquals(prueba.cliente1.cantDispositivosApagados(), 1);
	}

	@Test
	public void testClienteConDispositivosEncendidos() throws IOException {
		assertTrue(prueba.cliente1.hayDispositivosEncendidos());
	}

	@Test
	public void testTotalDispositivos() throws IOException {
		assertEquals(prueba.cliente1.cantDispositivos(), 3);
	}

	@Test
	public void testCalculoDeConsumo() throws IOException {
		assertEquals(prueba.cliente1.calcularConsumo(), 113.0, 0.0);
	}

	@Test
	public void testCalculoDeImporte() throws IOException {
		assertEquals(prueba.cliente1.obtenerImporte(), 91.53, 0.5);
	}

	@Test
	public void testClienteSinDispositivosApagados() throws IOException {
		assertEquals(prueba.cliente2.cantDispositivosApagados(), 0);
	}

	@Test
	public void testClienteSinDispositivosEncendidos() throws IOException {
		assertEquals(prueba.cliente3.cantDispositivosEncendidos(), 0);
	}

	@Test
	public void testCalculoDeConsumoSinDispEncendidos() throws IOException {
		assertEquals(prueba.cliente3.calcularConsumo(), 0.0, 0.0);
	}

	@Test
	public void testCalculoDeImporteSinDispEncendidos() throws IOException {
		assertEquals(prueba.cliente3.obtenerImporte(), 60.71, 0.1);
	}

	// Verifico caso con lista vacia
	@Test
	public void testCantidadDispositivosEncendidosEnListaVacia() throws IOException {
		assertEquals(prueba.cliente4.cantDispositivosEncendidos(), 0);
	}

	@Test
	public void testClienteConDispositivosEncendidosEnListaVacia() throws IOException {
		assertFalse(prueba.cliente4.hayDispositivosEncendidos());
	}

	@Test
	public void testTotalDispositivosEnListaVacia() throws IOException {
		assertEquals(prueba.cliente4.cantDispositivos(), 0);
	}

	@Test
	public void testCalculoDeConsumoSinDispositivos() throws IOException {
		assertEquals(prueba.cliente4.calcularConsumo(), 0.0, 0.0);
	}

	@Test
	public void testCalculoDeImporteSinDispositivos() throws IOException {
		assertEquals(prueba.cliente4.obtenerImporte(), 71.74, 0.1);
	}

	@Test
	public void testAntiguedadAdmin() throws IOException, ParseException {
		// Verificamos que el usuario admin tenga 92 meses de antiguedad
		assertEquals(prueba.admin.obtenerAntiguedad(), 93);
	}

}
