package com.Gabriel.Biblioteca.api.dtos;

public class AutorDTO {

	private int id;
	private int codigo;
	private String nome;
	private String sobrenome;

	public AutorDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AutorDTO [id=" + id + ", codigo=" + codigo + ", nome=" + nome + ", sobrenome=" + sobrenome + "]";
	}

}
