package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CharSequenceToYearMonthConverter implements Converter<CharSequence, YearMonth> {

    private final DateTimeFormatter dateTimeFormatter;

    public CharSequenceToYearMonthConverter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public YearMonth convert(CharSequence in) throws Exception {
        if (in == null || in.length() == 0) return null;
        return YearMonth.parse(in, dateTimeFormatter);
    }
}
