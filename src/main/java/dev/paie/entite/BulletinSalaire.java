package dev.paie.entite;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class BulletinSalaire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "remunerationEmploye", nullable = false)
	private RemunerationEmploye remunerationEmploye;
	@Column(name = "primeExceptionnelle", nullable = false)
	private BigDecimal primeExceptionnelle;

	private Periode periode;

	public RemunerationEmploye getRemunerationEmploye() {
		return remunerationEmploye;
	}

	public void setRemunerationEmploye(RemunerationEmploye remunerationEmploye) {
		this.remunerationEmploye = remunerationEmploye;
	}

	public Periode getPeriode() {
		return periode;
	}

	public void setPeriode(Periode periode) {
		this.periode = periode;
	}

	public BigDecimal getPrimeExceptionnelle() {
		return primeExceptionnelle;
	}

	public void setPrimeExceptionnelle(BigDecimal primeExceptionnelle) {
		this.primeExceptionnelle = primeExceptionnelle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BulletinSalaire() {
	}

}
