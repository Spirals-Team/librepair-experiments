package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Quizz extends BaseEntite {

	@Size(min = 5)
	private String titre;

	@NotEmpty
	private List<Question> questions = new ArrayList<>();

	public Quizz() {
		super();
	}

	public Quizz(String titre) {
		this();
		this.titre = titre;
	}

	public Quizz(String titre, List<Question> questions) {
		this(titre);
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
