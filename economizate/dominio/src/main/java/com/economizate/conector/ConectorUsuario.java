package com.economizate.conector;

import java.util.Arrays;
import java.util.List;

import com.economizate.entidades.Usuario;

public class ConectorUsuario {
	
	List<Usuario> usuarios;
	
	public ConectorUsuario() {
		this.usuarios = llenarLista();
	}
	
	private List<Usuario> llenarLista() {
		Usuario pepe = new Usuario("pepe", "Gonzalez", "p3p3G0nz4l3z@gmail.com");
		pepe.setSaldo(new ConectorCuenta().nuevaCuenta());
		
		Usuario pepa = new Usuario("pepa", "Sanxhez", "pepa@gmail.com");
		pepa.setSaldo(new ConectorCuenta().nuevaCuenta());
		return Arrays.asList(pepe, pepa);
	}
	
	public Usuario dameUsuario(String email) {
		return usuarios.stream().filter(u -> email.equals(u.getEmail())).
				findFirst().orElseThrow(() -> new IllegalArgumentException("Email no coincidente: " + email));
	};

}
