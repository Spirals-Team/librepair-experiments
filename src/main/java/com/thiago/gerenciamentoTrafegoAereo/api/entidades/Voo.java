package com.thiago.gerenciamentoTrafegoAereo.api.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "voo")
public class Voo
{
	private Long codigo;
	private Piloto piloto;
	private Aviao aviao;
	private Cidade cidadeOrigem;
	private Cidade cidadeDestino;
	private Date dataPartida;
	private Date dataChegada;
	

	@Id
	@GeneratedValue(generator = "increment_voo_codigo")
	@GenericGenerator(name = "increment_voo_codigo", strategy = "increment")
	@Column(name = "voo_codigo")
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Piloto getPiloto()
	{
		return piloto;
	}
	public void setPiloto(Piloto piloto)
	{
		this.piloto = piloto;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Aviao getAviao()
	{
		return aviao;
	}
	public void setAviao(Aviao aviao)
	{
		this.aviao = aviao;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	public Cidade getCidadeOrigem()
	{
		return cidadeOrigem;
	}
	public void setCidadeOrigem(Cidade cidade_origem)
	{
		this.cidadeOrigem = cidade_origem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Cidade getCidadeDestino()
	{
		return cidadeDestino;
	}
	public void setCidadeDestino(Cidade cidade_destino)
	{
		this.cidadeDestino = cidade_destino;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="voo_data_partida")
	public Date getDataPartida()
	{
		return dataPartida;
	}
	public void setDataPartida(Date dataPartida)
	{
		this.dataPartida = dataPartida;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="voo_data_chegada")
	public Date getDataChegada()
	{
		return dataChegada;
	}
	public void setDataChegada(Date dataChegada)
	{
		this.dataChegada = dataChegada;
	}	
}
