package ar.com.utn.dds.sge.state;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class ModoAhorro implements Estado {

	@Override
	public Float consumo(Float consumo) {
		return consumo * 0.75f;
	}

	@Override
	public void cambiarAEncendido(DispositivoInteligente dispositivo) {	
		dispositivo.setEstado(new Encendido());
	}

	@Override
	public void cambiarAApagado(DispositivoInteligente dispositivo) {
		dispositivo.setEstado(new Apagado());
	}

	@Override
	public void cambiarAModoAhorro(DispositivoInteligente dispositivo) {
	}
	
	public boolean getPrendido() {
		return true;
	}
}
