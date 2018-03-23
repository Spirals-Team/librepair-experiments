package com.kairos.pontointeligente.api.enums;

import com.kairos.pontointeligente.api.comum.enumsBase.EnumConverter;

public enum TipoEnum implements EnumConverter<Integer> {
	INICIO_TRABALHO(1, "Inicia do Turno de Trabalho"),  
	TERMINO_TRABALHO(2, "Termino do Turno de Trabalho"),
	INICIO_ALMOCO(3, "Inicia do horario de almoço"), 
	TERMINO_ALMOCO(4, "Inicia do horario de almoço"), 
	INICIO_PAUSA(5, "Inicio da Pausa"),
	TERMINAO_PAUSA(6, "Termino da Pausa");

	private Integer codigo;

	private String descricao;

	private TipoEnum(final Integer codigo, final String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	@Override
	public Integer getCodigo() {
		return this.codigo;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
