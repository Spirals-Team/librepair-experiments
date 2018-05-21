package com.economizate.servicios.impl;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ManejadorEtiqueta {
	
	private static Idioma idioma;
	
	private static ManejadorEtiqueta instancia;
	
	public static ManejadorEtiqueta getInstance() {
		if(instancia == null) {			
			instancia = new ManejadorEtiqueta();
			instancia.setIdioma(Idioma.ESPANIOL);
		}
		return instancia;
	}
	
	public static ManejadorEtiqueta getInstance(Idioma idioma) {
		if(instancia == null) {
			instancia = new ManejadorEtiqueta();	
			
		}
		instancia.setIdioma(idioma);
		return instancia;
	}
	
	public String getMensaje(String key) throws MissingResourceException {		
		ResourceBundle labels = ResourceBundle.getBundle("etiquetas", FrontController.getInstance(idioma).getLocale());
		return labels.getString(key);
	}
	
	public void setIdioma(Idioma idioma) {
		this.idioma = idioma; 
	}
}
