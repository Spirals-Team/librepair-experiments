package com.economizate.servicios.impl;

import java.text.SimpleDateFormat;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IConversor;

public class ConversorMovimientoSinCuota implements IConversor<MovimientoMonetario> {

private String delimitador;
	
	SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

	public ConversorMovimientoSinCuota(String delimitador) {
		this.delimitador = delimitador;
	}
	
	@Override
	public String convertToString(MovimientoMonetario movimiento) {
		return formater.format(movimiento.getFecha()) + delimitador + movimiento.getDescripcion() + delimitador + movimiento.getObservacion() +
				delimitador + movimiento.getImporte();
	}
}
