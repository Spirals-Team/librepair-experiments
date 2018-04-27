package com.economizate.servicios;

import java.util.Date;
import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Movimientos;

public interface Cuenta {
	
	public void agregarIngreso(MovimientoMonetario ingreso);
	
	public void agregarEgreso(MovimientoMonetario egreso);
	
	public void agregarMovimiento(MovimientoMonetario egreso);
	
	public Movimientos obtenerHistorialMovimientos();
	
	public double obtenerSaldoTotal();
	
	public void cambiarSaldoTotal(double importe);
	
	public List<MovimientoMonetario> obtenerIngresosPorFecha(Date fecha);
	
	public List<MovimientoMonetario> obtenerEgresosPorFecha(Date fecha);

	public double obtenerTotalMovimientos(List<MovimientoMonetario> movimientos);
	
	public double obtenerSaldoTotalPorPeriodo(int mes, int anio);
}
