package dev.controller.api.stagiaire;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.entites.Stagiaire;

public class StagiaireVm extends BaseEntiteVm {

	private Long id;
	private String prenom;
	private String nom;
	private String email;
	@JsonProperty("photo_url")
	private String photoUrl;

	public StagiaireVm() {
		super();
	}

	public StagiaireVm(String prenom, String nom, String email, String photoUrl) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.photoUrl = photoUrl;
	}

	public StagiaireVm(Stagiaire s) {
		super(s.getId());
		this.prenom = s.getPrenom();
		this.nom = s.getNom();
		this.email = s.getEmail();
		this.photoUrl = s.getPhotoUrl();
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl
	 *            the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
