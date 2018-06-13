package org.simpleflatmapper.datastax.impl.setter;

import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import org.simpleflatmapper.map.FieldMapper;
import org.simpleflatmapper.map.SourceMapper;
import org.simpleflatmapper.converter.Converter;

public class ConverterToUDTValueMapper<I> implements Converter<I, UDTValue> {

    private final FieldMapper<I, UDTValue> mapper;
    private final UserType userType;

    public ConverterToUDTValueMapper(FieldMapper<I, UDTValue> mapper, UserType userType) {
        this.mapper = mapper;
        this.userType = userType;
    }

    @Override
    public UDTValue convert(I in) throws Exception {
        if (in == null) return null;
        UDTValue udtValue = userType.newValue();
        mapper.mapTo(in, udtValue, null);
        return udtValue;
    }
}
