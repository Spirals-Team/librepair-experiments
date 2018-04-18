package com.economizate.servicios;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class BaseReader {

	protected String FileName; 
    
    public BaseReader(String FileName) 
    { 
        this.FileName = FileName; 
    } 
    
    public String Delimiter; 

    public String read() throws IOException 
    { 
    	String content = null;
	    File file = new File(FileName);
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(reader != null){
	            reader.close();
	        }
	    }
	    return content;
    } 
   
}
