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
public class EnderecoDTO {
	
	@NotNull(message = "Campo Obrigatorio!")
	private String cep;
	
	@NotNull(message = "Campo Obrigatorio!")
	private String logradouro;
	
	@NotNull(message = "Campo Obrigatorio!")
	private String numero;
	
	@NotNull(message = "Campo Obrigatorio!")
	private String bairro;
	
	@NotNull(message = "Campo Obrigatorio!")
	private String cidade;

}