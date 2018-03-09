package com.kazale.pontointeligente.api.dtos;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDto {
	private Long id;
	private String razaoSocial;
	private String cnpj;
	
	public EmpresaDto() {
	}
	
	@Override
	public String toString() {
		return "EmpresaDto [Id=" + id + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}
}
