package br.com.syntax.controledeponto.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="empresa")
public class Empresa {

  private Long id;
  private String razaoSocial;
  private String cnpj;
  private Date dataCriacao;
  private List<Funcionario> funcionarios;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getRazaoSocial() {
    return razaoSocial;
  }

  public void setRazaoSocial(String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }

  @Column(name = "cnpj", nullable = false)
  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  @Column(name = "data_criacao", nullable = false)
  public Date getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(Date dataCriacao) {
    this.dataCriacao = dataCriacao;
  }

  @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public List<Funcionario> getFuncionarios() {
    return funcionarios;
  }

  public void setFuncionarios(List<Funcionario> funcionarios) {
    this.funcionarios = funcionarios;
  }

  @PrePersist
  public void prePersist() {
    this.dataCriacao = new Date();
  }
}
