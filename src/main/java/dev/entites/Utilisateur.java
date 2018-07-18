package dev.entites;

public class Utilisateur extends BaseEntite {

	private String identifiant;
	private String motDePasse;
	private ProfilUtilisateur profil;

	public Utilisateur() {
	}

	public Utilisateur(String identifiant, String motDePasse, ProfilUtilisateur profil) {
		super();
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.profil = profil;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public ProfilUtilisateur getProfil() {
		return profil;
	}

	public void setProfil(ProfilUtilisateur profil) {
		this.profil = profil;
	}
}
