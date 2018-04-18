package com.economizate.conector;

import java.util.List;
import java.util.Observer;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Cuenta;

public class ConectorSaldo {
	
	private double total = 100;
	private Cuenta saldo;
	private Observer observer;
		
	public Cuenta nuevoSaldo() {
		if (saldo == null) {
			saldo = new Cuenta();
			MovimientoMonetario ingreso = new MovimientoMonetario("cuenta sueldo", 95);
			MovimientoMonetario ingreso2 = new MovimientoMonetario("horas extras", 10);
			MovimientoMonetario ingreso3 = new MovimientoMonetario("supermercado", -5);
			saldo.getMovimientos().add(ingreso);
			saldo.getMovimientos().add(ingreso2);
			saldo.getMovimientos().add(ingreso3);
			saldo.getMovimientos().add(ingreso);
			saldo.setTotal(obtenerSaldo());
		}
		return saldo;
	}
	
	public Cuenta nuevoSaldo(Observer o) {
		if (saldo == null) {
			observer = o;
			saldo = new Cuenta(o, obtenerSaldo());
			MovimientoMonetario ingreso = new MovimientoMonetario("cuenta sueldo", 95);
			MovimientoMonetario ingreso2 = new MovimientoMonetario("horas extras", 10);
			MovimientoMonetario ingreso3 = new MovimientoMonetario("supermercado", -5);
			saldo.getMovimientos().add(ingreso);
			saldo.getMovimientos().add(ingreso2);
			saldo.getMovimientos().add(ingreso3);
			saldo.setTotalTest(obtenerSaldo());
		}
		return saldo;
	}
	
	public List<MovimientoMonetario> obtenerHistorialDeMovimientos(){
		return nuevoSaldo().getMovimientos();
	}
	
	public double obtenerSaldo() {
		return total;
	}
	
	public void cambiarSaldo(double importe) {
		total = importe;
		nuevoSaldo().setTotal(total);
	}
	
	public void addObserver(Observer observer) {
		this.observer = observer;
	}

}
