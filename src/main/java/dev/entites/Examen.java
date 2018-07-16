package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class Examen extends BaseEntite {

	@NotEmpty
	private String titre;

	private Quizz quizz;

	private Classe classe;

	private List<Note> notes = new ArrayList<>();

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Quizz getQuizz() {
		return quizz;
	}

	public void setQuizz(Quizz quizz) {
		this.quizz = quizz;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public double getAvg() {
		return notes.stream().mapToDouble(Note::getNoteSur20).average().orElse(Double.NaN);
	}
}
