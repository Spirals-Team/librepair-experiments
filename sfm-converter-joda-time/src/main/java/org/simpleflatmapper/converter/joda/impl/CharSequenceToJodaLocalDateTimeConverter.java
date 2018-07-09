package org.simpleflatmapper.converter.joda.impl;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.simpleflatmapper.converter.Converter;


public class CharSequenceToJodaLocalDateTimeConverter implements Converter<CharSequence, LocalDateTime> {
    private final DateTimeFormatter dateTimeFormatter;

    public CharSequenceToJodaLocalDateTimeConverter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public LocalDateTime convert(CharSequence in) throws Exception {
        if (in == null || in.length() == 0) return null;
        return dateTimeFormatter.parseLocalDateTime(String.valueOf(in));
    }
}
