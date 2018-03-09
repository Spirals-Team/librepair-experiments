package com.kazale.pontointeligente.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDto {
	
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message = "Email não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Email deve conter entre 3 e 200 caracteres.")
	@Email(message = "Email inválido.")
	private String email;
	
	private Optional<String> senha = Optional.empty();
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	
	@Override
	public String toString() {
		return "FuncionarioDto [Id=" + id + ", nome=" + nome + ", email" + email + ", senha=" + senha + ", valorHora=" 
				+ valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="	+ qtdHorasAlmoco + ""
				+ "]";
	}
}
