package tech.metamaker.pontointeligente.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "empresa")
@Data
public class Empresa implements Serializable {

	private static final long serialVersionUID = 4533895176403940902L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "razao_social", nullable = false)
	private String razaoSocial;

	@Column(name = "cnpj", nullable = false)
	private String cnpj;

	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;

	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;

	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Funcionario> funcionarios;

}
