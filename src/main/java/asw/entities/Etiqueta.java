package asw.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TEtiqueta")
public class Etiqueta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String valor;
	@ManyToOne
	private Incidence incidencia;
	
	public Etiqueta() {
	}
	
	public Etiqueta(Incidence incidencia, String valor) {
		super();
		this.incidencia = incidencia;
		this.valor = valor;
	}
	
	public Etiqueta(String valor) {
		this.valor = valor;
	}
	
	
	public Long getId() {
		return id;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Incidence getIncidencia() {
		return incidencia;
	}
	public void setIncidencia(Incidence incidencia) {
		this.incidencia = incidencia;
	}
	
	
}