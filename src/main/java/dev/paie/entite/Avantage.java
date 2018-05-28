package dev.paie.entite;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Avantage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_avantage", unique = true, nullable = false)
	private Integer id;

	@Column(name = "CODE", nullable = false)
	private String code;

	@Column(name = "NOM", nullable = false)
	private String nom;

	@Column(name = "MONTANT", nullable = false)
	private BigDecimal montant;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean equals(Avantage av) {
		return this.code.equals(av.getCode()) && this.nom.equals(av.getNom())
				&& this.montant.compareTo(av.getMontant()) == 0;
	}
}
