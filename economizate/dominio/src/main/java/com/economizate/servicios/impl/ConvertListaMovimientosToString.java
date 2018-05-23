package com.economizate.servicios.impl;

import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IConversor;

public class ConvertListaMovimientosToString {

	public static String getRegistros(List<MovimientoMonetario> movimientos, IConversor<MovimientoMonetario> conversor) 
    { 
		StringBuilder registros = new StringBuilder();
		for(MovimientoMonetario mov : movimientos) {
			registros.append(conversor.convertToString(mov)).append("\r\n");
		}
        return registros.toString();
    }
}
