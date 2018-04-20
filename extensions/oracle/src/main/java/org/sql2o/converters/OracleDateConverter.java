package org.sql2o.converters;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import oracle.sql.TIMESTAMP;

/**
 * Created by lars on 01.05.14.
 */
public class OracleDateConverter extends DateConverter implements ConvertersProvider {
    @Override
    public Date convert(Object val) throws ConverterException {

        if (val instanceof TIMESTAMP) {
            try {
                return ((TIMESTAMP) val).timestampValue();
            } catch (SQLException e) {
                throw new ConverterException(
                    "Error trying to convert oracle.sql.TIMESTAMP to DateTime", e);
            }
        }

        return super.convert(val);
    }

    @Override
    public void fill(Map<Class<?>, Converter<?>> mapToFill) {
        mapToFill.put(Date.class, new OracleDateConverter());
    }
}
