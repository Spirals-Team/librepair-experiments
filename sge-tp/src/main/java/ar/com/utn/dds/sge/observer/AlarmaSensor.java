package ar.com.utn.dds.sge.observer;

import java.util.ArrayList;
import java.util.List;

import ar.com.utn.dds.sge.commands.Actuador;

public class AlarmaSensor implements SujetoObservable{

	private List<Actuador> actuadores = new ArrayList<>();
	
	public void agregarActuador(Actuador actuador) {
		actuadores.add(actuador);
	}
	
	public void quitarActuador(Actuador actuador) {
		actuadores.remove(actuador);
	}
	
	public void notificarActuadores() {
		for(Actuador invocador : actuadores) {
			invocador.run();
		}
	}

}
