package dev.entites;

import java.util.ArrayList;
import java.util.List;

public class Quizz extends BaseEntite {

	private String titre;

	private List<Question> questions = new ArrayList<>();

	public Quizz() {
	}

	public Quizz(String titre) {
		super();
		this.titre = titre;
	}

	public Quizz(String titre, List<Question> questions) {
		this.titre = titre;
		this.questions = questions;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
