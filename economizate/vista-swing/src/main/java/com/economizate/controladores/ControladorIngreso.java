package com.economizate.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import com.economizate.entidades.Cuenta;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Usuario;
import com.economizate.listeners.EgresoListener;
import com.economizate.listeners.IngresoListener;
import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.CuentaImpl;
import com.economizate.vistas.Home;
import com.economizate.vistas.Ingreso;

public class ControladorIngreso implements ActionListener{
	
	private static Logger logger = Logger.getLogger(ControladorIngreso.class.getName());
	
	Usuarios usuarioService;
	Cuenta saldoService;
	
	private Usuario  model;
	private Ingreso vista;
	private Home home;
	
	public ControladorIngreso() {
	}
	
	public ControladorIngreso(Usuario usuario, Ingreso vista, Home home, Usuarios usuarios, Cuenta saldos) {
		this.vista = vista;
		this.model = usuario;
		this.home = home;
		
		this.usuarioService = usuarios;
		this.saldoService = saldos;
		//No es necesario porque se agregan en los constructores de cada servicio
		//this.model.addObserver(home);
		//this.model.addObserver(vista);
	}

	public void actionPerformed(ActionEvent e) {
		logger.info("Ingreso action controlador");
		
		double nuevoTotal = Double.parseDouble(vista.getImporteTextFieldValue())
				+ model.getSaldo().getTotal();
		
		//lo cambio en la "base"
		usuarioService.obtenerSaldoUsuario(model).getMovimientos().agregarMovimiento(new MovimientoMonetario(vista.getDescricionTextFieldValue(), "", nuevoTotal));
		usuarioService.obtenerSaldoUsuario(model).setTotal(nuevoTotal);
		//saldoService.cambiarSaldoTotal(nuevoTotal);
		usuarioService.cambiarSaldoUsuario(nuevoTotal);
		
		//actualizo las vistas
		vista.getSaldoUsuario().setText("Saldo: " + String.valueOf(nuevoTotal));
		home.setSaldoUsuario("Saldo: " + String.valueOf(nuevoTotal));
		
		home.ingresoListener.setSaldo(nuevoTotal);
		//actualizo intancias de usuario en vistas
		//actualizarInstancias();
		 
		//volver Home
		vista.getVentana().setVisible(false);
		home.getVentana().setVisible(true);
	}
	
	private void actualizarInstancias() {
		home.botonIngreso.addActionListener(
				new IngresoListener(home, home.getEmail(), usuarioService.obtenerSaldoUsuario(model).getTotal()));
		
		home.botonEgreso.addActionListener(
				new EgresoListener(home, home.getEmail(), usuarioService.obtenerSaldoUsuario(model).getTotal()));
	}
}
