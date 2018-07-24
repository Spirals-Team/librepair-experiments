package dev.controller.api.concours;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.controller.api.viewModels.examen.BaseVm;
import dev.controller.api.viewModels.examen.QuizzVm;
import dev.entites.Concours;
import dev.entites.Quizz;

public class ConcoursVm extends BaseVm {

	private String titre;
	@JsonProperty("nb_participants")
	private int nbParticipants;
	@JsonProperty("nb_quizzes")
	private int nbQuizzes;

	public ConcoursVm(Concours c) {
		super(c.getId());
		this.titre = c.getTitre();
		this.nbParticipants = c.getParticipants().size();
		this.nbQuizzes = c.getQuizzes().size();
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public List<QuizzVm> transform(List<Quizz> quizzes) {
		List<QuizzVm> QVM = new ArrayList<>();

		for (Quizz s : quizzes) {
			QVM.add(new QuizzVm(s.getId(), s.getTitre(), s.getQuestions().size()));
		}
		;
		return QVM;
	}

	public int getNbParticipants() {
		return nbParticipants;
	}

	public void setNbParticipants(int nbParticipants) {
		this.nbParticipants = nbParticipants;
	}

	public int getNbQuizzes() {
		return nbQuizzes;
	}

	public void setNbQuizzes(int nbQuizzes) {
		this.nbQuizzes = nbQuizzes;
	}
	
	

}
