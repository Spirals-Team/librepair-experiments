package com.Gabriel.Biblioteca.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "periodico")
public class Periodico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -762655550539690816L;

	private String codigo;
	private int tipo = 0;
	private String nome = "";
	private int id;
	private int volume;
	private int quantidade;
	private int quantidadeEmprestimo;

	public Periodico() {
		// TODO Auto-generated constructor stub
	}

	@Column
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Column
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column
	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	@Column
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Column
	public int getQuantidadeEmprestimo() {
		return quantidadeEmprestimo;
	}

	public void setQuantidadeEmprestimo(int quantidadeEmprestimo) {
		this.quantidadeEmprestimo = quantidadeEmprestimo;
	}

	@Override
	public String toString() {
		return "Periodico [codigo=" + codigo + ", tipo=" + tipo + ", nome=" + nome + ", id=" + id + ", volume=" + volume
				+ ", quantidade=" + quantidade + ", quantidadeEmprestimo=" + quantidadeEmprestimo + "]";
	}

}