package ar.com.utn.dds.sge.state;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class Encendido implements Estado {
	
	@Override
	public Float consumo(Float consumo) {
		return consumo;
	}

	@Override
	public void cambiarAEncendido(DispositivoInteligente dispositivo) {		
	}

	@Override
	public void cambiarAApagado(DispositivoInteligente dispositivo) {
		dispositivo.setEstado(new Apagado());
	}

	@Override
	public void cambiarAModoAhorro(DispositivoInteligente dispositivo) {
		dispositivo.setEstado(new ModoAhorro());
	}
	
	public boolean getPrendido() {
		return true;
	}

}
