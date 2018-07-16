package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

/**
 * Question.java
 * 
 * @author matth
 */
public class Question extends BaseEntite {

	/**
	 * titre : String
	 */
	@NotEmpty
	private String titre;

	/**
	 * options : List<OptionQuestion>
	 */
	@NotEmpty
	private List<OptionQuestion> options;

	/**
	 * Constructor of Question.java
	 */
	public Question() {
		super();
	}

	/**
	 * Constructor of Question.java
	 * 
	 * @param id
	 * @param titre
	 */
	public Question(String id, String titre) {
		super();
		this.setId(Long.parseLong(id));
		this.titre = titre;
		this.options = new ArrayList<OptionQuestion>();
	}

	/**
	 * Constructor of Question.java
	 * 
	 * @param titre
	 */
	public Question(String titre) {
		super();
		this.titre = titre;
	}

	/**
	 * Constructor of Question.java
	 * 
	 * @param titre
	 * @param options
	 */
	public Question(String titre, List<OptionQuestion> options) {
		super();
		this.titre = titre;
		this.options = options;
	}

	/**
	 * Fonction :
	 * 
	 * @return
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * Fonction :
	 * 
	 * @param titre
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * Fonction :
	 * 
	 * @return
	 */
	public List<OptionQuestion> getOptions() {
		return options;
	}

	/**
	 * Fonction :
	 * 
	 * @param options
	 */
	public void setOptions(List<OptionQuestion> options) {
		this.options = options;
	}
}
