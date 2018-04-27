package nc.noumea.mairie.bilan.energie.contract.dto;

/**
 * Class CodeLabel pour la gestion des couples code/label
 * 
 * @author Greg Dujardin
 *
 */
public class CodeLabel {

	/** code du couple */
	private Long code;
	
	/** label du couple */
	private String label;

	/**
	 * @return the code
	 */
	public Long getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Long code) {
		this.code = code;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}