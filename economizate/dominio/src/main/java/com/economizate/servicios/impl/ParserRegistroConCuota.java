package com.economizate.servicios.impl;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IParserRegistro;

public class ParserRegistroConCuota implements IParserRegistro {

	int cantidadCampos;
	
	public ParserRegistroConCuota() {
		this.cantidadCampos = 4;
	}
	
	@Override
	public MovimientoMonetario getObjeto(String[] stringarray) {
		MovimientoMonetario movimiento = new MovimientoMonetario(stringarray[0], stringarray[1], 
        		Double.parseDouble(stringarray[2]), (int)Double.parseDouble(stringarray[3])); 
        return movimiento; 
	}

	@Override
	public int getCantidadCampos() {		
		return cantidadCampos;
	}

}
