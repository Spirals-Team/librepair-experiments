package nc.noumea.mairie.bilan.energie.contract.enumeration;

/**
 * Enumération des états de fichier
 *
 * @author Greg Dujardin
 *
 */
public enum EtatFichier {
	
	/**
	 * Etat Importé
	 */
	IMPORTE("Importé"),
	/**
	 * Etat Intégré
	 */
	INTEGRE("Intégré"),
	/**
	 * Anomalie d'import
	 */
	ANOMALIE_IMPORT("Anomalie d'import"),
	/**
	 * Anomalie d'intégration
	 */
	ANOMALIE_INTEGRATION("Anomalie d'intégration"),
	/**
	 * Etat Anomalies traitées
	 */
	ANOMALIES_TRAITEES("Anomalies traitées");
	
	private String libelle;
	
	private EtatFichier(String libelle){
		this.libelle = libelle;
	}
	
	/**
	 * get Libelle
	 *
	 * @return libelle
	 */
	public String getLibelle(){
		return libelle;
	}
}
