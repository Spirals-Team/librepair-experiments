package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class JavaLocalDateTojuDateConverter implements Converter<LocalDate, Date> {
    private final ZoneId dateTimeZone;

    public JavaLocalDateTojuDateConverter(ZoneId dateTimeZone) {
        this.dateTimeZone = dateTimeZone;
    }

    @Override
    public Date convert(LocalDate in) throws Exception {
        if (in == null) return null;
        return Date.from(in.atStartOfDay(dateTimeZone).toInstant());
    }
}
