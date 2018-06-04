package ar.com.utn.dds.sge.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * CLase que modela a un administrador teniendo en cuenta los atributos que
 * hereda de la clase Usuario.
 * 
 * @author Grupo 2
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id" })
public class Administrador extends Usuario {

	@JsonProperty("id")
	@NotNull(message = "El administrador debe poseer un id")
	private Integer id;

	public Administrador() {
		super();
	}

	public Administrador(String userName, String password, String nombre, String apellido, String domicilio,
			Calendar fechaAlta, Integer id) {
		super(userName, password, nombre, apellido, domicilio, fechaAlta);
		this.id = id;
	}

	/**
	 * 
	 * @return Antiguedad del administrador
	 */
	public int obtenerAntiguedad() {
		Calendar fechaHoy = new GregorianCalendar();

		int difA = fechaHoy.get(Calendar.YEAR) - super.getFechaAlta().get(Calendar.YEAR);
		int difM = difA * 12 + fechaHoy.get(Calendar.MONTH) - super.getFechaAlta().get(Calendar.MONTH);

		return difM;
	}

	/*
	 * GETTERS Y SETTERS
	 */

	/**
	 * @return the id
	 */
	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */

	public void setId(Integer id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Administrador other = (Administrador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}