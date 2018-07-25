package dev.controller.api.viewModels.examen;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.controller.api.viewModels.BaseVm;
import dev.entites.Quizz;

public class QuizzVm extends BaseVm {

	private String titre;
	@JsonProperty("nb_questions")
	private int nbQuestions;

	public QuizzVm(Long id, String titre, int nbQuestions) {
		super(id);
		this.titre = titre;
		this.nbQuestions = nbQuestions;
	}

	public QuizzVm(Quizz q) {
		super(q.getId());
		this.titre = q.getTitre();
		this.nbQuestions = q.getQuestions().size();
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
