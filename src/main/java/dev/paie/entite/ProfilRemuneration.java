package dev.paie.entite;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class ProfilRemuneration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_profil", unique = true, nullable = false)
	private Integer id;
	
	@Column(name="CODE", nullable = false)
	private String code;
	
	@ManyToMany
	@JoinTable(name="cotisation_non_imposable",
				joinColumns = @JoinColumn(name="id_cotisation"),
				inverseJoinColumns = @JoinColumn(name="id_profil")
	)
	private List<Cotisation> cotisationsNonImposables;
	
	@ManyToMany
	@JoinTable(name="cotisation_imposable",
				joinColumns = @JoinColumn(name="id_cotisation"),
				inverseJoinColumns = @JoinColumn(name="id_profil")
	)
	private List<Cotisation> cotisationsImposables;
	
	@ManyToMany
	@JoinTable(name="avantage_profil",
				joinColumns = @JoinColumn(name="id_avantage"),
				inverseJoinColumns = @JoinColumn(name="id_profil")
	)
	private List<Avantage> avantages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Cotisation> getCotisationsNonImposables() {
		return cotisationsNonImposables;
	}

	public void setCotisationsNonImposables(List<Cotisation> cotisationsNonImposables) {
		this.cotisationsNonImposables = cotisationsNonImposables;
	}

	public List<Cotisation> getCotisationsImposables() {
		return cotisationsImposables;
	}

	public void setCotisationsImposables(List<Cotisation> cotisationsImposables) {
		this.cotisationsImposables = cotisationsImposables;
	}

	public List<Avantage> getAvantages() {
		return avantages;
	}

	public void setAvantages(List<Avantage> avantages) {
		this.avantages = avantages;
	}

}
