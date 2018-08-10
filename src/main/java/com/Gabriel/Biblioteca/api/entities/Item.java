package com.Gabriel.Biblioteca.api.entities;

public class Item {

	private String codigo;
	private int tipo = 0;
	private String nome = "";

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

	@Override
	public String toString() {
		return "Item [codigo=" + codigo + ", tipo=" + tipo + ", nome=" + nome + "]";
	}

}
