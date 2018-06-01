package com.economizate.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class BaseReader {

	protected String FileName; 
    
    public BaseReader(String FileName) 
    { 
        this.FileName = FileName; 
    } 
    
    public String Delimiter; 

    public String read() throws IOException 
    { 	    
	    File initialFile = new File(FileName);
	    InputStream in = new FileInputStream(initialFile);
	    
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line).append("\r\n");
        }
        //System.out.println(out.toString());   //Prints the string content read from input stream
        reader.close();

	    return out.toString();
    } 
   
}
