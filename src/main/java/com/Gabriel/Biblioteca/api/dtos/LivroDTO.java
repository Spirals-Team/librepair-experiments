package com.Gabriel.Biblioteca.api.dtos;

public class LivroDTO {

	private int id;
	private String codigo;
	private int tipo = 0;
	private String nome = "";

	public LivroDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "LivroDTO [id=" + id + ", codigo=" + codigo + ", tipo=" + tipo + ", nome=" + nome + "]";
	}

}
