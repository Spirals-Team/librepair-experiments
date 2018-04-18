package br.com.tsp.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.tsp.api.enums.TipoEnum;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8222174310673996045L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name = "descricao", nullable = true)
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private TipoEnum tipo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Funcionario funcionario;
	
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEnum tipo) {
		this.tipo = tipo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		dataCriacao = atual;
		dataAtualizacao = atual;
	}
	
	@PreUpdate
	public void preUpdate() {
		dataAtualizacao = new Date();
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", data=" + data + ", descricao=" + descricao + ", tipo=" + tipo
				+ ", funcionario=" + funcionario + ", dataCriacao=" + dataCriacao + ", dataAtualizacao="
				+ dataAtualizacao + "]";
	}
}
