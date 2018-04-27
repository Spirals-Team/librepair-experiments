package com.economizate.servicios.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.IParserRegistro;
import com.economizate.servicios.ParserListaRegistros;

public class ParserListRegistrosMovimientos implements ParserListaRegistros<MovimientoMonetario> {

	private List<MovimientoMonetario> registrosMovs;
	private String registros;
	private IParserRegistro parser;
	public ParserListRegistrosMovimientos(String registros, IParserRegistro parser) {
		
		this.registros  = registros;
		this.parser = parser;
	}
	
	@Override
	public List<MovimientoMonetario> parse() throws ParseException {
		
		registrosMovs = new ArrayList<MovimientoMonetario>();		
		Scanner scanner = new Scanner(registros);
		while (scanner.hasNextLine()) {
		  ParserRegistroMovimiento parseRegistro = new ParserRegistroMovimiento(scanner.nextLine(), parser);
		  registrosMovs.add(parseRegistro.parse());
		}
		scanner.close();
		return registrosMovs;
	}

}
