package dev.entites;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "stagiaire")
public class Stagiaire extends BaseEntite {

	@Size(min = 2)
	@Column(name = "prenom")
	private String prenom;

	@Size(min = 2)
	@Column(name = "nom")
	private String nom;

	@NotEmpty
	@Email
	@Column(name = "email")
	private String email;

	@NotEmpty
	@Column(name = "photo_url")
	private String photoUrl;

	@ManyToOne
	@JoinColumn(name = "id_classe")
	private Classe classe;

	public Stagiaire() {
	}

	public Stagiaire(String prenom, String nom) {
		super();
		this.prenom = prenom;
		this.nom = nom;
	}

	public Stagiaire(String prenom, String nom, String email, String photoUrl) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.photoUrl = photoUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
