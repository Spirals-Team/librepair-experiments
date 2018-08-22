package ar.com.utn.dds.sge.commands;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class ActivarModoAhorro implements Comando {

	private DispositivoInteligente dispositivo;
	
	public ActivarModoAhorro(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	public void ejecutar() {
		dispositivo.activarModoAhorro();

	}

}
