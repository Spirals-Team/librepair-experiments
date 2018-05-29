package dev.paie.entite;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class ProfilRemuneration {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String code;

	@ManyToMany
	@JoinTable(name="nonImpossable",joinColumns =@JoinColumn(name="id_profil" ,referencedColumnName="id"),inverseJoinColumns = @JoinColumn(name="id_coti_non", referencedColumnName="id"))
	private List<Cotisation> cotisationsNonImposables;
	
	@ManyToMany
	@JoinTable(name="impossable",joinColumns =@JoinColumn(name="id_profil2" ,referencedColumnName="id"),inverseJoinColumns = @JoinColumn(name="id_impo", referencedColumnName="id"))
	private List<Cotisation> cotisationsImposables;
	
	@Transient
	private List<Avantage> avantages;

	@OneToMany(mappedBy="profilRemuneration")
	private List<RemunerationEmploye> remuneration;
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getCode();
	}
	
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
