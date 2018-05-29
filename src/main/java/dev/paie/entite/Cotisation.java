package dev.paie.entite;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cotisation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "code", length = 30, nullable = false)
	private String code;
	@Column(name = "libelle", length = 50, nullable = false)
	private String libelle;
	@Column(name = "tauxSalarial", nullable = true)
	private BigDecimal tauxSalarial;
	@Column(name = "tauxPatronal", nullable = true)
	private BigDecimal tauxPatronal;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public BigDecimal getTauxSalarial() {
		return tauxSalarial;
	}

	public void setTauxSalarial(BigDecimal tauxSalarial) {
		this.tauxSalarial = tauxSalarial;
	}

	public BigDecimal getTauxPatronal() {
		return tauxPatronal;
	}

	public void setTauxPatronal(BigDecimal tauxPatronal) {
		this.tauxPatronal = tauxPatronal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cotisation() {
	}

}
