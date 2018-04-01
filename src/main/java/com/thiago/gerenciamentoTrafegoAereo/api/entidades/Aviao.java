package com.thiago.gerenciamentoTrafegoAereo.api.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "aviao")
public class Aviao
{
	private Long codigo;
	private String nome;
	
	@Id
	@GeneratedValue(generator = "increment_aviao_codigo")
	@GenericGenerator(name = "increment_aviao_codigo", strategy = "increment")
	@Column(name = "aviao_codigo")
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	
	@Column(name = "aviao_nome", nullable = false)
	@Length(min = 1, max = 255, message="O nome do avião deve ter entre 1 e 255 caracteres.")
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}	
}
