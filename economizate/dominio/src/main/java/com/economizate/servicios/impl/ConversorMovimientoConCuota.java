package com.economizate.servicios.impl;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IConversorMovimiento;

public class ConversorMovimientoConCuota implements IConversorMovimiento {

	private String delimitador;
	
	public ConversorMovimientoConCuota(String delimitador) {
		this.delimitador = delimitador;
	}
	
	@Override
	public String convertToString(MovimientoMonetario movimiento) {
		return movimiento.getFecha() + delimitador + movimiento.getDescripcion() + delimitador + movimiento.getObservacion() +
				delimitador + movimiento.getImporte() + delimitador + movimiento.getCantidadCuotas();
	}

}
