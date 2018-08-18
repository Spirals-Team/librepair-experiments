package ar.com.utn.dds.sge.observer;

import ar.com.utn.dds.sge.commands.Actuador;

public interface SujetoObservable {
	
	public void agregarActuador(Actuador actuador);
	public void quitarActuador(Actuador actuador);
	public void notificarActuadores();

}
