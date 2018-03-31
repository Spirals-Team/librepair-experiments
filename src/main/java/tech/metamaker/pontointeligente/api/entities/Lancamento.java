package tech.metamaker.pontointeligente.api.entities;

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

import lombok.Data;
import tech.metamaker.pontointeligente.api.enums.TipoEnum;

@Entity
@Table(name = "lancamento")
@Data
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 6585355724319565265L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	private Date data;

	@Column(name = "descricao", nullable = false)
	private String descricao;

	@Column(name = "localizacao", nullable = false)
	private String localizacao;

	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;

	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoEnum tipo;

	@ManyToOne(fetch = FetchType.EAGER)
	private Funcionario funcionario;
	
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
