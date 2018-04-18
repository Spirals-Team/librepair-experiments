package br.com.tsp.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.com.tsp.api.enums.PerfilEnum;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289175815779312918L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@Column(name = "valor_hora", nullable = true)
	private BigDecimal valorHora;
	
	@Column(name = "qtd_horas_trabalho", nullable = true)
	private Float qtdHorasTrabalhoDia;
	
	@Column(name = "qtd_horas_almoco", nullable = true)
	private Float qtdHorasAlmoco;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "perfil", nullable = false)
	private PerfilEnum perfil;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Empresa empresa;
	
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;
	
	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Lancamento> lancamentos;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public BigDecimal getValorHora() {
		return valorHora;
	}
	
	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}
	
	public Float getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}
	
	public void setQtdHorasTrabalhoDia(Float qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}
	
	public Float getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}
	
	public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}
	
	public PerfilEnum getPerfil() {
		return perfil;
	}
	
	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
		return "Funcionario [id=" + id + ", nome=" + nome + ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia="
				+ qtdHorasTrabalhoDia + ", qtdHorasAlmoco=" + qtdHorasAlmoco + ", perfil=" + perfil + ", empresa="
				+ empresa + ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + "]";
	}
	
}
