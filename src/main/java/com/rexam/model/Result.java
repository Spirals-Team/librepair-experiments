package com.rexam.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Result {
	
	@EmbeddedId
	private IdResult id;
	
	private String dateObtened;
	
	private Double score;
	
	@ManyToOne
	@MapsId("idStudentYear")
	private StudentYear studentYear;
	
	@ManyToOne
	@MapsId("codeExam")
	private Exam exam;

	public IdResult getId() {
		return id;
	}

	public void setId(IdResult id) {
		this.id = id;
	}

	public String getDateObtened() {
		return dateObtened;
	}

	public void setDateObtened(String dateObtened) {
		this.dateObtened = dateObtened;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public StudentYear getStudentYear() {
		return studentYear;
	}

	public void setStudentYear(StudentYear studentYear) {
		this.studentYear = studentYear;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
	

}
