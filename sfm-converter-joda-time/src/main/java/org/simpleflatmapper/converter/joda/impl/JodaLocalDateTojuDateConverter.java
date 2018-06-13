package org.simpleflatmapper.converter.joda.impl;

import org.joda.time.LocalDate;
import org.simpleflatmapper.converter.Converter;

import java.util.Date;

public class JodaLocalDateTojuDateConverter implements Converter<LocalDate, Date> {

    @Override
    public Date convert(LocalDate in) throws Exception {
        if (in == null) return null;
        return in.toDate();
    }
}
