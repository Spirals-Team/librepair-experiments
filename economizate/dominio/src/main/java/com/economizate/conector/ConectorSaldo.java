package com.economizate.conector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observer;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Cuenta;

public class ConectorSaldo {
	
	private double total = 100;
	private Cuenta saldo;
	private Observer observer;
		
	
	private void agregarMovimientos() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		try {
			MovimientoMonetario ingreso = new MovimientoMonetario("cuenta sueldo", "ninguna", 95, formater.parse("20180315"));
			MovimientoMonetario ingreso2 = new MovimientoMonetario("horas extras", "ninguna", 10, formater.parse("20180331"));
			MovimientoMonetario ingreso3 = new MovimientoMonetario("Luz", "Servicio", -742, formater.parse("20180413"));
			MovimientoMonetario ingreso4 = new MovimientoMonetario("Gas", "Servicio", -325, formater.parse("20180426"));
			MovimientoMonetario ingreso5 = new MovimientoMonetario("Sueldo", "Sueldo", 25744, formater.parse("20180418"));
			MovimientoMonetario ingreso6 = new MovimientoMonetario("Tarjeta", "Gastos Generales", -6214, formater.parse("20180423"));
			saldo.getMovimientos().add(ingreso);
			saldo.getMovimientos().add(ingreso2);
			saldo.getMovimientos().add(ingreso3);
			saldo.getMovimientos().add(ingreso4);
			saldo.getMovimientos().add(ingreso5);
			saldo.getMovimientos().add(ingreso6);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Cuenta nuevoSaldo() {
		if (saldo == null) {
			saldo = new Cuenta();
			agregarMovimientos();						
			saldo.setTotal(obtenerSaldo());
		}
		return saldo;
	}
	
	public Cuenta nuevoSaldo(Observer o) {
		if (saldo == null) {
			observer = o;
			saldo = new Cuenta(o, obtenerSaldo());
			agregarMovimientos();
			saldo.setTotalSinObserver(obtenerSaldo());
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
