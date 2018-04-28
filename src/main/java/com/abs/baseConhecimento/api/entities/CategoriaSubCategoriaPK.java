package com.abs.baseConhecimento.api.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class CategoriaSubCategoriaPK implements Serializable{
	private static final long serialVersionUID = 1L;

	private Categoria categoria;
	private Categoria categoriaSub;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="categoria_id")
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@ManyToOne
	@JoinColumn(name="categoriaSub_id")
	public Categoria getCategoriaSub() {
		return categoriaSub;
	}

	public void setCategoriaSub(Categoria categoriaSub) {
		this.categoriaSub = categoriaSub;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((categoriaSub == null) ? 0 : categoriaSub.hashCode());
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
		CategoriaSubCategoriaPK other = (CategoriaSubCategoriaPK) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (categoriaSub == null) {
			if (other.categoriaSub != null)
				return false;
		} else if (!categoriaSub.equals(other.categoriaSub))
			return false;
		return true;
	}

}
