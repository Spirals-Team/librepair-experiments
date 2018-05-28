package dev.paie.entite;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name="CODE", nullable = false)
	private String code;
	
	@Column(name="NB_HEURES_BASE", nullable = false)
	private BigDecimal nbHeuresBase;
	
	@Column(name="TAUX_BASE", nullable = false)
	private BigDecimal tauxBase;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getNbHeuresBase() {
		return nbHeuresBase;
	}

	public void setNbHeuresBase(BigDecimal nbHeuresBase) {
		this.nbHeuresBase = nbHeuresBase;
	}

	public BigDecimal getTauxBase() {
		return tauxBase;
	}

	public void setTauxBase(BigDecimal tauxBase) {
		this.tauxBase = tauxBase;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean equals(Grade g) {
		//compareTo renvoie 0 si les deux BigDeicmal sont égaux
		return this.code.equals(g.getCode()) && this.nbHeuresBase.compareTo(g.getNbHeuresBase())==0 && this.tauxBase.compareTo(g.getTauxBase())==0;
	}

}
