package com.economizate.servicios.impl;

import java.util.Locale;

public class ManejadorIdioma {

	private Locale locale;
	
	private static ManejadorIdioma frontController;
	
	
	public static ManejadorIdioma getInstance(Idioma idioma) {
		if(frontController == null) {
			frontController = new ManejadorIdioma();	
			
		}
		frontController.setLocale(idioma);
		return frontController;
	}
	
	public static ManejadorIdioma getInstance() {
		if(frontController == null) {
			frontController = new ManejadorIdioma();	
			
		}
		return frontController;
	}
	
	private ManejadorIdioma() {
		 
		this.locale = new Locale("es", "ES");
	}

	public void setLocale(Idioma idioma) {
		if(idioma == Idioma.ESPANIOL) {
			this.locale = new Locale("es", "ES");
		} else if (idioma == Idioma.INGLES) {
			this.locale = new Locale("en", "EN");
		}
	}
	
	public Locale getLocale() {
		return this.locale;
	}
	
	public void manejarRequest(String request) {
		
	}
}
