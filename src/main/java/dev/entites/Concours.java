package dev.entites;

import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "concours")
public class Concours extends BaseEntite {

	@Column(name = "titre")
	private String titre;

	@ManyToMany
	@JoinTable(name = "concours_stagiaire", joinColumns = @JoinColumn(name = "id_concours", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_stagiaire", referencedColumnName = "id"))
	private List<Stagiaire> participants;

	@ManyToMany
	@JoinTable(name = "concours_quizz", joinColumns = @JoinColumn(name = "id_concours", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_quizz", referencedColumnName = "id"))
	private List<Quizz> quizzes;

	public Concours(String titre, List<Stagiaire> participants, List<Quizz> quizzes) {
		super();
		if (this.getId() == null) {
			this.setId(new Random().nextLong());
		}
		this.titre = titre;
		this.participants = participants;
		this.quizzes = quizzes;
	}

	public Concours() {
		super();
		if (this.getId() == null) {
			this.setId(new Random().nextLong());
		}
	}

	public Concours(String titre) {
		super();
		this.titre = titre;
	}

	public Concours(Long idConcours) {
		super.setId(idConcours);
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
