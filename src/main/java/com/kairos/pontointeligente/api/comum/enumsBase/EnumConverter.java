package com.kairos.pontointeligente.api.comum.enumsBase;

import java.util.stream.Stream;

public interface EnumConverter<T> {
	T getCodigo();

	static <E extends EnumConverter<T>, T> E lookup(final Class<E> classe, final T codigo) {

		if (codigo == null) {
			return null;
		}

		return Stream.of(classe.getEnumConstants()).filter(e -> e.getCodigo().equals(codigo)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"Nenhum valor encontrado para o código: " + codigo + "no enum: " + classe.getCanonicalName()));
	}

}
