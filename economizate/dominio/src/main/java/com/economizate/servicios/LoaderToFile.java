package com.economizate.servicios;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public interface LoaderToFile <T> {

	public void cargarDatos(List<T> registros) throws IOException, ParseException;
	
	public void cargarDatos(List<T> registros, IConversor<T> conversor) throws IOException, ParseException;
	
	public void generarArchivo(String nombreArchivo) throws IOException ;
}
