package com.abs.baseConhecimento.api.entities;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Informacao implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private Clob descricao;
	private Date dataCriacao;
	private Date dataAtualizacao;
	
	public Informacao() {
		
	}

	public Informacao(long id, Clob descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(nullable=false)
	public Clob getDescricao() {
		return descricao;
	}

	public void setDescricao(Clob descricao) {
		this.descricao = descricao;
	}

	@Column(nullable=false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(nullable=false)
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	@PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Topico [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(descricao);
		builder.append(", dataCriacao=");
		builder.append(dataCriacao);
		builder.append(", dataAtualizacao=");
		builder.append(dataAtualizacao);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Informacao other = (Informacao) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
