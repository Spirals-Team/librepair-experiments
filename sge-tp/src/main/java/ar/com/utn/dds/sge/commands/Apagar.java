package ar.com.utn.dds.sge.commands;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class Apagar implements Comando {

	private DispositivoInteligente dispositivo;
	
	public Apagar(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	@Override
	public void ejecutar() {
		dispositivo.apagar();
	}

}
