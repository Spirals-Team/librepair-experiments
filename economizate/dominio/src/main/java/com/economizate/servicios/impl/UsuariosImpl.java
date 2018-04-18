package com.economizate.servicios.impl;

import java.util.Observer;

import com.economizate.conector.ConectorSaldo;
import com.economizate.conector.ConectorUsuario;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.Usuario;
import com.economizate.servicios.MovimientosMonetarios;
import com.economizate.servicios.Usuarios;

public class UsuariosImpl implements Usuarios{
	
	private MovimientosMonetarios movimientos= new MovimientosMonetariosImpl();
	private ConectorUsuario conector = new ConectorUsuario();
	private ConectorSaldo conectorSaldo = new ConectorSaldo();
	
	public UsuariosImpl() {
	}
	
	public UsuariosImpl(Observer o) {
		conector.addObserver(o);
	}

	public Usuario buscarUsuarioPorEmail(String email) {
		return conector.usuarioNuevo();
	}

	public Cuenta obtenerSaldoUsuario(Usuario usuario) {
		return conector.obtenerSaldoUsuario(usuario.getEmail());
	}
	
	public Cuenta obtenerSaldoUsuario(String email) {
		return conector.obtenerSaldoUsuario(email);
	}

	public void cambiarSaldoUsuario(double importe) {
		//conector.cambiarSaldoUsuario(importe);
		conectorSaldo.cambiarSaldo(importe);
	}

	public MovimientosMonetariosImpl getMovimientos() {
		return (MovimientosMonetariosImpl) movimientos;
	}

}
