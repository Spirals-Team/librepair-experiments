package dev.entites;

import java.util.List;

public class Question extends BaseEntite {

	private String titre;

	private List<OptionQuestion> options;

	public Question() {
		super();
	}

	public Question(String titre) {
		super();
		this.titre = titre;
	}

	public Question(String titre, List<OptionQuestion> options) {
		super();
		this.titre = titre;
		this.options = options;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public List<OptionQuestion> getOptions() {
		return options;
	}

	public void setOptions(List<OptionQuestion> options) {
		this.options = options;
	}
}
