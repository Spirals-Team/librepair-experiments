package br.com.reclameaqui.teste.dtos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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