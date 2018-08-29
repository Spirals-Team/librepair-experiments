package ar.com.utn.dds.sge.commands;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class ActivarModoFrio implements Comando {

	private DispositivoInteligente dispositivo;
	
	public ActivarModoFrio(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	public void ejecutar() {
		dispositivo.setTemperatura(18.00f);
		
	}

}
