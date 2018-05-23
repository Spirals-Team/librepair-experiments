package com.economizate.nubeManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NubePropiedades {
	
private static NubePropiedades instance = null;
	
	public static NubePropiedades getInstance() {
	      if(instance == null) {
	         instance = new NubePropiedades();
	      }
	      return instance;
	   }
	
	public String getPropiedad(String key)  {
		InputStream inputStream;
		
		String value = "";
		try {			
			inputStream =  Thread.currentThread().getContextClassLoader().getResourceAsStream("nubeConfig.properties");
			
		    Properties props = new Properties();
		    props.load(inputStream);		 
		    value = props.getProperty(key);	
		    inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

}
