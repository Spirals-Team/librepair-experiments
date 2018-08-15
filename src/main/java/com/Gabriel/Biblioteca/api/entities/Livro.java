package com.Gabriel.Biblioteca.api.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "livro")
public class Livro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8547138235051226607L;

	private int id;
	private String codigo;
	private int tipo = 0;
	private String nome = "";
	private String data;
	private int edicao;
	private int volume;
	private Editora editora;
	private ArrayList<Autor> autores;
	private int quantidade;
	private int quantidadeEmprestimo;

	public Livro() {
		// TODO Auto-generated constructor stub
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

	@Column
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column
	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}

	@Column
	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	@Column
	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	@Column
	public ArrayList<Autor> getAutores() {
		return autores;
	}

	public void setAutores(ArrayList<Autor> autores) {
		this.autores = autores;
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
		return "Livro [id=" + id + ", codigo=" + codigo + ", tipo=" + tipo + ", nome=" + nome + ", data=" + data
				+ ", edicao=" + edicao + ", volume=" + volume + ", editora=" + editora + ", autores=" + autores
				+ ", quantidade=" + quantidade + ", quantidadeEmprestimo=" + quantidadeEmprestimo + "]";
	}

}
