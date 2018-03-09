package com.kazale.pontointeligente.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoDto {
	
	private Optional<Long> id = Optional.empty();
	
	@NotEmpty(message = "Data não pode ser vazia.")
	private String data;
	private String tipo;
	private String descricao;
	private String localizacao;
	private Long funcionarioId;

	@Override
	public String toString() {
		return "LancamentoDto [Id=" + id + ", data=" + data + ", tipo" + tipo + ", descricao=" + descricao 
				+ ", localizacao=" + localizacao + ", funcionarioId=" + funcionarioId + "]";
	}
}
