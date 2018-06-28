package inciManager.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Incidencia {
	@Id
	@GeneratedValue
	@JsonView(Incidencia.class)
	private long id;

	@JsonView(Incidencia.class)
	private String identificadorAgente;
	@JsonView(Incidencia.class)
	private String name;
	@JsonView(Incidencia.class)
	private String descripcion;
	@JsonView(Incidencia.class)
	private String urlMasInfo;

	@ElementCollection
	@JsonView(Incidencia.class)
	private Set<String> etiquetas = new HashSet<String>();
	@ElementCollection
	@JsonView(Incidencia.class)
	private Map<String, String> campos = new HashMap<String, String>();

	@Enumerated(EnumType.STRING)
	@JsonView(Incidencia.class)
	private Estado estado;
	@JsonView(Incidencia.class)
	private Localizacion localizacion = new Localizacion();
	@ManyToOne
	@JoinColumn(name = "operator_id")
	@JsonView(Incidencia.class)
	private Operator operadorAsignado;
	@Transient
	@JsonView(Agente.class)
	private Agente agenteAux = new Agente();

	public Incidencia() {
	}

	public Incidencia(String identificadorAgente, String name, String descripcion, Set<String> etiquetas,
			Map<String, String> campos, Estado estado, Localizacion localizacion) {
		super();
		this.identificadorAgente = identificadorAgente;
		this.name = name;
		this.descripcion = descripcion;
		this.etiquetas = etiquetas;
		this.campos = campos;
		this.estado = estado;
		this.localizacion = localizacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdentificadorAgente() {
		return identificadorAgente;
	}

	public void setIdentificadorAgente(String identificadorAgente) {
		this.identificadorAgente = identificadorAgente;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(Set<String> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public Map<String, String> getCampos() {
		return campos;
	}

	public void setCampos(Map<String, String> campos) {
		this.campos = campos;
	}

	public String getUrlMasInfo() {
		return urlMasInfo;
	}

	public void setUrlMasInfo(String urlMasInfo) {
		this.urlMasInfo = urlMasInfo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Operator getOperadorAsignado() {
		return operadorAsignado;
	}

	public void setOperadorAsignado(Operator operadorAsignado) {
		this.operadorAsignado = operadorAsignado;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Agente getAgenteAux() {
		return agenteAux;
	}

	public void setAgenteAux(Agente agenteAux) {
		this.agenteAux = agenteAux;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((identificadorAgente == null) ? 0 : identificadorAgente.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Incidencia other = (Incidencia) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (identificadorAgente == null) {
			if (other.identificadorAgente != null)
				return false;
		} else if (!identificadorAgente.equals(other.identificadorAgente))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setCamposLista(String str) {
		String[] lista = str.split(",");
		for (int i = 0; i < lista.length; i++) {
			String[] campo = lista[i].split("/");
			if (campo.length == 2)
				campos.put(campo[0], campo[1]);
		}
	}

}
