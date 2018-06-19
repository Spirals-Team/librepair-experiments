package org.simpleflatmapper.converter.joda.impl;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.simpleflatmapper.converter.Converter;


public class CharSequenceToJodaLocalDateConverter implements Converter<CharSequence, LocalDate> {
    private final DateTimeFormatter dateTimeFormatter;

    public CharSequenceToJodaLocalDateConverter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public LocalDate convert(CharSequence in) throws Exception {
        if (in == null || in.length() == 0) return null;
        return dateTimeFormatter.parseLocalDate(String.valueOf(in));
    }
}
