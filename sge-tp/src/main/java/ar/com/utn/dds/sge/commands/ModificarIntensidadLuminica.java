package ar.com.utn.dds.sge.commands;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class ModificarIntensidadLuminica implements Comando {
	
	private DispositivoInteligente dispositivo;
	private Integer intensidadLuminica;
	
	public ModificarIntensidadLuminica(DispositivoInteligente dispositivo, Integer intensidadLuminica) {
		this.dispositivo = dispositivo;
		this.intensidadLuminica = intensidadLuminica;
	}
	
	public void ejecutar() {
		dispositivo.setIntesidadLuminica(intensidadLuminica);
	}

}
