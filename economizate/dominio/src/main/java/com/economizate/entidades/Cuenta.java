package com.economizate.entidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Observer;


public class Cuenta extends java.util.Observable{
	
	//private long id;
	private Movimientos movimientos;
	
	private double total;
	private HashMap<String, Double> totalPorMes;
	private HashMap<String, Movimientos> movimientosPorMes;
	
	public Cuenta () {
		movimientosPorMes = new LinkedHashMap<String, Movimientos>();
		totalPorMes = new LinkedHashMap<String, Double>();
		this.movimientos = new Movimientos();
		this.total = 0;
	}
	
	
	public void agregarObserver(Observer o) {
		this.addObserver(o);
	}
	
	
	public Movimientos getMovimientos() {
		return movimientos;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void modificarTotal(double total) {
		this.total = total;
	}
	
	public void setTotal(double total) {
		this.total = total;
		setChanged();
		notifyObservers(total); //MVC
	}
	
	public void agregarMovimiento(MovimientoMonetario movimiento) {
				
		DateFormat df = new SimpleDateFormat("yyyyMM");
		
		String periodo = df.format(movimiento.getFecha());
		
		agregarMovimientoAMes(movimiento, periodo);
		
		sumarImporteATotalMes(movimiento, periodo);
		
		agregarCuotas(movimiento);
		
		if(movimiento.getImporte() < 0) {
			setChanged();
			notifyObservers(movimiento.getImporte());
		}
		
	}
	

	private void agregarCuotas(MovimientoMonetario movimiento) {
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(movimiento.getFecha());
		
		DateFormat df = new SimpleDateFormat("yyyyMM");

		
		for (int i = 1; i <= movimiento.getCantidadCuotas(); i++) {
			cal.add(Calendar.MONTH, i);
			String periodo = df.format(cal.getTime());
			agregarMovimientoAMes(movimiento, periodo);
			
			sumarImporteATotalMes(movimiento, periodo);
		}
		
	}


	private void sumarImporteATotalMes(MovimientoMonetario movimiento,
			String periodo) {
		Double totalMes = totalPorMes.get(periodo);		
		if(totalMes == null) {			
			totalPorMes.put(periodo, movimiento.getImporte());
		} else {
			totalMes += movimiento.getImporte();
			totalPorMes.put(periodo, totalMes);
		}
	}


	private void agregarMovimientoAMes(MovimientoMonetario movimiento,
			String periodo) {
		Movimientos movimientos = movimientosPorMes.get(periodo);
		if(movimientos == null) {
			movimientos = new Movimientos();
			movimientosPorMes.put(periodo, movimientos);
		}
		this.movimientos.agregarMovimiento(movimiento);
	}


	public Double getTotal(Integer mes, Integer anio) {
		
		String mesString = mes.toString().length() == 1 ? "0" + mes.toString() : mes.toString(); 
		String anioString = anio.toString();
		return totalPorMes.get(anioString + mesString) != null ? totalPorMes.get(anioString + mesString) : (double) 0;
}
}
