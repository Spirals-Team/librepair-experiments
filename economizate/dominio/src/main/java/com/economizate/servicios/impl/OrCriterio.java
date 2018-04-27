package com.economizate.servicios.impl;

import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Movimientos;
import com.economizate.servicios.Criterio;

public class OrCriterio implements Criterio {

	private Criterio criterio1;
	private Criterio criterio2;
	
	public OrCriterio(Criterio criterio1, Criterio criterio2) {
		this.criterio1 = criterio1;
		this.criterio2 = criterio2;
	}
	
	@Override
	public Movimientos filtrarMovimientos(
			List<MovimientoMonetario> movimientos) {
		
		Movimientos movimientosFinal = criterio1.filtrarMovimientos(movimientos);		
		for(MovimientoMonetario mov : criterio2.filtrarMovimientos(movimientos).getTodos()) {			
			if(!movimientosFinal.getTodos().contains(mov)) {
				movimientosFinal.agregarMovimiento(mov);
			}
		}		
		return movimientosFinal;
	}

	
}
