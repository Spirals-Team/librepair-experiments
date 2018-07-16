package dev.entites;

import javax.validation.constraints.NotEmpty;

/**
 * OptionQuestion.java
 * 
 * @author matth
 */
public class OptionQuestion extends BaseEntite {

	/**
	 * libelle : String
	 */
	@NotEmpty
	private String libelle;
	/**
	 * ok : Boolean
	 */
	@NotEmpty
	private Boolean ok;

	/**
	 * Constructor of OptionQuestion.java
	 */
	public OptionQuestion() {
		super();
	}

	/**
	 * Constructor of OptionQuestion.java
	 * 
	 * @param id
	 * @param libelle
	 * @param ok
	 */
	public OptionQuestion(String id, String libelle, String ok) {
		super();
		this.setId(Long.parseLong(id));
		this.libelle = libelle;
		this.ok = Boolean.valueOf(ok);
	}

	/**
	 * Fonction :
	 * 
	 * @return
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Fonction :
	 * 
	 * @param libelle
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the ok
	 */
	public Boolean getOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            the ok to set
	 */
	public void setOk(Boolean ok) {
		this.ok = ok;
	}

}
