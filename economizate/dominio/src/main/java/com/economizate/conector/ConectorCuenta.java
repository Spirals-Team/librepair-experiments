package com.economizate.conector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observer;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.Movimientos;

public class ConectorCuenta {
	
	private double total = 100;
	private Cuenta cuenta;
	private Observer observer;
		
	public ConectorCuenta() {
		this.cuenta = new Cuenta();
		agregarMovimientos();
	}
	
	private void agregarMovimientos() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		try {
			MovimientoMonetario ingreso = new MovimientoMonetario("cuenta sueldo", "ninguna", 95.0, formater.parse("20180315"));
			MovimientoMonetario ingreso2 = new MovimientoMonetario("horas extras", "ninguna", 10.0, formater.parse("20180331"));
			MovimientoMonetario ingreso3 = new MovimientoMonetario("Luz", "Servicio", -15.0, formater.parse("20180413"));
			MovimientoMonetario ingreso4 = new MovimientoMonetario("Gas", "Servicio", -15.0, formater.parse("20180426"));
			MovimientoMonetario ingreso5 = new MovimientoMonetario("Sueldo", "Sueldo", 95.0, formater.parse("20180418"));
			MovimientoMonetario ingreso6 = new MovimientoMonetario("Tarjeta", "Gastos Generales", -70.0, formater.parse("20180423"));
			cuenta.getMovimientos().agregarMovimiento(ingreso);
			cuenta.getMovimientos().agregarMovimiento(ingreso2);
			cuenta.getMovimientos().agregarMovimiento(ingreso3);
			cuenta.getMovimientos().agregarMovimiento(ingreso4);
			cuenta.getMovimientos().agregarMovimiento(ingreso5);
			cuenta.getMovimientos().agregarMovimiento(ingreso6);
			
			cuenta.setTotal(cuenta.getMovimientos().getTotal());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Cuenta nuevaCuenta() {
		/*if (cuenta == null) {
			cuenta = new Cuenta();
			agregarMovimientos();						
			//cuenta.setTotal(obtenerSaldo());
			total = cuenta.getMovimientos().getTotal();
		}*/
		return cuenta;
	}
	
	public Cuenta nuevaCuenta(Observer o) {
		/*if (cuenta == null) {
			observer = o;
			cuenta = new Cuenta();
			agregarMovimientos();
			cuenta.agregarObserver(o);
			cuenta.modificarTotal(obtenerSaldo());
		}*/
		return cuenta;
	}
	
	public Movimientos obtenerHistorialDeMovimientos(){
		return nuevaCuenta().getMovimientos();
	}
	
	/*public double obtenerSaldo() {
		return total;
	}
	
	public void cambiarSaldo(double importe) {
		total = importe;
		nuevaCuenta().setTotal(total);
	}
	
	public void addObserver(Observer observer) {
		this.observer = observer;
	}*/
	
	public Movimientos getMovimientos() {
		return cuenta.getMovimientos();
	}

}
