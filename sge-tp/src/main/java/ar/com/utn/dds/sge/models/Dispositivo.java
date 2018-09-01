package ar.com.utn.dds.sge.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ar.com.utn.dds.sge.state.Encendido;
import ar.com.utn.dds.sge.state.Estado;



/**
 * Clase que modela la entidad dispositivo
 * @author Grupo 2
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"tipo", "nombre", "consumo", "estado", "mensualMin", "mensualMax"})
public class Dispositivo{
	
	
	@JsonProperty("tipo")
	private String tipo;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("consumo")
	private Float consumo;

	@JsonProperty("mensualMin")
	private double mensualMin;

	@JsonProperty("mensualMax")
	private double mensualMax;
	
	@JsonProperty("optimizable")
	private boolean optimizable;
	
	private double hsRestantes;


	public Dispositivo() {
	}
	
	public Dispositivo(String tipo, String nombre, Float consumo, double mensualMin, double mensualMax, boolean optimizable) {
		setTipo(tipo);
		setNombre(nombre);
		setConsumo(consumo);
		setMensualMin(mensualMin);
		setMensualMax(mensualMax);
		setOptimizable(optimizable);
	}


	public boolean esInteligente() {
		return (this instanceof DispositivoInteligente) || (this instanceof DispositivoAdaptado);
	}
	
	public void prender() {
	}
	
	public void apagar() {		
	}
	
	public void activarModoAhorro() {
	}
	
	/*
	 * GETTERS Y SETTERS
	 */
	
	public Estado getEstado() {
		return new Encendido();
	}
	
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the consumo
	 */
	public Float getConsumo() {
		return consumo;
	}

	/**
	 * @param consumo the consumo to set
	 */
	public void setConsumo(Float consumo) {
		this.consumo = consumo;
	}
	
	/**
	 * @return the mensualMin
	 */
	public double getMensualMin() {
		return this.mensualMin;
	}

	/**
	 * @param consumo the mensualMin to set
	 */
	public void setMensualMin(double mensualMin) {
		this.mensualMin = mensualMin;
	}
	
	/**
	 * @return the mensualMax
	 */
	public double getMensualMax() {
		return this.mensualMax;
	}
	
	/**
	 * @param consumo the mensualMax to set
	 */
	public void setMensualMax(double mensualMax) {
		this.mensualMax = mensualMax;
	}
		
	/**
	/**
	 * @return the optimizable
	 */
	public boolean getOptimizable() {
		return this.optimizable;
	}
	
	/**
	 * @param consumo the mensualMax to set
	 */
	public void setOptimizable(boolean optimizable) {
		this.optimizable = optimizable;
	}
		
	/**
	 * @param consumo the hsRestantes to set
	 */
	public void setHsRestantes(double hsRestantes) {
		this.hsRestantes = hsRestantes;
	}
	
	/**
	 * @param consumo the hsRestantes to get
	 */
	public double getHsRestantes() {
		return this.hsRestantes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consumo == null) ? 0 : consumo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dispositivo other = (Dispositivo) obj;
		if (consumo == null) {
			if (other.consumo != null)
				return false;
		} else if (!consumo.equals(other.consumo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

}
