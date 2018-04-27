package com.economizate.servicios.impl;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.ValidadorRegistroStrategy;

public class ConcreteValidadorRegistroStrategy implements ValidadorRegistroStrategy {
	
	
	@Override
	public boolean validate(MovimientoMonetario mov) {

		if(mov.getDescripcion().isEmpty()) {
			return false;
		} else if(mov.getObservacion().isEmpty()) {
			return false;
		} else if(mov.getImporte() == null || mov.getImporte().equals(0)) {
			return false;
		}
				
		return true;
	}

}
