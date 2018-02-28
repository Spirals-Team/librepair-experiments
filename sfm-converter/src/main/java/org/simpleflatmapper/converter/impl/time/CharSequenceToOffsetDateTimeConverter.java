package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CharSequenceToOffsetDateTimeConverter implements Converter<CharSequence, OffsetDateTime> {

    private final DateTimeFormatter dateTimeFormatter;

    public CharSequenceToOffsetDateTimeConverter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public OffsetDateTime convert(CharSequence in) throws Exception {
        if (in == null || in.length() == 0) return null;
        return OffsetDateTime.parse(in, dateTimeFormatter);
    }
}
