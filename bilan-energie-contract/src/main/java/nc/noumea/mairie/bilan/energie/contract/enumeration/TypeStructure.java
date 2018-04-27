package nc.noumea.mairie.bilan.energie.contract.enumeration;

/**
 * Enumération de type de structure
 *
 * @author Greg Dujardin
 *
 */
public enum TypeStructure {
	
	/**
	 * Structure Eclairage Public
	 */
	EP("Eclairage Public"),
	/**
	 * Structure Bâtiment
	 */
	BATIMENT("Bâtiment");
	
	private String libelle;
	
	private TypeStructure(String libelle){
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
