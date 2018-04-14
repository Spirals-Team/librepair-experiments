package br.com.reclameaqui.teste.documents;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cnpj;

	@NotNull(message = "Campo Obrigatorio!")
	private String nome;

	public Empresa update(Empresa empresa, Empresa empresaDetails) {
		empresa.setNome(empresaDetails.getNome());
		empresa.setCnpj(empresaDetails.getCnpj());

		return empresa;
	}

}
