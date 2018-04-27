package com.economizate.servicios.impl;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IParserRegistro;

public class ParserRegistroSinCuota implements IParserRegistro {

	int cantidadCampos;
	
	public ParserRegistroSinCuota() {
		this.cantidadCampos = 3;
	}
	
	@Override
	public MovimientoMonetario getObjeto(String[] stringarray) {
		MovimientoMonetario movimiento = new MovimientoMonetario(stringarray[0], stringarray[1], 
        		Double.parseDouble(stringarray[2])); 
        return movimiento; 
	}

	@Override
	public int getCantidadCampos() {		
		return cantidadCampos;
	}
}
