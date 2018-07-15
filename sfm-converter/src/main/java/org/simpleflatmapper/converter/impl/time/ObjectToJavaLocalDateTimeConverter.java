package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Date;


public class ObjectToJavaLocalDateTimeConverter implements Converter<Object, LocalDateTime> {
    private final ZoneId zone;

    public ObjectToJavaLocalDateTimeConverter(ZoneId zoneId) {
        this.zone = zoneId;
    }

    @Override
    public LocalDateTime convert(Object o) throws Exception {
        if (o == null) {
            return null;
        }

        if (o instanceof Date) {
            return Instant.ofEpochMilli(((Date) o).getTime()).atZone(zone).toLocalDateTime();
        }

        if (o instanceof Instant) {
            final Instant instant = (Instant) o;
            return instant.atZone(zone).toLocalDateTime();
        }

        if (o instanceof LocalDateTime) {
            return (LocalDateTime) o;
        }

        if (o instanceof TemporalAccessor) {
            return LocalDateTime.from((TemporalAccessor) o);
        }

        throw new IllegalArgumentException("Cannot convert " + o + " to LocalDateTime");
    }
}
