package com.rexam.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class StudentYear implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 818503377607555498L;

	@EmbeddedId
	private IdStudentYear id;

	@ManyToOne
	private Student student;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Registration> registration;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Result> result;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Registration> getRegistration() {
		return registration;
	}

	public void setRegistration(List<Registration> registration) {
		this.registration = registration;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

	public IdStudentYear getId() {
		return id;
	}

	public void setId(IdStudentYear id) {
		this.id = id;
	}

}
