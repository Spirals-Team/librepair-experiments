package tech.metamaker.pontointeligente.api.entities;

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

import lombok.Data;
import tech.metamaker.pontointeligente.api.enums.PerfilEnum;

@Entity
@Table(name = "funcionario")
@Data
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1446551163313094515L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "cpf", nullable = false)
	private String cpf;

	@Column(name = "valor_hora", nullable = false)
	private BigDecimal valorHora;

	@Column(name = "qtd_horas_trabalho_dia", nullable = true)
	private Float qtdHorasTrabalhoDia;

	@Column(name = "qtd_horas_almoco", nullable = true)
	private Float qtdHorasAlmoco;

	@Column(name = "perfil", nullable = true)
	@Enumerated(EnumType.STRING)
	private PerfilEnum perfil;

	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;

	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Empresa empresa;
	
	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Lancamento> lancamentos;

	@Transient
	public Optional<Float> getQtdHorasTrabalhoDiaOpt() {
		return Optional.ofNullable(this.qtdHorasTrabalhoDia);
	}

	@Transient
	public Optional<BigDecimal> getGetValorHoraOpt() {
		return Optional.ofNullable(this.valorHora);
	}
	
	@PreUpdate
	public void preUpdate() {
		this.dataAtualizacao = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date dataAtual = new Date();
		this.dataAtualizacao = dataAtual;
		this.dataCriacao = dataAtual;
	}

}
