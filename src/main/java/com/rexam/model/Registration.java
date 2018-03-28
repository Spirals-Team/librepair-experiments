package com.rexam.model;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "computeAvg", 
                               procedureName = "computeAvg",
                               parameters = {
                                  @StoredProcedureParameter(mode = ParameterMode.IN, name = "tu_code", type = String.class),
                                  @StoredProcedureParameter(mode = ParameterMode.IN, name = "sy_id", type = Integer.class),
                                  @StoredProcedureParameter(mode = ParameterMode.IN, name = "sy_y", type = Integer.class)
                               })})
public class Registration {

	@EmbeddedId
	private IdRegistration id;
	@ManyToOne(cascade = { CascadeType.ALL })
	@MapsId("codeTeachingUnit")
	private TeachingUnit teachingUnit;

	@ManyToOne(cascade = { CascadeType.ALL })
	@MapsId("idStudentYear")
	private StudentYear studentYear;

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
