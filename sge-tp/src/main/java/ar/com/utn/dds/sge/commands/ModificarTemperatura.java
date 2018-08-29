package ar.com.utn.dds.sge.commands;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class ModificarTemperatura implements Comando {

	private DispositivoInteligente dispositivo;
	private Float temperatura;
	
	public ModificarTemperatura(DispositivoInteligente dispositivo, Float temperatura) {
		this.dispositivo = dispositivo;
		this.temperatura = temperatura;
	}
	
	public void ejecutar() {
		dispositivo.setTemperatura(temperatura);
	}
	
}
