package com.rexam.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class IdRegistration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IdStudentYear idStudentYear;
	private String codeTeachingUnit;

	public IdStudentYear getIdStudentYear() {
		return idStudentYear;
	}

	public void setIdStudentYear(IdStudentYear idStudentYear) {
		this.idStudentYear = idStudentYear;
	}

	public String getCodeTeachingUnit() {
		return codeTeachingUnit;
	}

	public void setCodeTeachingUnit(String codeTeachingUnit) {
		this.codeTeachingUnit = codeTeachingUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeTeachingUnit == null) ? 0 : codeTeachingUnit.hashCode());
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
		IdRegistration other = (IdRegistration) obj;
		if (codeTeachingUnit == null) {
			if (other.codeTeachingUnit != null)
				return false;
		} else if (!codeTeachingUnit.equals(other.codeTeachingUnit))
			return false;
		if (idStudentYear == null) {
			if (other.idStudentYear != null)
				return false;
		} else if (!idStudentYear.equals(other.idStudentYear))
			return false;
		return true;
	}

}
