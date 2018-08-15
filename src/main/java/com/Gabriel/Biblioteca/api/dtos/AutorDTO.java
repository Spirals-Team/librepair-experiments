package com.Gabriel.Biblioteca.api.dtos;

import javax.validation.constraints.NotEmpty;

public class AutorDTO {

	private int id;
	private String codigo;
	private String nome;
	private String sobrenome;

	public AutorDTO() {
		// TODO Auto-generated constructor stub
	}

	@NotEmpty(message= "Código não deve ser vazio")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@NotEmpty(message= "Nome não deve ser vazio")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message= "Sobrenome não deve ser vazio")
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
