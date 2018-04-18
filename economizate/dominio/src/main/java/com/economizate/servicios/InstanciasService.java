package com.economizate.servicios;

import java.util.Observer;

import com.economizate.servicios.impl.SaldosImpl;
import com.economizate.servicios.impl.UsuariosImpl;

public class InstanciasService {
	
	private static InstanciasService instanciasService;
	
	private Usuarios usuarios;
	private Usuarios usuariosConObservador;
	
	private Saldos saldos;
	private Saldos saldosConObservador;
	
	public static InstanciasService getInstanciasService() {
		if(instanciasService == null)
			instanciasService = new InstanciasService();
		return instanciasService;
	}
	
	public Usuarios getUsuariosService() {
		if (usuarios == null) {
			usuarios = new UsuariosImpl();
		}
		return usuarios;
	}
	
	public Usuarios getUsuariosObservadorService(Observer observer) {
		if (usuariosConObservador == null) {
			usuariosConObservador = new UsuariosImpl(observer);
		}
		return usuariosConObservador;
	}
	
	public Saldos getSaldosService() {
		if (saldos == null) {
			saldos = new SaldosImpl();
		}
		return saldos;
	}

}
