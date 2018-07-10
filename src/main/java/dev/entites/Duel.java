package dev.entites;

public class Duel extends BaseEntite {

	private Stagiaire stagiaireA;
	private Stagiaire stagiaireB;
	private Quizz quizz;

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
