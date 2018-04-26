package com.economizate.servicios.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Propiedad {

	private static Propiedad instance = null;
	
	public static Propiedad getInstance() {
	      if(instance == null) {
	         instance = new Propiedad();
	      }
	      return instance;
	   }
	
	public String getPropiedad(String key)  {
		InputStream inputStream;
		
		String value = "";
		try {			
			inputStream =  Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
			
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
