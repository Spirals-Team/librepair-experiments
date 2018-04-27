package nc.noumea.mairie.bilan.energie.web;

/**
 * Enumération RecordMode
 *
 * @author Greg Dujardin
 *
 */
public enum RecordMode {
	
	/**
	 * Mode Nouveau
	 */
	NEW("NEW"),
	/**
	 * Mode Edition
	 */
	EDIT("EDIT"),
	/**
	 * Mode Consultation
	 */
	CONSULT("CONSULT");
	
	
	private String code;
	
	private RecordMode(String code){
		this.code = code;
	}
	
	/**
	 * get StringCode
	 *
	 * @return stringCode
	 */
	public String getStringCode(){
		return code;
	}
}
