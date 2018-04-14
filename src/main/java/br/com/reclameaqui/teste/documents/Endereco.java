package br.com.reclameaqui.teste.documents;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

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
	
	public Endereco update(Endereco endereco, Endereco enderecoDetails) {
		endereco.setLogradouro(enderecoDetails.getLogradouro());
		endereco.setNumero(enderecoDetails.getNumero());
		endereco.setBairro(enderecoDetails.getBairro());
		endereco.setCidade(enderecoDetails.getCidade());
		endereco.setCep(enderecoDetails.getCep());
		
		return endereco;
	}
	
}
