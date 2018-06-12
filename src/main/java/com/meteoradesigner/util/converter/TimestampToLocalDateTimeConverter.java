package com.meteoradesigner.util.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

//Dependency hibernate.core added to add the implementation's interface.

/**
 * This class @code{TimestampToLocalDateTimeConverter} converts from/to db data
 * (<code>Timestamp</code>) to/from attribute (<code>LocalDateTime</code>).
 * For all entities in enabled auto mode; spring-context's asserts using for checking.
 */
@Converter(autoApply = true)
public class TimestampToLocalDateTimeConverter implements AttributeConverter<
        LocalDateTime, Timestamp> {
    //TODO test on next iteration.

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attributeLocalDateTime) {
        if (attributeLocalDateTime != null) {
            return Timestamp.valueOf(attributeLocalDateTime);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dBTimestamp) {
        if (dBTimestamp != null) {
            return dBTimestamp.toLocalDateTime();
        }
        return null;
    }
}
