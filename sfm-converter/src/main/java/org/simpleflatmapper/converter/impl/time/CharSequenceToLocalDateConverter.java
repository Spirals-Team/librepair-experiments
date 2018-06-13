package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CharSequenceToLocalDateConverter implements Converter<CharSequence, LocalDate> {

    private final DateTimeFormatter dateTimeFormatter;

    public CharSequenceToLocalDateConverter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public LocalDate convert(CharSequence in) throws Exception {
        if (in == null || in.length() == 0) return null;
        return LocalDate.parse(in, dateTimeFormatter);
    }
}
