package ar.com.utn.dds.sge.models;




import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



/**
 * Clase que modela la entidad dispositivo
 * @author Grupo 2
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "tipo", "nombre", "consumo", "estado"})
public class Dispositivo{
	
	
	
	@JsonProperty("tipo")
	private String tipo;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("consumo")
	private Float kw_h;
	
	@JsonProperty("estado")
	private Boolean estado;

	public Dispositivo() {

	}
	
	public Dispositivo(String tipo, String nombre, Float consumo, boolean estado) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.kw_h = consumo;
		this.estado= estado;
		
	}


	/*
	 * GETTERS Y SETTERS
	 */
	
	
	public Boolean getEstado() {
		return estado;
	}

	
	public void setEstado(Boolean estado) {
		this.estado = estado;
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
		return kw_h;
	}

	/**
	 * @param consumo the consumo to set
	 */
	public void setConsumo(Float consumo) {
		this.kw_h = consumo;
	}

	/**
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kw_h == null) ? 0 : kw_h.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
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
		if (kw_h == null) {
			if (other.kw_h != null)
				return false;
		} else if (!kw_h.equals(other.kw_h))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
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
