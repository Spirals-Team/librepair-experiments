package ar.com.utn.dds.sge.state;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public interface Estado {
	
	public Float consumo(Float consumo);
	public void cambiarAEncendido (DispositivoInteligente dispositivo);
	public void cambiarAApagado(DispositivoInteligente dispositivo);
	public void cambiarAModoAhorro(DispositivoInteligente dispositivo);
	public boolean getPrendido();
}
