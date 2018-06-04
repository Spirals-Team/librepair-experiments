package ar.com.utn.dds.sge.models;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Clase que modela a un usuario 
 * @author Grupo 2
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "username", "password", "nombre", "apellido", "domicilio", "fecha_alta"})
public abstract class Usuario {
	
	@JsonProperty("username")
	@NotNull(message = "El usuario no puede estar vacio")
	private String usuario;
	
	@JsonProperty("password")
	@NotNull(message = "La contraseña no puede estar vacia")
	private String contrasena;
	
	@JsonProperty("nombre")
	@NotNull(message = "El nombre no puede estar vacio")
	private String nombre;
	
	@JsonProperty("apellido")
	@NotNull(message = "El apellido no puede estar vacio")
	private String apellido;
	
	@JsonProperty("domicilio")
	@NotNull(message = "El domicilio no puede estar vacio")
	private String domicilio;
	
	@JsonProperty("fecha_alta")
	@NotNull(message = "La fecha de alta no puede estar vacia")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Calendar fec_alta;

	
	public Usuario() {

	}
	
	public Usuario(String userName, String password, String nombre, String apellido, String domicilio,
			Calendar fechaAlta) {
		super();
		this.usuario = userName;
		this.contrasena = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.fec_alta = fechaAlta;
	}


	/*
	 * GETTERS Y SETTERS
	 */

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return usuario;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.usuario = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return contrasena;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.contrasena = password;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido
	 *            the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * @param domicilio
	 *            the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * @return the fechaAlta
	 */
	
	public Calendar getFechaAlta() {
		return fec_alta;
	}

	/**
	 * @param fechaAlta
	 *            the fechaAlta to set
	 */
	
	public void setFechaAlta(Calendar fechaAlta) {
		this.fec_alta = fechaAlta;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result + ((fec_alta == null) ? 0 : fec_alta.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((contrasena == null) ? 0 : contrasena.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (domicilio == null) {
			if (other.domicilio != null)
				return false;
		} else if (!domicilio.equals(other.domicilio))
			return false;
		if (fec_alta == null) {
			if (other.fec_alta != null)
				return false;
		} else if (!compararFechas(fec_alta,other.fec_alta))
				return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (contrasena == null) {
			if (other.contrasena != null)
				return false;
		} else if (!contrasena.equals(other.contrasena))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	 /**
	  * Metodo privado que compara dia mes y año entre 2 fechas
	   * @param fecha1 
	   * @param fecha2
	   * @return Valor de verdad de la igualdad de las fechas
	  **/
	  private boolean compararFechas(Calendar fecha1, Calendar fecha2) {
	    return fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
	         fecha1.get(Calendar.MONTH) == fecha2.get(Calendar.MONTH) &&
	         fecha1.get(Calendar.DAY_OF_MONTH) == fecha2.get(Calendar.DAY_OF_MONTH);
	  }	

	
}
