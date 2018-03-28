package com.rexam.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Embeddable
public class IdStudentYear implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2538447516915936035L;
	
	@NotNull
    @Min(value = 1900, message = "immortel êtes vous?")
	private int year;
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + year;
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
		IdStudentYear other = (IdStudentYear) obj;
		if (id != other.id)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}
