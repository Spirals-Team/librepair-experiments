package com.economizate.servicios.impl;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ManejadorEtiqueta {
	
	
	private static ManejadorEtiqueta instancia;
	
	public static ManejadorEtiqueta getInstance() {
		if(instancia == null) {
			instancia = new ManejadorEtiqueta();	
			
		}
		return instancia;
	}
	
	public String getMensaje(String key) throws MissingResourceException {		
		ResourceBundle labels = ResourceBundle.getBundle("etiquetas", ManejadorIdioma.getInstance().getLocale());
		return labels.getString(key);
	}

}
