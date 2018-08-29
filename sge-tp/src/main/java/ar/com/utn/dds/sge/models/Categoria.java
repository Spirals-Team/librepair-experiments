package ar.com.utn.dds.sge.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Clase que modela la entidad categoría
 * 
 * @author Grupo 2
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nombre", "consumo_minimo", "consumo_maximo", "cargo_fijo", "cargo_variable" })
public class Categoria {

	/**
	 * 
	 */
	@JsonProperty("nombre")
	@NotNull(message = "El nombre de la categoria no puede estar vacio")
	private String nombre;
	
	@JsonProperty("consumo_minimo")
	@NotNull(message = "El consumo minimo no puede estar vacio")
	private Float consumoMinimo;
	
	@JsonProperty("consumo_maximo")
	@NotNull(message = "El consumo maximo no puede estar vacio")
	private Float consumoMaximo;	

	@JsonProperty("cargo_fijo")
	@NotNull(message = "El cargo fijo no puede estar vacio")
	private Float cargoFijo;

	@JsonProperty("cargo_variable")
	@NotNull(message = "El cargo variable no puede estar vacio")
	private Float cargoVariable;

	public Categoria() {

	}

	public Categoria(String nombre, Float consumoMinimo, Float consumoMaximo, Float cargoFijo, Float cargoVariable) {
		this.nombre = nombre;
		this.consumoMinimo = consumoMinimo;
		this.consumoMaximo = consumoMaximo;
		this.cargoFijo = cargoFijo;
		this.cargoVariable = cargoVariable;
	}

	/*
	 * GETTERS Y SETTERS
	 */
	
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
	
	public Float getConsumoMinimo(){
		return consumoMinimo;
	}

	public void setConsumoMinimo(Float consumoMinimo){
		this.consumoMinimo = consumoMinimo;
	}
	
	public Float getConsumoMaximo(){
		return consumoMaximo;
	}

	public void setConsumoMaximo(Float consumoMaximo){
		this.consumoMaximo = consumoMaximo;
	}	
	
	/**
	 * @return the cargoFijo
	 */
	public Float getCargoFijo() {
		return cargoFijo;
	}

	public Float calcularImporte(Float consumo){
		return cargoFijo + cargoVariable * consumo;
	}
	
	/**
	 * @param cargoFijo
	 *            the cargoFijo to set
	 */
	public void setCargoFijo(Float cargoFijo) {
		this.cargoFijo = cargoFijo;
	}

	/**
	 * @return the cargoVariable
	 */
	public Float getCargoVariable() {
		return cargoVariable;
	}

	/**
	 * @param cargoVariable
	 *            the cargoVariable to set
	 */
	public void setCargoVariable(Float cargoVariable) {
		this.cargoVariable = cargoVariable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargoFijo == null) ? 0 : cargoFijo.hashCode());
		result = prime * result + ((cargoVariable == null) ? 0 : cargoVariable.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (cargoFijo == null) {
			if (other.cargoFijo != null)
				return false;
		} else if (!cargoFijo.equals(other.cargoFijo))
			return false;
		if (cargoVariable == null) {
			if (other.cargoVariable != null)
				return false;
		} else if (!cargoVariable.equals(other.cargoVariable))
			return false;
		if (consumoMinimo == null) {
			if (other.consumoMinimo != null)
				return false;
		} else if (!consumoMinimo.equals(other.consumoMinimo))
			return false;
		if (consumoMaximo == null) {
			if (other.consumoMaximo != null)
				return false;
		} else if (!consumoMaximo.equals(other.consumoMaximo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
