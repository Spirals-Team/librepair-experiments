package br.com.reclameaqui.teste.dtos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ClienteDTO {

	private String id;

	@NotNull()
	private String nome;
	private String telefone;

	@CPF
	@NotNull()
	private String cpf;
	private EnderecoDTO endereco;
	
	
}