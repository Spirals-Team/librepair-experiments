package com.economizate.servicios.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IParserRegistro;

public class ParserRegistroFechaSinCuota implements IParserRegistro {

int cantidadCampos;
	
	public ParserRegistroFechaSinCuota() {
		this.cantidadCampos = 4;
	}
	
	SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public MovimientoMonetario getObjeto(String[] stringarray) throws NumberFormatException, ParseException {
		MovimientoMonetario movimiento = null;
		
			movimiento = new MovimientoMonetario(stringarray[1], stringarray[2], 
					Double.parseDouble(stringarray[3]), formater.parse(stringarray[0]));
		
        return movimiento; 
	}

	@Override
	public int getCantidadCampos() {		
		return cantidadCampos;
	}
}
