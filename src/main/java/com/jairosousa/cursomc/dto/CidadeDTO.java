package com.jairosousa.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.jairosousa.cursomc.domain.Cidade;

public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(min = 3, max = 120, message = "O tamanho deve ser entre 3 e 120 caracteres")
	private String nome;

	public CidadeDTO() {
	}

	public CidadeDTO(Cidade obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
