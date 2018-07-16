package dev.entites;

import javax.validation.constraints.NotNull;

public class Duel extends BaseEntite {

	private Stagiaire stagiaireA;
	private Stagiaire stagiaireB;

	@NotNull
	private Quizz quizz;

	/**
	 * Constructeur par défaut
	 */
	public Duel() {
		super();
	}

	/**
	 * @param stagiaireA
	 * @param stagiaireB
	 * @param quizz
	 */
	public Duel(Stagiaire stagiaireA, Stagiaire stagiaireB, @NotNull Quizz quizz) {
		super();
		this.stagiaireA = stagiaireA;
		this.stagiaireB = stagiaireB;
		this.quizz = quizz;
	}

	public Stagiaire getStagiaireA() {
		return stagiaireA;
	}

	public void setStagiaireA(Stagiaire stagiaireA) {
		this.stagiaireA = stagiaireA;
	}

	public Stagiaire getStagiaireB() {
		return stagiaireB;
	}

	public void setStagiaireB(Stagiaire stagiaireB) {
		this.stagiaireB = stagiaireB;
	}

	public Quizz getQuizz() {
		return quizz;
	}

	public void setQuizz(Quizz quizz) {
		this.quizz = quizz;
	}
}
