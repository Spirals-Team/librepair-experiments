package com.economizate.servicios;

import java.io.IOException;

import com.economizate.servicios.impl.CSVReader;
import com.economizate.servicios.impl.ExcelReader;
import com.economizate.servicios.impl.ParserType;
import com.economizate.servicios.impl.PipeReader;

public class FactoryReader {
	
	public static BaseReader getParseador(String nombreArchivo) throws IOException {
		
		String[] separador = nombreArchivo.split("\\.");
		
		if(separador.length == 1) {
			throw new IOException("No es un tipo de archivo válido para procesar.");
		} else if(separador[separador.length - 1].toUpperCase().equals(ParserType.CSV.toString())) {
			return new CSVReader(nombreArchivo);
		} else if(separador[separador.length - 1].toUpperCase().equals(ParserType.TXT.toString())) {
			return new PipeReader(nombreArchivo);
		} else if(separador[separador.length - 1].toUpperCase().equals(ParserType.XLSX.toString())) {
			return new ExcelReader(nombreArchivo);			
		} 
		
		throw new IOException("No es un tipo de archivo válido para procesar.");
		
		
	}
}
