package br.com.syntax.controledeponto.entities;

import br.com.syntax.controledeponto.enums.Perfil;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="funcionario")
public class Funcionario implements Serializable {

  private Long id;
  private String nome;
  private String email;
  private String senha;
  private String cpf;
  private BigDecimal valorHora;
  private Integer quantidadeHorasTrabalhadasDia;
  private Integer quantidadeHorasAlmoco;
  private Perfil perfil;
  private Date dataAdmissao;
  private Date dataAtualizacao;
  private Empresa empresa;
  private List<Lancamento> lancamentos;


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name = "nome", nullable = false)
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Column(name = "email", nullable = false)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "senha", nullable = false)
  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  @Column(name = "cpf", nullable = false)
  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  @Column(name = "valor_hora", nullable = false)
  public BigDecimal getValorHora() {
    return valorHora;
  }

  public void setValorHora(BigDecimal valorHora) {
    this.valorHora = valorHora;
  }

  @Column(name = "quantidade_horas_dia", nullable = false)
  public Integer getQuantidadeHorasTrabalhadasDia() {
    return quantidadeHorasTrabalhadasDia;
  }

  public void setQuantidadeHorasTrabalhadasDia(Integer quantidadeHorasTrabalhadasDia) {
    this.quantidadeHorasTrabalhadasDia = quantidadeHorasTrabalhadasDia;
  }

  @Column(name = "quantidade_horas_almoco", nullable = false)
  public Integer getQuantidadeHorasAlmoco() {
    return quantidadeHorasAlmoco;
  }

  public void setQuantidadeHorasAlmoco(Integer quantidadeHorasAlmoco) {
    this.quantidadeHorasAlmoco = quantidadeHorasAlmoco;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "perfil", nullable = false)
  public Perfil getPerfil() {
    return perfil;
  }

  public void setPerfil(Perfil perfil) {
    this.perfil = perfil;
  }

  @Column(name = "data_admissao", nullable = false)
  public Date getDataAdmissao() {
    return dataAdmissao;
  }

  public void setDataAdmissao(Date dataAdmissao) {
    this.dataAdmissao = dataAdmissao;
  }

  @Column(name = "data_atualizacao", nullable = false)
  public Date getDataAtualizacao() {
    return dataAtualizacao;
  }

  public void setDataAtualizacao(Date dataAtualizacao) {
    this.dataAtualizacao = dataAtualizacao;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  public Empresa getEmpresa() {
    return empresa;
  }

  public void setEmpresa(Empresa empresa) {
    this.empresa = empresa;
  }

  @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public List<Lancamento> getLancamentos() {
    return lancamentos;
  }

  public void setLancamentos(List<Lancamento> lancamentos) {
    this.lancamentos = lancamentos;
  }

  @PreUpdate
  public void preUpdate() {
    this.dataAtualizacao = new Date();
  }

  @PrePersist
  public void prePersist() {
    Date hoje = new Date();
    this.dataAtualizacao = hoje;
    this.dataAdmissao = hoje;
  }
}

