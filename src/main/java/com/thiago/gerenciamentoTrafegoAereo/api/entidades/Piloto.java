package com.thiago.gerenciamentoTrafegoAereo.api.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "piloto")
public class Piloto
{
	private Long codigo;
	private String nome;
	private String identificacao;
	
	@Id
	@GeneratedValue(generator = "increment_piloto_codigo")
	@GenericGenerator(name = "increment_piloto_codigo", strategy = "increment")
	@Column(name = "piloto_codigo")
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	
	@Column(name = "piloto_nome", nullable = false)
	@Length(min = 3, max = 255, message="Nome do piloto deve ter entre 3 e 255 caracteres.")
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	@Column(name = "piloto_identificacao", nullable = false)
	@Length(min = 1, max = 255, message="A identificação do piloto deve ter entre 1 e 255 caracteres.")
	public String getIdentificacao()
	{
		return identificacao;
	}
	public void setIdentificacao(String identificacao)
	{
		this.identificacao = identificacao;
	}	
}
