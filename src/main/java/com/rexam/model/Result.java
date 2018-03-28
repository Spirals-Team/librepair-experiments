package com.rexam.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Result {
	
	@EmbeddedId
	private IdResult id;
	
	private String dateObtened;
	
	@NotNull
    @Max(value = 20, message = "La note doit être inférieure ou égale à 20")
    @Min(value = 0, message = "La note doit être supérieure ou égale à 0")
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
