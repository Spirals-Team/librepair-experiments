package br.com.reclameaqui.teste.dtos;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

	public ReclamacaoDTO(String nome, String descricao, EmpresaDTO empresa, EnderecoDTO endereco, String idCliente) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.empresa = empresa;
		this.endereco = endereco;
		this.idCliente = idCliente;
	}
	
	
	
}