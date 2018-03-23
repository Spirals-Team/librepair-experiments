package com.kairos.pontointeligente.api.comum.enumsBase;

import javax.persistence.AttributeConverter;

public abstract class EnumAttributeConverterAbstract<X extends EnumConverter<Y>, Y>
		implements AttributeConverter<X, Y> {
	@Override
	public final Y convertToDatabaseColumn(final X x) {
		return x != null ? x.getCodigo() : null;
	}

	public X convertToEntityAttribute(final Class<X> classe, final Y dbData) {
		return EnumConverter.lookup(classe, dbData);
	}
}
