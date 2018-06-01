package com.economizate.servicios.impl;

import java.text.ParseException;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IParserRegistro;

public class ParserRegistroMovimiento {

	private String registro;
	
	private String delimitador = ";";
	
	private IParserRegistro parser;
	
	public ParserRegistroMovimiento(String registro, IParserRegistro parser) {
		this.registro = registro;
		this.parser = parser;
	}
	
	public ParserRegistroMovimiento(String registro, String delimitador, IParserRegistro parser) {
		this.delimitador = delimitador;
		this.registro = registro;
		this.parser = parser;
	}
	
	public MovimientoMonetario parse() throws ParseException, NumberFormatException {
		String[] campos = this.registro.split(delimitador);
		if(campos.length != parser.getCantidadCampos()) {
			throw new ParseException("El registro de movimiento debe contener " + parser.getCantidadCampos() + " campos", -10);
		}				
		MovimientoMonetario reg = ConvertToMovimiento.getObject(registro, delimitador, parser);
		
		return reg;
	}
}
