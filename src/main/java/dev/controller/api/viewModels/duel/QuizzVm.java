package dev.controller.api.viewModels.duel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizzVm extends BaseVm {

	private String titre;

	@JsonProperty("nb_questions")
	private Integer nbQuestions;

	public QuizzVm(Long id, String titre, Integer nbQuestions) {
		super(id);
		this.titre = titre;
		this.nbQuestions = nbQuestions;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getNbQuestions() {
		return nbQuestions;
	}

	public void setNbQuestions(Integer nbQuestions) {
		this.nbQuestions = nbQuestions;
	}

}
