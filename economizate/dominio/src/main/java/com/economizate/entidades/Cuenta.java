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
	private List<MovimientoMonetario> movimientos;
	private double total;
	private HashMap<String, Double> totalPorMes;
	private HashMap<String, List<MovimientoMonetario>> movimientosPorMes;
	
	public Cuenta () {
		movimientosPorMes = new LinkedHashMap<String, List<MovimientoMonetario>>();
		totalPorMes = new LinkedHashMap<String, Double>();
		this.movimientos = new ArrayList<>();
		this.total = 0;
	}
	
	public Cuenta (Observer o) {
		this();
		this.addObserver(o);
	}
	
	public Cuenta (Observer o, double total) {
		this.addObserver(o);
		this.movimientos = new ArrayList<>();
		this.total = total;
	}
	
	public Cuenta (List<MovimientoMonetario> movimientos, double total) {
		this.movimientos = movimientos;
		this.total = total;
	}
	
	public List<MovimientoMonetario> getMovimientos() {
		return movimientos;
	}
	
	public void setMovimientos(List<MovimientoMonetario> movimientos) {
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
			movimientos = new ArrayList<MovimientoMonetario>();
			movimientosPorMes.put(periodo, movimientos);
		}
		this.movimientos.add(movimiento);
		
		Double totalMes = totalPorMes.get(periodo);		
		if(totalMes == null) {			
			totalPorMes.put(periodo, movimiento.getImporte());
		} else {
			totalMes += movimiento.getImporte();
			totalPorMes.put(periodo, totalMes);
		}
		
	}

	public double getTotal(Integer mes, Integer anio) {
		
		String mesString = mes.toString().length() == 1 ? "0" + mes.toString() : mes.toString(); 
		String anioString = anio.toString();
		return totalPorMes.get(anioString + mesString);
	}
}
