package com.economizate.servicios.impl;

import java.text.ParseException;

import com.economizate.entidades.MovimientoMonetario;

public class ParserRegistroMovimiento {

	private String registro;
	
	private String delimitador = ";";
	
	public ParserRegistroMovimiento(String registro) {
		this.registro = registro;
	}
	
	public ParserRegistroMovimiento(String registro, String delimitador) {
		this.delimitador = delimitador;
		this.registro = registro;
	}
	
	public MovimientoMonetario parse() throws ParseException {
		String[] campos = this.registro.split(delimitador);
		if(campos.length != 4) {
			throw new ParseException("El registro de movimiento debe contener 4 campos", -10);
		}				
		MovimientoMonetario reg = ConvertToMovimiento.getObject(registro, delimitador);
		
		return reg;
	}
}
