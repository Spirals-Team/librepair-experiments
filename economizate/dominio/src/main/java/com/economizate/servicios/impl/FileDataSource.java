package com.economizate.servicios.impl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.economizate.servicios.BaseReader;
import com.economizate.servicios.BaseWriter;
import com.economizate.servicios.DataSource;
import com.economizate.servicios.FactoryReader;
import com.economizate.servicios.FactoryWriterMovimientos;

public class FileDataSource implements DataSource{

	private OutputStream out;

	private String nombreArchivo;
	
	public FileDataSource(String nombreArchivo) throws FileNotFoundException {
		
		this.nombreArchivo = nombreArchivo;
		
		out = new FileOutputStream(nombreArchivo);
		out = new BufferedOutputStream(out); 
	}
	
	@Override
	public void writeData(String data) {
				
		BaseWriter writer = new TXTWriter(nombreArchivo, data);
		
		try {
			writer.write();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

	@Override
	public String readData() {
		BaseReader reader;
		try {
			reader = FactoryReader.getReader(nombreArchivo);
			return reader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
