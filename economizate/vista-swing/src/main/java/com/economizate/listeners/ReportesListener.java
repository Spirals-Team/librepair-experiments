package com.economizate.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.economizate.vistas.Home;
import com.economizate.vistas.Reportes;

public class ReportesListener implements ActionListener{

	private Home home;
	private JFrame ventana;
	private String email;
	
	public ReportesListener(Home home, String email) {
		this.home = home;
		this.ventana = home.getVentana();
		this.email = email;
	}

	public void actionPerformed(ActionEvent e) {
		ventana.setVisible(false);
		new Reportes(email, home.getServicioUsuario(), home).iniciarVista();
	}

}
