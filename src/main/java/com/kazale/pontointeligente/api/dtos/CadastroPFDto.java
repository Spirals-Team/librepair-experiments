package com.kazale.pontointeligente.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroPFDto {
	
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message = "Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	@Email(message = "Email inválido.")
	private String email;
	
	@NotEmpty(message = "Senha não pode ser vazia.")
	private String senha;
	
	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message = "CPF inválido.")
	private String cpf;
	
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	private String cnpj;
	
	public CadastroPFDto() {
	}
	
	@Override
	public String toString() {
		return "CadastroPFDto [Id=" + id + ", nome=" + nome + ", email" + email + ", senha=" + senha + ", cpf=" + cpf 
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + ", cnpj=" + cnpj + "]";
	}
	
}
