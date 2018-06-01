package com.economizate.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.economizate.servicios.Usuarios;
import com.economizate.vistas.Home;
import com.economizate.vistas.Ingreso;

public class IngresoListener implements ActionListener{
	
	private Home home;
	private JFrame ventana;
	private String email;
	private double saldo;
	private Usuarios usuarios;
	
	public IngresoListener(Home home, String email, double saldo){
		this.home = home;
		this.ventana = home.getVentanaHome();
		this.email = email;
		this.saldo = saldo;
		
	}

	public void actionPerformed(ActionEvent e) {
		ventana.setVisible(false);
		new Ingreso(home, email, saldo).iniciarVista();
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

}
