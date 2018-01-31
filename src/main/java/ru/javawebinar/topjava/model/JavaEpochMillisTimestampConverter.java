package ru.javawebinar.topjava.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;

/** @author danis.tazeev@gmail.com */
@Converter
public final class JavaEpochMillisTimestampConverter implements AttributeConverter<Long, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(Long attribute) {
        return new Timestamp(attribute);
    }

    @Override
    public Long convertToEntityAttribute(Timestamp dbData) {
        return dbData.getTime();
    }
}
