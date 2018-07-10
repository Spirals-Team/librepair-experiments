package br.com.syntax.controledeponto.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CadastroEmpresaDto {

  private Long id;
  private String razaoSocial;
  private String cnpj;

  // Informação do dono da Empresa
  private String nome;
  private String cpf;
  private String email;
  private String senha;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @NotEmpty(message = "A razão social não pode ser vazia.")
  @Length(min = 3, max = 200, message = "A razão social deve conter entre 3 e 200 caracteres")
  public String getRazaoSocial() {
    return razaoSocial;
  }

  public void setRazaoSocial(@NotNull String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }

  @NotEmpty(message = "O CNPJ não pode ser vazio.")
  @CNPJ(message = "O CNPJ está inválido")
  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(@NotNull String cnpj) {
    this.cnpj = cnpj;
  }

  @NotEmpty(message = "O nome não pode ser vazio.")
  @Length(min = 3, max = 200, message = "O nome deve conter entre 3 e 200 caracteres")
  public String getNome() {
    return nome;
  }

  public void setNome(@NotNull  String nome) {
    this.nome = nome;
  }

  @NotEmpty(message = "O email não pode ser vazio.")
  @Length(min = 10, max = 200, message = "O email deve conter entre 10 e 200 caracteres")
  @Email(message = "Email inválido")
  public String getEmail() {
    return email;
  }

  public void setEmail(@NotNull String email) {
    this.email = email;
  }

  @NotEmpty(message = "A senha não pode ser vazia.")
  @Length(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
  public String getSenha() {
    return senha;
  }

  public void setSenha(@NotNull String senha) {
    this.senha = senha;
  }

  @NotEmpty(message = "O CPF não pode ser vazio.")
  @CPF(message = "O CPF está inválido")
  public String getCpf() {
    return cpf;
  }

  public void setCpf(@NotNull String cpf) {
    this.cpf = cpf;
  }
}
