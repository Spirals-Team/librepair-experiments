package dev.controller.api.viewModels.duel;

public class StagiaireVm extends BaseVm {
	private String nom;
	private String prenom;

	/**
	 * Constructeur
	 * 
	 * @param nom
	 * @param prenom
	 */
	public StagiaireVm(Long id, String nom, String prenom) {
		super(id);
		this.nom = nom;
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

}
