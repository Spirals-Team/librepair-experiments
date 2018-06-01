package com.economizate.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.economizate.servicios.BaseWriter;

public class TXTWriter extends BaseWriter {

	private String contenidoArchivo;
	
	public TXTWriter(String nombreArchivo, String registros) {
		super(nombreArchivo);
		this.contenidoArchivo = registros;
	}

	@Override
	public void write() throws IOException, FileNotFoundException { 
    	super.write(contenidoArchivo);
    } 
	
}
