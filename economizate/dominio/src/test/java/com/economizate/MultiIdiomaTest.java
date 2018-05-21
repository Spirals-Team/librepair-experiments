package com.economizate;

import static org.junit.Assert.*;

import java.util.MissingResourceException;

import org.junit.Test;

import com.economizate.servicios.impl.Idioma;
import com.economizate.servicios.impl.ManejadorEtiqueta;

public class MultiIdiomaTest {

	ManejadorEtiqueta labels = new ManejadorEtiqueta();
	
	@Test
	public void InglesTest() {
	
		assertTrue(ManejadorEtiqueta.getInstance(Idioma.INGLES).getMensaje("etiquetaBienvenido").equals("Welcome"));
	}
	
	@Test
	public void EspaniolTest() {
		
		assertTrue(ManejadorEtiqueta.getInstance(Idioma.ESPANIOL).getMensaje("etiquetaBienvenido").equals("Bienvenido"));
	}

	@Test (expected=MissingResourceException.class)
	public void InglesEtiquetaInexistenteTest() {
		ManejadorEtiqueta.getInstance(Idioma.INGLES).getMensaje("etiquetaInexistente");
	}
	
	@Test (expected=MissingResourceException.class)
	public void EspaniolEtiquetaInexistenteTest() {
		ManejadorEtiqueta.getInstance(Idioma.ESPANIOL).getMensaje("etiquetaInexistente");		
	}
}
