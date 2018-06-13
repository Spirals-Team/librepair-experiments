package org.simpleflatmapper.converter.impl.time;

import org.simpleflatmapper.converter.Converter;

import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateToJavaYearMonthConverter implements Converter<Date, YearMonth> {
    private final ZoneId zoneId;

    public DateToJavaYearMonthConverter(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public YearMonth convert(Date in) throws Exception {
        if (in == null) return null;
        ZonedDateTime zonedDateTime = in.toInstant().atZone(zoneId);
        return YearMonth.of(zonedDateTime.getYear(), zonedDateTime.getMonth());
    }
}
