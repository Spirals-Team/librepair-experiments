package dev.controller.api.viewModels.examen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizzVm extends BaseVm{
	
	private String titre;
	@JsonProperty("nb_questions")
	private int nbQuestions;
	
	public QuizzVm(Long id, String titre, int nbQuestions){
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

	public void setNbQuestions(int nb_questions) {
		this.nbQuestions = nb_questions;
	}
	
	

}
