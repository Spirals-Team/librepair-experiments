package com.economizate.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.economizate.controladores.FrontController;
import com.economizate.servicios.impl.Idioma;

public class IdiomaListener implements ActionListener{

	private Idioma idioma;
	
	public IdiomaListener(Idioma idioma) {
		this.idioma = idioma;
	}
	
	public void actionPerformed(ActionEvent evento) {
		FrontController.getInstance(this.idioma).manejarRequest("HOME");;
	}

}
