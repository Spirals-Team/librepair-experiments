package com.economizate.servicios.impl;

import java.util.Observer;

import com.economizate.conector.ConectorCuenta;
import com.economizate.conector.ConectorUsuario;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.Usuario;
import com.economizate.servicios.MovimientosMonetarios;
import com.economizate.servicios.Usuarios;

public class UsuariosImpl implements Usuarios{
	
	private MovimientosMonetarios movimientos= new MovimientosMonetariosImpl();
	private ConectorUsuario conector = new ConectorUsuario();
	private ConectorCuenta conectorSaldo = new ConectorCuenta();
	
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

	public MovimientosMonetariosImpl getMovimientos() {
		return (MovimientosMonetariosImpl) movimientos;
	}

	@Override
	public double validarImporteIgresado(String importe) throws NumberFormatException{
		return
			Double.parseDouble(importe);
	}

	@Override
	public void cambiarSaldoUsuario(double importe) {
		// TODO Auto-generated method stub
		
	}

}
