package nc.noumea.mairie.bilan.energie.contract.enumeration;

/**
 * Enumération Type de l'analyse
 * 
 * @author Greg Dujardin
 *
 */
public enum TypeAnalyse {
	
	/**
	 * Type Permanente
	 */
	PERMANENTE("Permanente"),
	/**
	 * Type Rapport
	 */
	RAPPORT("Associé à un rapport");
	
	private String libelle;
	
	private TypeAnalyse(String libelle){
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
