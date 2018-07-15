package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.Year;
import java.time.ZoneId;
import java.util.Date;

public class DateToJavaYearConverter implements Converter<Date, Year> {
    private final ZoneId zoneId;

    public DateToJavaYearConverter(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public Year convert(Date in) throws Exception {
        if (in == null) return null;
        return Year.of(in.toInstant().atZone(zoneId).getYear());
    }
}
