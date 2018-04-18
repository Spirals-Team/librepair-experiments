package com.economizate.servicios.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.Criterio;

public class RangoFechaCriterio implements Criterio {

	private Date fechaDesde;
	private Date fechaHasta;
	
	public RangoFechaCriterio(Date fechaDesde, Date fechaHasta) {
		if(fechaHasta.before(fechaDesde)) {
			throw new IllegalArgumentException("La fecha hasta debe ser posterior a la fecha desde");
		}
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
	}
	
	@Override
	public List<MovimientoMonetario> filtrarMovimientos(
			List<MovimientoMonetario> movimientos) {
		
		List<MovimientoMonetario> movsFecha = new ArrayList<MovimientoMonetario>();
		
		for (MovimientoMonetario mov : movimientos) {
			if(mov.getFecha().after(fechaDesde) && mov.getFecha().before(fechaHasta)) {
				movsFecha.add(mov);
			}
		}
		
		return null;
	}

}
