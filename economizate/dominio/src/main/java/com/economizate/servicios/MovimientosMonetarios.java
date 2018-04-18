package com.economizate.servicios;

import java.util.Date;
import java.util.List;

import com.economizate.entidades.MovimientoMonetario;

public interface MovimientosMonetarios {
	
	public void agregarIngreso(MovimientoMonetario ingreso);
	
	public void agregarEgreso(MovimientoMonetario egreso);
	
	public MovimientoMonetario obtenerIngresosPorDescripcion(String descripcion);
	
	public MovimientoMonetario obtenerEgesosPorDescripcion(String descripcion);
	
	public List<MovimientoMonetario> obtenerIngresosPorFecha(Date fecha);
	
	public List<MovimientoMonetario> obtenerEgresosPorFecha(Date fecha);

}
