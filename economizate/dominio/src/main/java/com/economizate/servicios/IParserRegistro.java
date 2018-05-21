package com.economizate.servicios;

import java.text.ParseException;

import com.economizate.entidades.MovimientoMonetario;

public interface IParserRegistro {

	public MovimientoMonetario getObjeto(String[] stringarray) throws NumberFormatException, ParseException;
	
	public int getCantidadCampos();
}
