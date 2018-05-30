package com.economizate.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Movimientos;
import com.economizate.servicios.Criterio;

public class IngresoCriterio implements Criterio {

	@Override
	public Movimientos filtrarMovimientos(
			List<MovimientoMonetario> movimientos) {
		Movimientos ingresos = new Movimientos();
		
		for(MovimientoMonetario mov : movimientos) {
			if(mov.getImporte() > 0) {
				ingresos.agregarMovimiento(mov);
			}
		}
		return ingresos;		
	}
}
