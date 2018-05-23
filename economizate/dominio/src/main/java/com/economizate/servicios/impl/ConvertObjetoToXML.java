package com.economizate.servicios.impl;

import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.thoughtworks.xstream.XStream;

public class ConvertObjetoToXML {

	public static String convert(List<MovimientoMonetario> objeto) {
		XStream xstream = new XStream();
	    

	    String xml = xstream.toXML(objeto);
	    return xml;
	}
}
