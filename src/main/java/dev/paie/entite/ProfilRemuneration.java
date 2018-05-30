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
	private Integer id;
	@Column(name = "code", length = 255, nullable = false)
	private String code;
	@ManyToMany
	@JoinTable(name = "ProRem_CotiNonImpo", joinColumns = @JoinColumn(name = "Id_ProRem", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "Id_Coti_NON_Impo", referencedColumnName = "ID"))
	private List<Cotisation> cotisationsNonImposables;
	@ManyToMany
	@JoinTable(name = "ProRem_CotiImpo", joinColumns = @JoinColumn(name = "ID_ProRemu", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "Id_CotiImpo", referencedColumnName = "ID"))
	private List<Cotisation> cotisationsImposables;
	@ManyToMany
	@JoinTable(name = "Avan_ProRum", joinColumns = @JoinColumn(name = "ID_AVANTAGE", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_PROREMU", referencedColumnName = "ID"))
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
