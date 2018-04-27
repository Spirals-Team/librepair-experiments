package nc.noumea.mairie.bilan.energie.contract.enumeration;

/**
 * Enumération des types d'anomalie
 *
 * @author Greg Dujardin
 *
 */
public enum TypeAnomalie {
	
	/**
	 * Anomalie Aucune correspondance
	 */
	ERREUR_PAS_CORRESPONDANCE("Erreur : Aucune rapprochement possible"),
	/**
	 * Anomalie Pas de rapprochement sur le numéro de police
	 */
	ERREUR_NUM_POLICE("Erreur : Rapprochement impossible sur le numéro de police"),
	/**
	 * Anomalie Pas de rapprochement sur le numéro de compteur
	 */
	ERREUR_NUM_COMPTEUR("Erreur : Rapprochement impossible sur le numéro de compteur"),
	/**
	 * Anomalie Doublon sur le couple numéros police/compteur
	 */
	ERREUR_COUPLE_DOUBLON("Erreur : Couple police / compteur en doublon");
	
	private String libelle;
	
	private TypeAnomalie(String libelle){
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
