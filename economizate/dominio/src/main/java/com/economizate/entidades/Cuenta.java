package com.economizate.entidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Observer;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.collections4.map.HashedMap;

public class Cuenta extends java.util.Observable{
	
	private long id;
	//private List<MovimientoMonetario> movimientos;
	
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
	
	public Cuenta (Movimientos movimientos, double total) {
		this.movimientos = movimientos;
		this.total = total;
	}
	
	public Movimientos getMovimientos() {
		return movimientos;
	}
	
	public void setMovimientos(Movimientos movimientos) {
		this.movimientos = movimientos;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setTotalSinObserver(double total) {
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
		
		movimientos = movimientosPorMes.get(periodo);
		if(movimientos == null) {
			movimientos = new Movimientos();
			movimientosPorMes.put(periodo, movimientos);
		}
		this.movimientos.agregarMovimiento(movimiento);
		
		Double totalMes = totalPorMes.get(periodo);		
		if(totalMes == null) {			
			totalPorMes.put(periodo, movimiento.getImporte());
		} else {
			totalMes += movimiento.getImporte();
			totalPorMes.put(periodo, totalMes);
		}
		
		if(movimiento.getImporte() < 0) {
			setChanged();
			notifyObservers(movimiento.getImporte());
		}
		
	}

	public double getTotal(Integer mes, Integer anio) {
		
		String mesString = mes.toString().length() == 1 ? "0" + mes.toString() : mes.toString(); 
		String anioString = anio.toString();
		return totalPorMes.get(anioString + mesString);
	}
}
