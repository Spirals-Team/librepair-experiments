package com.Gabriel.Biblioteca.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "editora")
public class Editora implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4126204956722886610L;

	private int id;
	private String codigo;
	private String nome;
	private boolean nacional;

	public Editora() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column
	public boolean isNacional() {
		return nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

	@Override
	public String toString() {
		return "Editora [id=" + id + ", codigo=" + codigo + ", nome=" + nome + ", nacional=" + nacional + "]";
	}

}
