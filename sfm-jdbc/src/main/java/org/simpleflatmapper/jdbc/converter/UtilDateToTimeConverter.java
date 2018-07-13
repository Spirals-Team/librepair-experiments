package org.simpleflatmapper.jdbc.converter;

import org.simpleflatmapper.converter.Converter;

import java.sql.Time;
import java.util.Date;

public class UtilDateToTimeConverter implements Converter<Date, Time> {
    @Override
    public Time convert(Date in) throws Exception {
        if (in != null) {
            return new Time(in.getTime());
        }
        return null;
    }
}
