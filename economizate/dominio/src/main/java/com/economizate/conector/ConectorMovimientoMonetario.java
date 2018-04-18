package com.economizate.conector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.economizate.entidades.MovimientoMonetario;

public class ConectorMovimientoMonetario {
	
	List<MovimientoMonetario> ingresos = new ArrayList<>();
	List<MovimientoMonetario> egresos = new ArrayList<>();
	
	public MovimientoMonetario buscarIngresoPorDescripcion(String descripcion) {
		return ingresos.stream()
				.filter(i -> descripcion.equals(i.getDescripcion()))
				.collect(Collectors.toList()).get(0);
	}
	
	public MovimientoMonetario buscarEgresoPorDescripcion(String descripcion) {
		return new MovimientoMonetario("joda", -100);
	}
	
	public void agregarIngreso(MovimientoMonetario ingreso) {
		ingreso.setFecha(new Date());
		ingresos.add(ingreso);
	}
	
}
