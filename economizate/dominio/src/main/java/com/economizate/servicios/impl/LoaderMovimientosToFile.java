package com.economizate.servicios.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.BaseWriter;
import com.economizate.servicios.FactoryWriterMovimientos;
import com.economizate.servicios.IConversor;
import com.economizate.servicios.LoaderToFile;

public class LoaderMovimientosToFile implements LoaderToFile<MovimientoMonetario> {

	private IConversor<MovimientoMonetario> conversor;
	
	List<MovimientoMonetario> movimientos;
	
	@Override
	public void cargarDatos(List<MovimientoMonetario> movimientos) throws IOException,
			ParseException {
		
		conversor = new ConversorMovimientoSinCuota(";");
		this.movimientos = movimientos;
		
	}

	@Override
	public void generarArchivo(String nombreArchivo) throws IOException {
		BaseWriter writer = FactoryWriterMovimientos.getWriter(nombreArchivo, movimientos, conversor);
		writer.write();
		
	}

	@Override
	public void cargarDatos(List<MovimientoMonetario> movimientos,
			IConversor<MovimientoMonetario> conversor) throws IOException,
			ParseException {	
		this.conversor = conversor;	
		this.movimientos = movimientos;		
		
	}

}
