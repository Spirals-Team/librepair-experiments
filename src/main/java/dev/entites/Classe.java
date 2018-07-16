package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "classe")
public class Classe extends BaseEntite {

	@NotEmpty
	@Column(name = "nom")
	private String nom;

	@NotEmpty
	@OneToMany(mappedBy = "classe")
	private List<Stagiaire> stagiaires = new ArrayList<>();

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Stagiaire> getStagiaires() {
		return stagiaires;
	}

	public void setStagiaires(List<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
	}
}
