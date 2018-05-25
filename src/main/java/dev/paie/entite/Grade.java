package dev.paie.entite;

import java.math.BigDecimal;

public class Grade {

	private Integer id;
	private String code;
	private BigDecimal nbHeuresBase;
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
