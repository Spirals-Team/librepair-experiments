package ar.com.utn.dds.sge.commands;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class Prender implements Comando {

	private DispositivoInteligente dispositivo;
	
	public Prender(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	public void ejecutar() {
		dispositivo.prender();
	}

}
