package com.economizate.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.economizate.entidades.Usuario;
import com.economizate.servicios.Cuenta;
import com.economizate.servicios.Usuarios;
import com.economizate.vistas.Egreso;
import com.economizate.vistas.Home;
import com.economizate.vistas.Reportes;

public class ControladorReportes implements ActionListener {
	
	Cuenta saldosService;
	Usuarios usuarioService;
	
	private Usuario  model;
	private Reportes vista;
	private Home home;
	
	public ControladorReportes() {
	}
	
	public ControladorReportes(Usuario usuario, Reportes vista, Home home, Usuarios usuarios) {
		this.vista = vista;
		this.model = usuario;
		this.home = vista.getVentanaHome();
		
		this.usuarioService = usuarios;
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Apéndice de método generado automáticamente
		
	}

}
