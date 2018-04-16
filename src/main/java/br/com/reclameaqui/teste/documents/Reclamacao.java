package br.com.reclameaqui.teste.documents;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
@Document(collection="reclamacao")
@TypeAlias("reclamacao")
public class Reclamacao {

	@Id
	private String id;
	
	@NotNull(message = "Campo Obrigatorio!")
	private String nome;
	
	@NotNull(message = "Campo Obrigatorio!")
	private String descricao;
	
	@NotNull(message = "Campo Obrigatorio!")
	private Endereco endereco;
	
	@NotNull(message = "Campo Obrigatorio!")
	private Empresa empresa;
	
	@DBRef
	private Cliente idCliente;
	
	public Reclamacao(String nome, String descricao, Endereco endereco, Empresa empresa, Cliente idCliente) {
		this.nome = nome;
		this.descricao = descricao;
		this.endereco = endereco;
		this.empresa = empresa;
		this.idCliente = idCliente;
	}
	
	public Reclamacao update(Reclamacao reclamacao, Reclamacao reclamacaoDetails) {
		reclamacao.setNome(reclamacaoDetails.getNome());
		reclamacao.setDescricao(reclamacaoDetails.getDescricao());
		reclamacao.setEndereco(reclamacaoDetails.getEndereco());
		reclamacao.setEmpresa(reclamacaoDetails.getEmpresa());
		reclamacao.setIdCliente(reclamacaoDetails.getIdCliente());
		
		return reclamacao;
	}

}
