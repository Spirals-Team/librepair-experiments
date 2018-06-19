package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.Instant;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CharSequenceToYearConverter implements Converter<CharSequence, Year> {

    private final DateTimeFormatter dateTimeFormatter;

    public CharSequenceToYearConverter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public Year convert(CharSequence in) throws Exception {
        if (in == null || in.length() == 0) return null;
        return Year.parse(in, dateTimeFormatter);
    }
}
