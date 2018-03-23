package com.kairos.pontointeligente.api.enums;

import java.util.EnumSet;

import com.kairos.pontointeligente.api.comum.enumsBase.EnumConverter;

public enum MesesEnum implements EnumConverter<String> {
	JANEIRO("01", "Janeiro"), FEVEREIRO("02", "Fevereiro"), MARCO("03", "Março"), ABRIL("04", "Abril"), MAIO("05",
			"Maio"), JUNHO("06", "Junho"), JULHO("07", "Julho"), AGOSTO("08", "Agosto"), SETEMBRO("09",
					"Setembro"), OUTUBRO("10", "Outubro"), NOVEMBRO("11", "Novembro"), DEZEMBRO("12", "Dezembro");

	private String codigo;

	private String descricao;

	public String getCodigo() {
		return codigo;
	}

	void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	MesesEnum(final String codigo, final String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static MesesEnum lookup(final String codigo) {
		if (codigo != null) {
			try {
				final Integer valor = Integer.valueOf(codigo.trim());
				return lookup(valor);
			} catch (final NumberFormatException e) {
				return null;
			}
		}

		return null;
	}

	public static MesesEnum lookup(final int codigo) {
		for (final MesesEnum mes : EnumSet.allOf(MesesEnum.class)) {
			if (Integer.valueOf(mes.codigo).equals(codigo)) {
				return mes;
			}
		}

		return null;
	}

}
