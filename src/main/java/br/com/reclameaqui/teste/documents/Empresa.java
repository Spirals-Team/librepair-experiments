package br.com.reclameaqui.teste.documents;

import java.io.Serializable;

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
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cnpj;

	@NotNull(message = "Campo Obrigatorio!")
	private String nome;

}
