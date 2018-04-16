package br.com.reclameaqui.teste.dtos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReclamacaoDTO {

	private String id;

	@NotNull()
	private String nome;
	
	@NotNull()
	private String descricao;

	@NotNull()
	private EmpresaDTO empresa;
	
	@NotNull()
	private EnderecoDTO endereco;
	
	@NotNull()
	private String idCliente;

	
}