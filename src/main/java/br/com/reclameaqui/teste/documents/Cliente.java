package br.com.reclameaqui.teste.documents;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "cliente")
@TypeAlias("cliente")
public class Cliente {

	@Id
	private String id;
	
	@NotNull(message = "Campo Obrigatorio!")
	private String nome;
	private String telefone;
	
	@CPF
	private String cpf;
	private Endereco endereco;
	
	public Cliente(String nome, String telefone, String cpf, Endereco endereco) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.cpf = cpf;
		this.endereco = endereco;
	}
	
	public Cliente update(Cliente cliente, Cliente clienteDetails) {
		cliente.setNome(clienteDetails.getNome());
		cliente.setTelefone(clienteDetails.getTelefone());
		cliente.setCpf(clienteDetails.getCpf());
		cliente.setEndereco(clienteDetails.getEndereco());
		return cliente;
	}

}
