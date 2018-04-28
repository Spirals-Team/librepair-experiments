package com.abs.baseConhecimento.api.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CategoriaSubCategoria implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private CategoriaSubCategoriaPK id = new CategoriaSubCategoriaPK();

	public CategoriaSubCategoria() {
	}

	public CategoriaSubCategoria(CategoriaSubCategoriaPK id) {
		this.id = id;
	}

	@JsonIgnore
	@EmbeddedId
	public CategoriaSubCategoriaPK getId() {
		return id;
	}

	public void setId(CategoriaSubCategoriaPK id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CategoriaSubCategoria other = (CategoriaSubCategoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategoriaSubCategoria [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
