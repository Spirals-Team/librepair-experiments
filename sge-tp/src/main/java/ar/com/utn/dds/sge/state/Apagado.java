package ar.com.utn.dds.sge.state;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class Apagado implements Estado {

	@Override
	public Float consumo(Float consumo) {
		return 0.0f;
	}

	@Override
	public void cambiarAEncendido(DispositivoInteligente dispositivo) {	
		dispositivo.setEstado(new Encendido());
	}

	@Override
	public void cambiarAApagado(DispositivoInteligente dispositivo) {
	}

	@Override
	public void cambiarAModoAhorro(DispositivoInteligente dispositivo) {
		dispositivo.setEstado(new ModoAhorro());
	}
	
	public boolean getPrendido() {
		return false;
	}
}
