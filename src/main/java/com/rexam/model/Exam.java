package com.rexam.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Exam {
	
	@Id
	private String code;
	
	private String typeExam;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Result> result;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTypeExam() {
		return typeExam;
	}

	public void setTypeExam(String typeExam) {
		this.typeExam = typeExam;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}
	
	public Result getResultByStudentYear(StudentYear student) {
		for(Result res : result) {
			if (res.getStudentYear().equals(student)) {
				return res;
			}
		}
		return null;
	}
}
