package br.com.reclameaqui.teste.dtos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

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
public class EmpresaDTO {

	@NotNull()
	@CNPJ
	private String cnpj;

	@NotNull()
	private String nome;

}