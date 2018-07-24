package dev.controller.api.viewModels.duel;

import dev.entites.Duel;

public class DuelVm extends BaseVm {
	private StagiaireVm stagiaireA;
	private StagiaireVm stagiaireB;
	private QuizzVm quizz;

	/**
	 * Constructeur
	 * 
	 * @param duel
	 */
	public DuelVm(Duel duel) {
		super(duel.getId());
		stagiaireA = new StagiaireVm(duel.getStagiaireA().getId(), duel.getStagiaireA().getNom(),
				duel.getStagiaireA().getPrenom());
		stagiaireB = new StagiaireVm(duel.getStagiaireB().getId(), duel.getStagiaireB().getNom(),
				duel.getStagiaireB().getPrenom());
		setQuizz(new QuizzVm(duel.getQuizz().getId(), duel.getQuizz().getTitre(),
				duel.getQuizz().getQuestions().size()));
	}

	/**
	 * @return the stagiaireA
	 */
	public StagiaireVm getStagiaireA() {
		return stagiaireA;
	}

	/**
	 * @param stagiaireA
	 *            the stagiaireA to set
	 */
	public void setStagiaireA(StagiaireVm stagiaireA) {
		this.stagiaireA = stagiaireA;
	}

	/**
	 * @return the stagiaireB
	 */
	public StagiaireVm getStagiaireB() {
		return stagiaireB;
	}

	/**
	 * @param stagiaireB
	 *            the stagiaireB to set
	 */
	public void setStagiaireB(StagiaireVm stagiaireB) {
		this.stagiaireB = stagiaireB;
	}

	public QuizzVm getQuizz() {
		return quizz;
	}

	public void setQuizz(QuizzVm quizz) {
		this.quizz = quizz;
	}

}
