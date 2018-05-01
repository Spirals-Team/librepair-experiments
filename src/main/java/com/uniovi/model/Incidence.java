package com.uniovi.model;

import java.util.HashMap;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Clase que representa una incidencia
 * @author Tania Álvarez Díaz
 *
 */
@Entity
public class Incidence {
	
	public enum Estado {
	    ABIERTA, CERRADA, SOLUCIONANDOLA
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private String descripcion;
	private Location localizacion;
	@Transient
	private Set<String> etiquetas;
	private HashMap<String, String> campos;
	private Estado estado;

	public Incidence() {
	}

	public Incidence(String descripcion, String localizacion, Set<String> etiquetas, HashMap<String, String> campos,
			Estado estado) {
		super();
		this.descripcion = descripcion;
		this.localizacion = obtenerLocalizacion(localizacion);
		this.etiquetas = etiquetas;
		this.campos = campos;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Location getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = obtenerLocalizacion(localizacion);
	}

	/**
	 * Método que devuelve la localizacion a partir del string que se pasa por
	 * parametro
	 * 
	 * @param localizacion2
	 * @return
	 */
	private Location obtenerLocalizacion(String localizacion) {
		String[] trozos = localizacion.split(";");
		double latitude = Double.parseDouble(trozos[0]);
		double longitud = Double.parseDouble(trozos[1]);
		return new Location(latitude, longitud);
	}

	public Set<String> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(Set<String> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public HashMap<String, String> getCampos() {
		return campos;
	}

	public void setCampos(HashMap<String, String> campos) {
		this.campos = campos;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campos == null) ? 0 : campos.hashCode());
		result = prime * result + ((etiquetas == null) ? 0 : etiquetas.hashCode());
		result = prime * result + ((localizacion == null) ? 0 : localizacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Incidence other = (Incidence) obj;
		if (campos == null) {
			if (other.campos != null)
				return false;
		} else if (!campos.equals(other.campos))
			return false;
		if (etiquetas == null) {
			if (other.etiquetas != null)
				return false;
		} else if (!etiquetas.equals(other.etiquetas))
			return false;
		if (localizacion == null) {
			if (other.localizacion != null)
				return false;
		} else if (!localizacion.equals(other.localizacion))
			return false;
		return true;
	}
}
