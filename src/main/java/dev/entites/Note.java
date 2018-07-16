package dev.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "note")
public class Note extends BaseEntite {

	@ManyToOne
	@JoinColumn(name = "id_stagiaire")
	private Stagiaire stagiaire;

	@ManyToOne
	@JoinColumn(name = "id_examen")
	private Examen examen;

	@Min(value = 0)
	@Max(value = 20)
	@Column(name = "note_sur_20")
	private Float noteSur20;

	public Note() {

	}

	public Note(Stagiaire stagiaire, Float noteSur20, Examen exam) {
		super();
		this.stagiaire = stagiaire;
		this.noteSur20 = noteSur20;
		examen = exam;
	}

	public Stagiaire getStagiaire() {
		return stagiaire;
	}

	public void setStagiaire(Stagiaire stagiaire) {
		this.stagiaire = stagiaire;
	}

	public Float getNoteSur20() {
		return noteSur20;
	}

	public void setNoteSur20(Float noteSur20) {
		this.noteSur20 = noteSur20;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

}
