package ar.com.utn.dds.sge.rules.impl;

import ar.com.utn.dds.sge.commands.Comando;
import ar.com.utn.dds.sge.rules.Regla;

public class ReglaTemperaturaMaxima implements Regla {

	private Float tempraturaMaxima;
	private Comando accionUmbralMaximo;
	
	
	/**
	 * Constructor de la regla para umbral maximo. Tener en cuenta que dependiendo que tipo de artefacto
	 * sea, el comando puede ir variando. Lo que importa es simplemente tener la referencia de una temperatura
	 * maxima y un comando que se asocie cuando se supere la misma, sin importar que tipo de accion sea o el dispositivo
	 * al cual corresponda la regla.
	 * 
	 * @param tempraturaMaxima
	 * @param accionUmbralMaximo
	 */
	public ReglaTemperaturaMaxima(Float tempraturaMaxima, Comando accionUmbralMaximo) {
		super();
		this.tempraturaMaxima = tempraturaMaxima;
		this.accionUmbralMaximo = accionUmbralMaximo;
	}

	@Override
	public boolean seCumpleRegla(Float medicion) {
		return medicion <= tempraturaMaxima;
	}

	/**
	 * @return the tempraturaMaxima
	 */
	public Float getTempraturaMaxima() {
		return tempraturaMaxima;
	}

	/**
	 * @param tempraturaMaxima the tempraturaMaxima to set
	 */
	public void setTempraturaMaxima(Float tempraturaMaxima) {
		this.tempraturaMaxima = tempraturaMaxima;
	}

	/**
	 * @return the accionUmbralMaximo
	 */
	public Comando getAccionUmbralMaximo() {
		return accionUmbralMaximo;
	}

	/**
	 * @param accionUmbralMaximo the accionUmbralMaximo to set
	 */
	public void setAccionUmbralMaximo(Comando accionUmbralMaximo) {
		this.accionUmbralMaximo = accionUmbralMaximo;
	}
	
	

}
