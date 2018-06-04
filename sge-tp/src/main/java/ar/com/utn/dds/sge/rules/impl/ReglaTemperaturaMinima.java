package ar.com.utn.dds.sge.rules.impl;

import ar.com.utn.dds.sge.commands.Comando;
import ar.com.utn.dds.sge.rules.Regla;

public class ReglaTemperaturaMinima implements Regla {

	private Float temperaturaMinima;
	private Comando accionUmbralMinimo;

	/**
	 * Constructor de la regla para umbral minimo. Tener en cuenta que dependiendo que tipo de artefacto
	 * sea, el comando puede ir variando. Lo que importa es simplemente tener la referencia de una temperatura
	 * minima y un comando que se asocie cuando se la temperatura sea menor al umbral minimo, sin importar 
	 * que tipo de accion sea o el dispositivo al cual corresponda la regla.
	 * 
	 * @param temperaturaMinima
	 * @param accionUmbralMinimo
	 */
	public ReglaTemperaturaMinima(Float temperaturaMinima, Comando accionUmbralMinimo) {
		super();
		this.temperaturaMinima = temperaturaMinima;
		this.accionUmbralMinimo = accionUmbralMinimo;
	}
	
	/**
	 * Metodo que indica si se cumple regla o no. En caso de no cumplirse obtener
	 * comando asociado a la regla para ejecutar.
	 */
	@Override
	public boolean seCumpleRegla(Float medicion) {
		return medicion >= temperaturaMinima;
	}

	/**
	 * @return the temperaturaMinima
	 */
	public Float getTemperaturaMinima() {
		return temperaturaMinima;
	}

	/**
	 * @param temperaturaMinima the temperaturaMinima to set
	 */
	public void setTemperaturaMinima(Float temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}

	/**
	 * @return the accionUmbralMinimo
	 */
	public Comando getAccionUmbralMinimo() {
		return accionUmbralMinimo;
	}

	/**
	 * @param accionUmbralMinimo the accionUmbralMinimo to set
	 */
	public void setAccionUmbralMinimo(Comando accionUmbralMinimo) {
		this.accionUmbralMinimo = accionUmbralMinimo;
	}
	

}
