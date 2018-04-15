package com.ivson.ponto.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.ivson.ponto.api.enums.PerfilEnum;

@Entity
@Table(name = "funcionario", schema = "pontointeligente")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String nome;

	@NotNull
	@Email
	private String email;

	@NotNull
	private String senha;

	@NotNull
	@CPF
	private String cpf;

	@Column(name="valor_hora")
	@NotNull
	private BigDecimal valorHora;

	@Column(name="qtd_horas_trabalho_dia")
	@NotNull
	private Float qtdHorasTrabalhoDia;

	@Column(name="qtd_horas_almoco")
	@NotNull
	private Float qtdHorasAlmoco;

	@Enumerated(EnumType.STRING)	// coloca o NOME da role, e nao o numero
	@Column(name = "perfil")
	@NotNull
	private PerfilEnum perfil;

	@Column(name="data_criacao")
	@NotNull
	private Date dataCriacao;

	@Column(name = "data_atualizacao")
	@NotNull
	private Date dataAtualizacao;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Empresa empresa;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@NotNull
	private List<Lancamento> lancamentos;

	public Funcionario() {

	}
	
	/**
	 * Ao salvar sempre
	 */
	@PreUpdate
	public void preUpdate() {
		dataAtualizacao = new Date();
	}
	
	/**
	 * Ao salvar na primeira vez
	 */
	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		dataCriacao = atual;
		dataAtualizacao = atual;
	}


	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getCpf() {
		return cpf;
	}

	@Transient
	public Optional<BigDecimal> getValorHoraOptional() {
		return Optional.ofNullable(valorHora);
	}
	
	public BigDecimal getValorHora() {
		return valorHora;
	}
	
	/**
	 * Metodo para fazer conversoes com o DTO
	 * TRANSIENT = o JPA deve ignorar este metodo, nao esta relacionado com o mapeamento do BD
	 * @return
	 */
	@Transient
	public Optional<Float> getQtdHorasTrabalhoDiaOptional() {
		return Optional.ofNullable(qtdHorasTrabalhoDia);
	}

	public Float getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}

	public Float getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	public PerfilEnum getPerfil() {
		return perfil;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	public void setQtdHorasTrabalhoDia(Float qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}

	public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + ", perfil=" + perfil + ", dataCriacao=" + dataCriacao + ", dataAtualizacao="
				+ dataAtualizacao + ", empresa=" + empresa + "]";
	}

}
