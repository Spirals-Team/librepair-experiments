package com.economizate.servicios;

import com.economizate.entidades.Cuenta;
import com.economizate.entidades.Usuario;
import com.economizate.servicios.impl.MovimientosMonetariosImpl;

public interface Usuarios {
	
	public Usuario buscarUsuarioPorEmail(String email);
	
	public Cuenta obtenerSaldoUsuario(Usuario usuario);
	
	public Cuenta obtenerSaldoUsuario(String email);
	
	public void cambiarSaldoUsuario(double importe);
	
	public MovimientosMonetariosImpl getMovimientos();

}
