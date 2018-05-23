package com.economizate.servicios;

import java.io.IOException;
import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.impl.ConvertListaMovimientosToString;
import com.economizate.servicios.impl.ExcelWriter;
import com.economizate.servicios.impl.MovimientosSheet;
import com.economizate.servicios.impl.ParserType;
import com.economizate.servicios.impl.PdfWriter;
import com.economizate.servicios.impl.TXTWriter;
import com.economizate.servicios.impl.TransformadorMovimientos;

public class FactoryWriterMovimientos {

	public static BaseWriter getWriter(String nombreArchivo, List<MovimientoMonetario> movimientos, IConversor<MovimientoMonetario> conversor) throws IOException {
		
		String[] separador = nombreArchivo.split("\\.");
		
		if(separador.length == 1) {
			throw new IOException("No es un tipo de archivo válido para generar.");
		} else if(separador[separador.length - 1].toUpperCase().equals(ParserType.PDF.toString())) {
			return new PdfWriter(nombreArchivo, new TransformadorMovimientos(movimientos, "Movimientos.xsl"));
		} else if(separador[separador.length - 1].toUpperCase().equals(ParserType.TXT.toString())) {
			return new TXTWriter(nombreArchivo, ConvertListaMovimientosToString.getRegistros(movimientos, conversor));
		} else if(separador[separador.length - 1].toUpperCase().equals(ParserType.XLSX.toString())) {
			return new ExcelWriter(nombreArchivo, new MovimientosSheet(movimientos));			
		} 
		
		throw new IOException("No es un tipo de archivo válido para generar.");
		
		
	}
}
