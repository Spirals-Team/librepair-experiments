package com.rexam.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class IdResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IdStudentYear idStudentYear;
	private String codeExam;


	public String getCodeExam() {
		return codeExam;
	}

	public void setCodeExam(String codeExam) {
		this.codeExam = codeExam;
	}

	public IdStudentYear getIdStudentYear() {
		return idStudentYear;
	}

	public void setIdStudentYear(IdStudentYear idStudentYear) {
		this.idStudentYear = idStudentYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeExam == null) ? 0 : codeExam.hashCode());
		result = prime * result + ((idStudentYear == null) ? 0 : idStudentYear.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdResult other = (IdResult) obj;
		if (codeExam == null) {
			if (other.codeExam != null)
				return false;
		} else if (!codeExam.equals(other.codeExam))
			return false;
		if (idStudentYear == null) {
			if (other.idStudentYear != null)
				return false;
		} else if (!idStudentYear.equals(other.idStudentYear))
			return false;
		return true;
	}

}
