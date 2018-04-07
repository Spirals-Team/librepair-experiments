package org.simpleflatmapper.jdbc.converter;


import org.simpleflatmapper.converter.Converter;
import org.simpleflatmapper.reflect.Getter;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.List;

public class SqlArrayToJavaArrayConverter<T> implements Converter<Array, T[]> {
    private final T[] emptyArray;

    private final SqlArrayToListConverter<T> sqlArrayToListConverter;

    @SuppressWarnings("unchecked")
    public SqlArrayToJavaArrayConverter(Class<T> elementType, Getter<? super ResultSet, ? extends T> getter) {
        this.emptyArray = (T[]) java.lang.reflect.Array.newInstance(elementType, 0);
        this.sqlArrayToListConverter = new SqlArrayToListConverter<T>(getter);
    }

    @Override
    public T[] convert(Array in) throws Exception {
        List<T> list = sqlArrayToListConverter.convert(in);
        return list != null ? list.toArray(emptyArray) : null;
    }
}
