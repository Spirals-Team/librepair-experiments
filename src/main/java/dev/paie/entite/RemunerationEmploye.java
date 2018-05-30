package dev.paie.entite;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "remunerationEmploye")
public class RemunerationEmploye {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "matricule")
	private String matricule;
	@ManyToOne
	private Entreprise entreprise;
	@ManyToOne
	private ProfilRemuneration profilRemuneration;
	@ManyToOne
	private Grade grade;
	@Column(name = "heureCreation")
	private ZonedDateTime heureCreation;

	@PrePersist
	private void onPrePersist() {

		this.heureCreation = ZonedDateTime.now();
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public ProfilRemuneration getProfilRemuneration() {
		return profilRemuneration;
	}

	public void setProfilRemuneration(ProfilRemuneration profilRemuneration) {
		this.profilRemuneration = profilRemuneration;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZonedDateTime getHeureCreation() {
		return heureCreation;
	}

	public void setHeureCreation(ZonedDateTime heureCreation) {
		this.heureCreation = heureCreation;
	}

}
