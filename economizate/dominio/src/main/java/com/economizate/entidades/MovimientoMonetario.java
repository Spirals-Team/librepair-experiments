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
			
	
	public MovimientoMonetario(String descripcion, String observacion, double importe) {
		this.descripcion = descripcion;
		this.observacion = observacion;
		this.importe = importe;
		fecha = new Date();
	}

	public MovimientoMonetario(String descripcion, double importe) {
		this.descripcion = descripcion;
		this.importe = importe;
		fecha = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
}
