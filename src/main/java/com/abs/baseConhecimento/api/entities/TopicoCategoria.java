package com.abs.baseConhecimento.api.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TopicoCategoria implements Serializable{
	private static final long serialVersionUID = 1L;

	private TopicoCategoriaPK id = new TopicoCategoriaPK();

	public TopicoCategoria() {
	}

	public TopicoCategoria(TopicoCategoriaPK id) {
		this.id = id;
	}

	@JsonIgnore
	@EmbeddedId
	public TopicoCategoriaPK getId() {
		return id;
	}

	public void setId(TopicoCategoriaPK id) {
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
		TopicoCategoria other = (TopicoCategoria) obj;
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
		builder.append("TopicoCategoria [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
