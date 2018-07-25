package dev.controller.api.viewModels.duel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DuelCreateVm {

	@JsonProperty("stagiaireA_id")
	private Long stagiaireAId;

	@JsonProperty("stagiaireB_id")
	private Long stagiaireBId;

	@JsonProperty("quizz_id")
	private Long quizzId;

	/**
	 * @return the stagiaireAId
	 */
	public Long getStagiaireAId() {
		return stagiaireAId;
	}

	/**
	 * @param stagiaireAId
	 *            the stagiaireAId to set
	 */
	public void setStagiaireAId(Long stagiaireAId) {
		this.stagiaireAId = stagiaireAId;
	}

	/**
	 * @return the stagiaireBId
	 */
	public Long getStagiaireBId() {
		return stagiaireBId;
	}

	/**
	 * @param stagiaireBId
	 *            the stagiaireBId to set
	 */
	public void setStagiaireBId(Long stagiaireBId) {
		this.stagiaireBId = stagiaireBId;
	}

	/**
	 * @return the quizzId
	 */
	public Long getQuizzId() {
		return quizzId;
	}

	/**
	 * @param quizzId
	 *            the quizzId to set
	 */
	public void setQuizzId(Long quizzId) {
		this.quizzId = quizzId;
	}

}
