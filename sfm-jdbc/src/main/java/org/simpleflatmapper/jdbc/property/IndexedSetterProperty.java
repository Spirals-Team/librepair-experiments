package org.simpleflatmapper.jdbc.property;


import org.simpleflatmapper.jdbc.impl.setter.PreparedStatementSetterImpl;
import org.simpleflatmapper.map.property.SetterFactoryProperty;
import org.simpleflatmapper.map.mapper.PropertyMapping;
import org.simpleflatmapper.reflect.IndexedSetter;
import org.simpleflatmapper.reflect.Setter;
import org.simpleflatmapper.reflect.SetterFactory;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;

public class IndexedSetterProperty extends SetterFactoryProperty {

    private final IndexedSetter<?, ?> setter;

    public <P> IndexedSetterProperty(final IndexedSetter<PreparedStatement, P> setter) {
        super(new SetterFactory<PreparedStatement, Object>() {
            @SuppressWarnings("unchecked")
            @Override
            public <PP> Setter<PreparedStatement, PP> getSetter(Object arg) {
                return new PreparedStatementSetterImpl<PP>(((PropertyMapping)arg).getColumnKey().getIndex(), (IndexedSetter<PreparedStatement, PP>) setter);
            }
        }, PreparedStatement.class);
        this.setter = setter;
    }

    public IndexedSetter<?, ?> getIndexedSetter() {
        return setter;
    }

    @Override
    public String toString() {
        return "IndexedSetter{IndexedSetter}";
    }
}
