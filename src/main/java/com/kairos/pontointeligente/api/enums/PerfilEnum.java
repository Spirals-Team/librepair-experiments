package com.kairos.pontointeligente.api.enums;

import com.kairos.pontointeligente.api.comum.enumsBase.EnumConverter;

public enum PerfilEnum implements EnumConverter<Integer> {
	ROLE_ADMINISTRADOR(1, "Perfil Administrador"), ROLE_USUARIO(2, "Perfil de Usuário");

	private Integer codigo;

	private String descricao;

	private PerfilEnum(final Integer codigo, final String descricao) {
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
