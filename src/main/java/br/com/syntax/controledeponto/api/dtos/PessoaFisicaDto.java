package br.com.syntax.controledeponto.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class PessoaFisicaDto {

  private Long id;
  private String nome;
  private String cpf;
  private String email;
  private String senha;

  private BigDecimal valorHora;
  private Integer quantidadeHorasTrabalhadasDia;
  private Integer quantidadeHorasAlmoco;

  private String cnpj;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @NotEmpty(message = "O nome não pode ser vazio.")
  @Length(min = 3, max = 200, message = "O nome deve conter entre 3 e 200 caracteres")
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @NotEmpty(message = "O CPF não pode ser vazio.")
  @CPF(message = "O CPF está inválido")
  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  @NotEmpty(message = "O email não pode ser vazio.")
  @Length(min = 10, max = 200, message = "O email deve conter entre 10 e 200 caracteres")
  @Email(message = "Email inválido")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @NotEmpty(message = "A senha não pode ser vazia.")
  @Length(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public BigDecimal getValorHora() {
    return valorHora;
  }

  public void setValorHora(BigDecimal valorHora) {
    this.valorHora = valorHora;
  }

  public Integer getQuantidadeHorasTrabalhadasDia() {
    return quantidadeHorasTrabalhadasDia;
  }

  public void setQuantidadeHorasTrabalhadasDia(Integer quantidadeHorasTrabalhadasDia) {
    this.quantidadeHorasTrabalhadasDia = quantidadeHorasTrabalhadasDia;
  }

  public Integer getQuantidadeHorasAlmoco() {
    return quantidadeHorasAlmoco;
  }

  public void setQuantidadeHorasAlmoco(Integer quantidadeHorasAlmoco) {
    this.quantidadeHorasAlmoco = quantidadeHorasAlmoco;
  }

  @NotEmpty(message = "O CNPJ não pode ser vazio.")
  @CNPJ(message = "O CNPJ está inválido")
  public String getCnpj() {
    return cnpj;
  }


  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }
}
