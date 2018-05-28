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
	@Column(name="id_cotisation", unique = true, nullable = false)
	private Integer id;
	
	@Column(name="CODE", nullable = false)
	private String code;
	
	@Column(name="LIBELLE", nullable = false)
	private String libelle;
	
	@Column(name="TAUX_SALARIAL", nullable = true)
	private BigDecimal tauxSalarial;
	
	@Column(name="TAUX_PATRONAL", nullable = true)
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
	
	public boolean equals(Cotisation cotisation)
	{
		return this.code.equals(cotisation.getCode())
				&& this.libelle.equals(cotisation.getLibelle())
				&& this.tauxSalarial.compareTo(cotisation.getTauxSalarial()) == 0
				&& this.tauxPatronal.compareTo(cotisation.getTauxPatronal()) == 0;
	}
	
	

}
