package com.economizate.controladores;

import java.util.Locale;

import com.economizate.servicios.impl.Idioma;
import com.economizate.servicios.impl.ManejadorIdioma;

public class FrontController {

	private static FrontController frontController;
	
	private Dispatcher dispatch;
	
	private ManejadorIdioma idioma;
	
	public static FrontController getInstance(Idioma idioma) {
		if(frontController == null) {
			frontController = new FrontController();				
		}
		frontController.setLocale(idioma);
		return frontController;
	}
	
	private FrontController() {
		dispatch = new Dispatcher();
	}
	
	public void setLocale(Idioma idioma) {
		
		this.idioma = ManejadorIdioma.getInstance(idioma);		
	}
	
	public Locale getLocale() {
		return this.idioma.getLocale();
	}
	
	public void manejarRequest(String request) {
		
		dispatch.dispatch(request);
	}
}
