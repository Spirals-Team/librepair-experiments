package com.economizate.servicios.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.economizate.servicios.ImportadorStrategy;

public class TxtImportadorStrategy implements ImportadorStrategy {

	@Override
	public String importarFile(String filename) throws IOException {
		String content = null;
	    File file = new File(filename); // For example, foo.txt
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
