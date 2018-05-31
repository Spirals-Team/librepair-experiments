package dev.paie.entite;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RemunerationEmploye {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "matricule")
	private String matricule;
	@ManyToOne
	@JoinColumn(name = "id_en")
	private Entreprise entreprise;
	@ManyToOne
	@JoinColumn(name = "id_pr")
	private ProfilRemuneration profilRemuneration;
	@ManyToOne
	@JoinColumn(name = "id_gr")
	private Grade grade;
	@Column(name = "dateDeCreation")
	private LocalDateTime dateDeCreation;

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

	public LocalDateTime getDateDeCreation() {
		return dateDeCreation;
	}

	public void setDateDeCreation(LocalDateTime dateDeCreation) {
		this.dateDeCreation = dateDeCreation;
	}

}
