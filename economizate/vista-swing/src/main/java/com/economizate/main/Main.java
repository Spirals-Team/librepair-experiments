package com.economizate.main;

import com.economizate.controladores.FrontController;
import com.economizate.servicios.impl.Idioma;


public class Main {

	public static void main(String[] args) {
		
		/*Home home = new Home();
		home.iniciarVista();*/
		FrontController.getInstance(Idioma.ESPANIOL).manejarRequest("HOME");
	}
}
