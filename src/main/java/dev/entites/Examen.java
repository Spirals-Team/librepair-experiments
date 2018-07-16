package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "examen")
public class Examen extends BaseEntite {

	@NotEmpty
	@Column(name = "titre")
	private String titre;

	@ManyToOne
	@JoinColumn(name = "id_quizz")
	private Quizz quizz;

	@ManyToOne
	@JoinColumn(name = "id_classe")
	private Classe classe;

	@OneToMany(mappedBy = "examen")
	private List<Note> notes = new ArrayList<>();

	public Examen() {

	}

	public Examen(Long id) {
		super.setId(id);
	}

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

	public void addNote(Note note) {
		notes.add(note);

	}
}
