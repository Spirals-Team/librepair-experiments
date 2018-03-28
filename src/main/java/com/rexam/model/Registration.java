package com.rexam.model;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class Registration {

	@EmbeddedId
	private IdRegistration id;
	@ManyToOne(cascade = { CascadeType.ALL })
	@MapsId("codeTeachingUnit")
	private TeachingUnit teachingUnit;

	@ManyToOne(cascade = { CascadeType.ALL })
	@MapsId("idStudentYear")
	private StudentYear studentYear;

	@Max(value = 20, message = "La moyenne doit être inférieure ou égale à 20")
    @Min(value = 0, message = "La moyenne doit être supérieure ou égale à 0")
	private Double averageScore;
	
	private String status;

	public IdRegistration getId() {
		return id;
	}

	public void setId(IdRegistration id) {
		this.id = id;
	}

	public TeachingUnit getTeachingUnit() {
		return teachingUnit;
	}

	public void setTeachingUnit(TeachingUnit teachingUnit) {
		this.teachingUnit = teachingUnit;
	}

	public StudentYear getStudentYear() {
		return studentYear;
	}

	public void setStudentYear(StudentYear studentYear) {
		this.studentYear = studentYear;
	}

	public Double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(Double averageScore) {
		this.averageScore = averageScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
