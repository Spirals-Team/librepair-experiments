package com.economizate.servicios.impl;

import java.util.Date;
import java.util.List;

import com.economizate.conector.ConectorMovimientoMonetario;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.MovimientosMonetarios;

public class MovimientosMonetariosImpl implements MovimientosMonetarios {
	
	private ConectorMovimientoMonetario conector = new ConectorMovimientoMonetario();

	@Override
	public MovimientoMonetario obtenerIngresosPorDescripcion(String descripcion) {
		return conector.buscarIngresoPorDescripcion(descripcion);
	}

	@Override
	public MovimientoMonetario obtenerEgesosPorDescripcion(String descripcion) {
		return conector.buscarEgresoPorDescripcion(descripcion);
	}

	@Override
	public List<MovimientoMonetario> obtenerIngresosPorFecha(Date fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovimientoMonetario> obtenerEgresosPorFecha(Date fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void agregarIngreso(MovimientoMonetario ingreso) {
		conector.agregarIngreso(ingreso);
	}

	@Override
	public void agregarEgreso(MovimientoMonetario egreso) {
		
	}

}
