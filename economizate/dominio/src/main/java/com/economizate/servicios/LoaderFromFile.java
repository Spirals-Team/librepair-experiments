package com.economizate.servicios;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.xml.bind.ValidationException;


public interface LoaderFromFile <T> {

	public void cargarDatos(IParserRegistro parser) throws IOException, ParseException;
	
	public List<T> getDatos() throws ValidationException;
	
}
