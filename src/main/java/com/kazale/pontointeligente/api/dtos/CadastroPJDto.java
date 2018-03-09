package com.kazale.pontointeligente.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroPJDto {
	
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 a 200 caracters.")
	private String nome;
	
	@NotEmpty(message = "Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 a 200 caracters.")
	@Email(message = "Email inválido.")
	private String email;
	
	@NotEmpty(message = "Senha não pode ser vazio.")
	private String senha;
	
	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message = "CPF inválido.")
	private String cpf;
	
	@NotEmpty(message = "Razáo social não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Razáo social deve conter entre 5 a 200 caracters.")
	private String razaoSocial;
	
	@NotEmpty(message = "CNPJ não pode ser vazio.")
	@CNPJ(message = "CNPJ inválido.")
	private String cnpj;
	
	public CadastroPJDto() {
	}

	@Override
	public String toString() {
		return "CadastroPJDto [Id=" + id + ", nome=" + nome + ", email" + email + ", senha=" + senha + ", cpf=" + cpf 
				+ ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}
}
