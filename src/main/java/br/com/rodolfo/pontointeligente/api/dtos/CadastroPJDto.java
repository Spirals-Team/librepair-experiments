package br.com.rodolfo.pontointeligente.api.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

/**
 * CadastroPJDto
 */
public class CadastroPJDto {

    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio.")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
    private String nome;

    @NotEmpty(message = "Email não pode ser vazio")
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @Email(message = "Email inválido.")
    private String email;

    @NotEmpty(message = "Senha não pode ser vazia.")
    private String senha;

    @NotEmpty(message = "CPF não pode ser vazio.")
    @CPF(message = "CPF inválido.")
    private String cpf;

    @NotEmpty(message = "Razão Social não pode ser vazio.")
    @Length(min = 5, max = 200, message = "Razão Social deve conter entre 5 e 200 caracteres.")
    private String razaoSocial;

    @NotEmpty(message = "CNPJ não pode ser vazio.")
    @CNPJ(message = "CNPJ inválido.")
    private String cnpj;

    public CadastroPJDto() {}

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return String return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return String return the razaoSocial
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * @param razaoSocial the razaoSocial to set
     */
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    /**
     * @return String return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "CadastroPJDto [ id = " + id + ", nome = " + nome + ", email = " + email + ", senha = " + senha + ", cpf = " + cpf + ", razaoSocial = " + razaoSocial+ ", cnpj = " + cnpj + " ]";
    }

}