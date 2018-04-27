package com.economizate.conector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observer;

import com.economizate.entidades.Cuenta;
import com.economizate.entidades.Usuario;

public class ConectorUsuario {
	
	//private ConectorMysql conector;
	private Usuario usuario;
	
	private Observer observer;

	public ConectorUsuario() {
		//conector = new ConectorMysql();
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		Usuario usuario = new Usuario();
		/*PreparedStatement ps;
		try {
			//ps = conector.conectar().prepareStatement("select * from usuarios where email = ?");
		
			ps.setString(1, email);
			try {
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					usuario.setNombre(rs.getString("nombre"));
					usuario.setApellido(rs.getString("apellido"));
					usuario.setEmail(rs.getString("email"));
				}
			}finally {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		return usuario;
	}
	
	public Usuario usuarioNuevo() {
		if(usuario == null) {
			usuario = new Usuario();
			usuario.setNombre("pepe");
			usuario.setApellido("gonzalez");
			usuario.setEmail("pepeGonzalez@gmail.com");
			Cuenta saldo;
			/*if(observer != null) {
				saldo = new ConectorCuenta().nuevoSaldo(observer);
				saldo.addObserver(observer);
			}else {
				saldo = new ConectorCuenta().nuevoSaldo();
			}
			usuario.setSaldo(saldo);*/
		}	
		return usuario;
	}
	
	public Cuenta obtenerSaldoUsuario(String email) {
		return usuarioNuevo().getSaldo();
	}
	
	public void cambiarSaldoUsuario(double importe) {
		usuarioNuevo().getSaldo().setTotal(importe);
	}
	
	public void addObserver(Observer o) {
		this.observer = o;
	}

}
