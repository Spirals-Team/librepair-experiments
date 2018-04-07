package org.simpleflatmapper.converter.impl.time;


import org.simpleflatmapper.converter.Converter;

import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class JavaTemporalToStringConverter implements Converter<Temporal, String> {

    private final DateTimeFormatter dateTimeFormatter;

    public JavaTemporalToStringConverter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public String convert(Temporal in) throws Exception {
        return dateTimeFormatter.format(in);
    }
}
