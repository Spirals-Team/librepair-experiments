package com.economizate.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.Criterio;

public class IngresoCriterio implements Criterio {

	@Override
	public List<MovimientoMonetario> filtrarMovimientos(
			List<MovimientoMonetario> movimientos) {
		List<MovimientoMonetario> ingresos = new ArrayList<MovimientoMonetario>();
		
		for(MovimientoMonetario mov : movimientos) {
			if(mov.getImporte() > 0) {
				ingresos.add(mov);
			}
		}
		return ingresos;		
	}
}
