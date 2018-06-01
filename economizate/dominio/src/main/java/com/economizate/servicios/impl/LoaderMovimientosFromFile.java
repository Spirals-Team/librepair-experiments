package com.economizate.servicios.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.ValidationException;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.BaseReader;
import com.economizate.servicios.FactoryReader;
import com.economizate.servicios.IParserRegistro;
import com.economizate.servicios.LoaderFromFile;
import com.economizate.servicios.ParserListaRegistros;

public class LoaderMovimientosFromFile implements LoaderFromFile<MovimientoMonetario> {

	private List<MovimientoMonetario> registrosMovimientos;
	private String nombreArchivo;
	
	public LoaderMovimientosFromFile(String nombreArchivo) {				
		this.nombreArchivo = nombreArchivo;
	}
	
	private void validarMovimientos() throws ValidationException {
		Iterator<MovimientoMonetario> iterator = registrosMovimientos.iterator();
		int linea = 0;
		while (iterator.hasNext()) {
			linea++;
			MovimientoMonetario r = iterator.next();
			r.setValidador(new ConcreteValidadorRegistroStrategy());
			if(!r.isValid()) {
				throw new ValidationException("Registro inválido. Línea " + linea);
			}
			
		}
	}

	@Override
	public void cargarDatos(IParserRegistro parserRegistro) throws IOException, ParseException {
		BaseReader importador = FactoryReader.getReader(nombreArchivo);
		ParserListaRegistros<MovimientoMonetario> parser = new ParserListRegistrosMovimientos(importador.read(), parserRegistro);
		registrosMovimientos = parser.parse();		
	}

	@Override
	public List<MovimientoMonetario> getDatos() throws ValidationException {
		validarMovimientos();
		return registrosMovimientos;
	}
	
	
}
