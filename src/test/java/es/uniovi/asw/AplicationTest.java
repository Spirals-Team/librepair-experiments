package es.uniovi.asw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.Agente;

public class AplicationTest {

	@Before
	public void before() {
		BBDD.eliminarAgentes();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void addAgenteTest() {
		List<Agente> agentes = new ArrayList<Agente>();

		Agente c = new Agente("Pepe", "locprueba", "email@prueba", "identifPrueba", 1);
		agentes.add(c);
		BBDD.insertarAgente(agentes);

		Agente cBD = BBDD.obtenerAgente("identifPrueba");
		assertNotNull(cBD);
		assertEquals("Pepe", cBD.getNombre());
		assertEquals("locprueba", cBD.getLocalizacion());
		assertEquals("email@prueba", cBD.getEmail());
		assertEquals("identifPrueba", cBD.getIdentificador());
		

		c.setEmail("otroemail@.com");

		BBDD.updateAgente(c);
		cBD = BBDD.obtenerAgente("identifPrueba");
		assertNotNull(cBD);
		assertEquals("Pepe", cBD.getNombre());
		assertEquals("otroemail@.com", cBD.getEmail());
		assertEquals("identifPrueba", cBD.getIdentificador());

		BBDD.eliminarAgente("identifPrueba");
		cBD = BBDD.obtenerAgente("identifPrueba");
		assertNull(cBD);
	}

	@Test
	public void testCargarCSS() {
		// leemos y cargamos el fichero
		ArrayList<Agente> ciudadanos = new ArrayList<Agente>();
		ciudadanos = Leer.leerAgentesdelExcel(ciudadanos, "./src/main/java/es/uniovi/asw/agentes.xlsx");

		// probamos con el primer Agente
		Agente c = ciudadanos.get(0);
		assertEquals("Pedro", c.getNombre());
		assertEquals("pedro@hotmail.com", c.getEmail());
		assertEquals("78569544S", c.getIdentificador());



	}

	

	@SuppressWarnings("deprecation")
	@Test
	public void testCrearCorreo() {
		Agente c = new Agente("Pepe", "locprueba", "email@prueba", "identifPrueba", 1);
		
		assertNotNull(c.getPassword());

		String rutaFichero = "./correos/" + c.getNombre() + ".txt";
		File fichero = new File(rutaFichero);
		assertFalse(fichero.exists());

		CrearCorreo.mandarCorreo(c);
		
		fichero = new File(rutaFichero);
		assertTrue(fichero.exists());

		
		fichero.delete();
	}



}
