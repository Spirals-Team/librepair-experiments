package br.com.syntax.controledeponto.entities;

import br.com.syntax.controledeponto.enums.TipoDeControle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable {

  private Long id;
  private String descricao;
  private String localizacao;
  private Date data;
  private Date dataCriacao;
  private Date dataAtualizacao;
  private TipoDeControle tipo;
  private Funcionario funcionario;

  public Lancamento() {}

  public Lancamento(Funcionario funcionario) {
    this.funcionario = funcionario;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name = "descricao", nullable = false)
  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  @Column(name = "localizacao", nullable = false)
  public String getLocalizacao() {
    return localizacao;
  }

  public void setLocalizacao(String localizacao) {
    this.localizacao = localizacao;
  }

  @Temporal(TemporalType.TIMESTAMP) // Gravar data e hora
  @Column(name = "data", nullable = false)
  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  @Column(name = "data_criacao", nullable = false)
  public Date getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(Date dataCriacao) {
    this.dataCriacao = dataCriacao;
  }

  @Column(name = "data_atualizacao", nullable = false)
  public Date getDataAtualizacao() {
    return dataAtualizacao;
  }

  public void setDataAtualizacao(Date dataAtualizacao) {
    this.dataAtualizacao = dataAtualizacao;
  }

  @Column(name = "tipo", nullable = false)
  public TipoDeControle getTipo() {
    return tipo;
  }

  public void setTipo(TipoDeControle tipo) {
    this.tipo = tipo;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  public Funcionario getFuncionario() {
    return funcionario;
  }

  public void setFuncionario(Funcionario funcionario) {
    this.funcionario = funcionario;
  }

  @PreUpdate
  public void preUpdate() {
    this.dataAtualizacao = new Date();
  }

  @PrePersist
  public void prePersist() {
    Date hoje = new Date();
    this.dataAtualizacao = hoje;
    this.dataCriacao = hoje;
  }
}
