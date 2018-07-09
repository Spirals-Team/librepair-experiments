package org.simpleflatmapper.jdbc.converter.time;

import org.simpleflatmapper.converter.Converter;

import java.sql.Time;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class TimeToOffsetTimeConverter implements Converter<Time, OffsetTime> {
    private final ZoneOffset offset;

    public TimeToOffsetTimeConverter(ZoneOffset offset) {
        this.offset = offset;
    }

    @Override
    public OffsetTime convert(Time in) throws Exception {
        if (in == null) return null;
        return in.toLocalTime().atOffset(offset);
    }
}
