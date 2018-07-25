package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "quizz")
public class Quizz extends BaseEntite {

	@Size(min = 5)
	@Column(name = "titre")
	private String titre;

	@NotEmpty
	@ManyToMany
	@JoinTable(name = "compo_quizz", joinColumns = @JoinColumn(name = "id_quizz", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_question", referencedColumnName = "id"))
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
