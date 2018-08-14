package com.Gabriel.Biblioteca.api.dtos;

public class PeriodicoDTO {

	private String codigo;
	private int tipo = 0;
	private String nome = "";

//	----------------------------

	private int id;
	private int volume;
	private int quantidade;
	private int quantidadeEmprestimo;

	public PeriodicoDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getQuantidadeEmprestimo() {
		return quantidadeEmprestimo;
	}

	public void setQuantidadeEmprestimo(int quantidadeEmprestimo) {
		this.quantidadeEmprestimo = quantidadeEmprestimo;
	}

	@Override
	public String toString() {
		return "PeriodicoDTO [codigo=" + codigo + ", tipo=" + tipo + ", nome=" + nome + ", id=" + id + ", volume="
				+ volume + ", quantidade=" + quantidade + ", quantidadeEmprestimo=" + quantidadeEmprestimo + "]";
	}

}
