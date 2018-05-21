package com.economizate.servicios.impl;

import java.util.Locale;

public class FrontController {

	private Locale locale;
	
	private static FrontController frontController;
	
	
	public static FrontController getInstance() {
		if(frontController == null) {
			frontController = new FrontController();	
		}
		return frontController;
	}
	
	public static FrontController getInstance(Idioma idioma) {
		if(frontController == null) {
			frontController = new FrontController();	
			
		}
		frontController.setLocale(idioma);
		return frontController;
	}
	
	private FrontController() {
		 
		this.locale = new Locale("es", "ES");
	}
	
	private FrontController(Locale locale) {
		this.locale = locale;
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
