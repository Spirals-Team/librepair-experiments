package com.economizate;


import javax.faces.bean.ManagedBean;

import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.UsuariosImpl;

@ManagedBean(name = "usuarios", eager = true)
public class UsuariosBean {
	
	private Usuarios usuarios;
	
	public UsuariosBean() {
		usuarios = new UsuariosImpl();
	}
	
	public String getMensaje() {
		return "Bienvenido: ".concat(usuarios.buscarUsuarioPorEmail("pepeGonzalez@gmail.com").getNombre());
	}

}
