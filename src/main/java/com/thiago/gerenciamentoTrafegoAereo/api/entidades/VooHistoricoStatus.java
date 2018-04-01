package com.thiago.gerenciamentoTrafegoAereo.api.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "voo_historico_status")
public class VooHistoricoStatus
{
	private Long codigo;
	private String descricao;
	private Voo voo;
	
	@Id
	@GeneratedValue(generator = "increment_voo_historico_status_codigo")
	@GenericGenerator(name = "increment_voo_historico_status_codigo", strategy = "increment")
	@Column(name = "voo_historico_status_codigo")
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	
	@Column(name = "voo_historico_status_descricao", nullable = false)
	@Length(min = 1, max = 255, message="A descrição do voo deve ter entre 3 e 255 caracteres.")
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Voo getVoo()
	{
		return voo;
	}
	public void setVoo(Voo voo)
	{
		this.voo = voo;
	}
}
