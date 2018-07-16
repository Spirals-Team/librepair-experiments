package dev.entites;

import java.util.List;

public class Concours extends BaseEntite {

	private String titre;

	private List<Stagiaire> participants;

	private List<Quizz> quizzes;

	public Concours(String titre, List<Stagiaire> participants, List<Quizz> quizzes) {
		super();
		this.titre = titre;
		this.participants = participants;
		this.quizzes = quizzes;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public List<Stagiaire> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Stagiaire> participants) {
		this.participants = participants;
	}

	public List<Quizz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quizz> quizzes) {
		this.quizzes = quizzes;
	}

}
