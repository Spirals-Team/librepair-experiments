package com.economizate.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Logger;

public class Cuenta extends java.util.Observable{
	
	private static Logger logger = Logger.getLogger(Cuenta.class.getName());
	
	private long id;
	private List<MovimientoMonetario> movimientos;
	private double total;
	
	public Cuenta () {
		this.movimientos = new ArrayList<>();
		this.total = 0;
	}
	
	public Cuenta (Observer o) {
		this.addObserver(o);
		this.movimientos = new ArrayList<>();
		this.total = 0;
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
	
	public void setTotal(double total) {
		logger.info("Ingreso a Saldo con " + this.countObservers() + " cantidad de observers");
		this.total = total;
		setChanged();
		notifyObservers(total); //MVC
		logger.info("Saldo cambiado exitosamente");
	}
	
	public void setTotalTest(double total) {
		this.total = total;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
