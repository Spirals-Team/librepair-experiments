package dev.entites;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Note extends BaseEntite {

	private Stagiaire stagiaire;

	@Min(value = 0)
	@Max(value = 20)
	private Float noteSur20;

	public Note() {

	}

	public Note(Stagiaire stagiaire, Float noteSur20) {
		super();
		this.stagiaire = stagiaire;
		this.noteSur20 = noteSur20;
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
}
