package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JavaLocalDateTimeTojuDateConverter implements Converter<LocalDateTime, Date> {
    private final ZoneId dateTimeZone;

    public JavaLocalDateTimeTojuDateConverter(ZoneId dateTimeZone) {
        this.dateTimeZone = dateTimeZone;
    }

    @Override
    public Date convert(LocalDateTime in) throws Exception {
        if (in == null) return null;
        return Date.from(in.atZone(dateTimeZone).toInstant());
    }
}
