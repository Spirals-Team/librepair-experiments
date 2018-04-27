package com.economizate.servicios.impl;

import java.util.Date;
import java.util.List;
import java.util.Observer;

import com.economizate.conector.ConectorCuenta;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Movimientos;

public class CuentaImpl implements com.economizate.servicios.Cuenta{
	
	private ConectorCuenta conector = new ConectorCuenta();

	private Cuenta cuenta;
	
	public CuentaImpl() {
		this.cuenta = new Cuenta();
	}
	
	public CuentaImpl(Observer observer) {
		cuenta.agregarObserver(observer);
	}
	
	@Override
	public Movimientos obtenerHistorialMovimientos() {
		//return conector.obtenerHistorialDeMovimientos();
		return null;
	}

	@Override
	public double obtenerSaldoTotal() {
		//return conector.obtenerSaldo();
		return cuenta.getTotal();
	}

	@Override
	public List<MovimientoMonetario> obtenerIngresosPorFecha(Date fecha) {
		return new MovimientosMonetariosImpl().obtenerIngresosPorFecha(fecha);
	}

	@Override
	public List<MovimientoMonetario> obtenerEgresosPorFecha(Date fecha) {
		return new MovimientosMonetariosImpl().obtenerEgresosPorFecha(fecha);
	}

	@Override
	public void agregarIngreso(MovimientoMonetario ingreso) {
		
	}

	@Override
	public void agregarEgreso(MovimientoMonetario egreso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cambiarSaldoTotal(double importe) {
		//conector.cambiarSaldo(importe);
	}

	@Override
	public double obtenerTotalMovimientos(List<MovimientoMonetario> movimientos) {
		
		double total = 0;
		
		for (MovimientoMonetario mov : movimientos) {
			total += mov.getImporte();
		}		
		return total;
	}

	@Override
	public void agregarMovimiento(MovimientoMonetario movimiento) {
		cuenta.agregarMovimiento(movimiento);
		
	}

	@Override
	public double obtenerSaldoTotalPorPeriodo(int mes, int anio) {
		// TODO Auto-generated method stub
		return cuenta.getTotal(mes, anio);
	}

}
