package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Date;


public class ObjectToJavaLocalDateConverter implements Converter<Object, LocalDate> {
    private final ZoneId zone;

    public ObjectToJavaLocalDateConverter(ZoneId zoneId) {
        this.zone = zoneId;
    }

    @Override
    public LocalDate convert(Object o) throws Exception {
        if (o == null) {
            return null;
        }

        if (o instanceof Date) {
            return Instant.ofEpochMilli(((Date) o).getTime()).atZone(zone).toLocalDate();
        }

        if (o instanceof Instant) {
            Instant instant = (Instant) o;
            return instant.atZone(zone).toLocalDate();
        }

        if (o instanceof LocalDate) {
            return (LocalDate) o;
        }

        if (o instanceof LocalDateTime) {
            return ((LocalDateTime)o).toLocalDate();
        }

        if (o instanceof TemporalAccessor) {
            return LocalDate.from((TemporalAccessor) o);
        }

        throw new IllegalArgumentException("Cannot convert " + o + " to LocalDate");
    }
}
