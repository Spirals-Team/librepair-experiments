package com.economizate.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.economizate.servicios.ValidadorRegistroStrategy;

public class MovimientoMonetario {
	
	private long id;
	private Date fecha;
	private String descripcion;
	private String observacion;
	private Double importe;
	private int cantidadCuotas;
	
	public MovimientoMonetario(String descripcion, String observacion, double importe, Date fecha) {
		this.descripcion = descripcion;
		this.observacion = observacion;
		this.importe = importe;
		this.fecha = fecha;
	}
	
	public MovimientoMonetario(String descripcion, String observacion, double importe, int cantidadCuotas) {
		this.descripcion = descripcion;
		this.observacion = observacion;
		this.importe = importe;
		this.cantidadCuotas = cantidadCuotas;
		fecha = new Date();
	}

	public MovimientoMonetario(String descripcion, String observacion, double importe) {
		this.descripcion = descripcion;
		this.observacion = observacion;
		this.importe = importe;
		fecha = new Date();
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	public int getCantidadCuotas() {
		return cantidadCuotas;
	}

	public void setCantidadCuotas(int cantidadCuotas) {
		this.cantidadCuotas = cantidadCuotas;
	}

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return "Movimiento Monetario: "+ descripcion +"\nID: " + id + "\n Fecha: " + formatter.format(fecha) + ",\n Descripcion:" + ",\n Observacion:"
				+ observacion + ",\n Importe=" + importe;
	}
	
	protected ValidadorRegistroStrategy validador;
	
	public void setValidador(ValidadorRegistroStrategy v) {
		this.validador = v;
	}
	
	public boolean isValid() {
		return validador.validate(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimientoMonetario other = (MovimientoMonetario) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		if (observacion == null) {
			if (other.observacion != null)
				return false;
		} else if (!observacion.equals(other.observacion))
			return false;
		return true;
	}
	
	
	
}
