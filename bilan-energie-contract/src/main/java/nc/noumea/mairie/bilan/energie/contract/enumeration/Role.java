package nc.noumea.mairie.bilan.energie.contract.enumeration;

/**
 * Enumération des rôles
 *
 * @author Greg Dujardin
 *
 */
public enum Role {
	
	/**
	 * Rôle Administrateur de bâtiment
	 */
	ADMINISTRATEUR_BATIMENT("ADMINISTRATEUR_BATIMENT"),
	/**
	 * Rôle Contributeur de bâtiment
	 */
	CONTRIBUTEUR_BATIMENT("CONTRIBUTEUR_BATIMENT"),
	/**
	 * Rôle Visiteur de bâtiment
	 */
	VISITEUR_BATIMENT("VISITEUR_BATIMENT"),
	/**
	 * Rôle Administrateur d'éclairage public
	 */
	ADMINISTRATEUR_EP("ADMINISTRATEUR_EP"),
	/**
	 * Rôle Contributeur d'éclairage public
	 */
	CONTRIBUTEUR_EP("CONTRIBUTEUR_EP"),
	/**
	 * Rôle Visiteur d'éclairage public
	 */
	VISITEUR_EP("VISITEUR_EP");
	
	private String libelle;
	
	private Role(String libelle){
		this.libelle = libelle;
	}
	
	/**
	 * get Libelle
	 *
	 * @return libellé
	 */
	public String getLibelle(){
		return libelle;
	}
}
