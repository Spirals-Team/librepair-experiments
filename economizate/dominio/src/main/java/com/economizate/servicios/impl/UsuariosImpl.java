package com.economizate.servicios.impl;

import com.economizate.conector.ConectorCuenta;
import com.economizate.conector.ConectorUsuario;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.Usuario;
import com.economizate.servicios.Usuarios;

public class UsuariosImpl implements Usuarios{
	
	private ConectorUsuario conector = new ConectorUsuario();
	private ConectorCuenta conectorSaldo = new ConectorCuenta();
	
	public UsuariosImpl() {
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

	@Override
	public Usuario buscarUsuarioPorEmail(String email) {
		return conector.dameUsuario(email);
	}

	@Override
	public Cuenta obtenerSaldoUsuario(Usuario usuario) {
		return conector.dameUsuario(usuario.getEmail()).getSaldo();
	}

	@Override
	public Cuenta obtenerSaldoUsuario(String email) {
		return conector.dameUsuario(email).getSaldo();
	}

}
