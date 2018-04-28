package com.abs.baseConhecimento.api.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class TopicoCategoriaPK implements Serializable{
	private static final long serialVersionUID = 1L;

	private Categoria categoria;
	private Topico topico;
	
	@ManyToOne
	@JoinColumn(name="topico_id")
	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="categoria_id")
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((topico == null) ? 0 : topico.hashCode());
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
		TopicoCategoriaPK other = (TopicoCategoriaPK) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (topico == null) {
			if (other.topico != null)
				return false;
		} else if (!topico.equals(other.topico))
			return false;
		return true;
	}

}
