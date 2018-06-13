/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.kernel.util.convert;

import io.github.fasset.fasset.kernel.util.ConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 * Converts java.util.Date to java.time.LocalDate
 */
@Component("dateToLocalDateConverter")
public class DateToLocalDateConverter implements Converter<Date, LocalDate> {

    private static final Logger log = LoggerFactory.getLogger(DateToLocalDateConverter.class);

    @Override
    public LocalDate convert(Date date) {

        LocalDate converted = null;

        Date toConvert = nullDateReassignment(date);

        log.debug("Converting : {} to LocalDate", date);

        try {

            converted = new java.sql.Date(toConvert.getTime()).toLocalDate();

        } catch (Throwable e) {

            throw new ConverterException(String.format("Exception thrown while converting %s to localDate", date), e);
        }

        log.debug("{} successfully converted to {}", date, converted);

        return converted;
    }

    private Date nullDateReassignment(Date date) {

        if (date == null) {

            Date nullDate = Date.from(Instant.now());

            log.warn("The date given is null reassigning to : {}", nullDate);

            return nullDate;

        }
        return date;
    }
}
