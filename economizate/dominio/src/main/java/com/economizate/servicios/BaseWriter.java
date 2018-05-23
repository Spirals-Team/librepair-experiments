package com.economizate.servicios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public abstract class BaseWriter {

	protected String nombreArchivo; 
	
    public BaseWriter(String nombreArchivo) 
    { 
        this.nombreArchivo = nombreArchivo; 
    } 

    public void write(String contenidoArchivo) throws IOException { 
    	File statText = new File(nombreArchivo);
        FileOutputStream is = new FileOutputStream(statText);
        OutputStreamWriter osw = new OutputStreamWriter(is);    
        Writer w = new BufferedWriter(osw);
        w.write(contenidoArchivo);
        w.close();
    } 
    
    public void write() throws IOException { 
    	
    } 
}
