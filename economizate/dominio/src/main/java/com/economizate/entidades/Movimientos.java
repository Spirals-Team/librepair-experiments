package com.economizate.entidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.economizate.servicios.Criterio;

public class Movimientos {

	private List<MovimientoMonetario> movimientos;
	
	private Double total;
	
	public Movimientos() {
		movimientos = new ArrayList<MovimientoMonetario>();
		total = 0.0;
	}
	
	public void agregarMovimiento(MovimientoMonetario movimiento){
		movimientos.add(movimiento);
		total = total + movimiento.getImporte();
	}
	
	public Movimientos filtrarPorCriterio(Criterio criterio) {
		
		return criterio.filtrarMovimientos(movimientos);
		
	}
	
	public List<MovimientoMonetario> getTodos() {
		
		return movimientos;		
	}
	
	public double getTotal() {
		return total;
	}
	
	@Override
	public String toString() {
		return movimientos.toString();
	}
}
