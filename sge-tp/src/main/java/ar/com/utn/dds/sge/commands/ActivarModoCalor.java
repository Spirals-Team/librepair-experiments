package ar.com.utn.dds.sge.commands;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class ActivarModoCalor implements Comando {

	private DispositivoInteligente dispositivo;
	
	public ActivarModoCalor(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	public void ejecutar() {
		dispositivo.setTemperatura(30.00f);
	}
	
}
