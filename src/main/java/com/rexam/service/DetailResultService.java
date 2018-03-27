package com.rexam.service;

public class DetailResultService {

	private String typeExam;
	private Double score;
	private Integer weight;
	private String dateObt;
	
	/**
	 * @return the typeExam
	 */
	public String getTypeExam() {
		return typeExam;
	}
	/**
	 * @param typeExam the typeExam to set
	 */
	public void setTypeExam(String typeExam) {
		this.typeExam = typeExam;
	}
	/**
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Double score) {
		this.score = score;
	}
	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	/**
	 * @return the dateObt
	 */
	public String getDateObt() {
		return dateObt;
	}
	/**
	 * @param dateObt the dateObt to set
	 */
	public void setDateObt(String dateObt) {
		this.dateObt = dateObt;
	}
}
