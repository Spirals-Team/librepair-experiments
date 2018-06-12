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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Date;
import java.util.Objects;

/**
 * Takes {@link Date} converting it to {@link YearMonth}
 *
 * @author edwin.njeru
 */
@Component("dateToYearMonthConverter")
public class DateToYearMonthConverter implements Converter<Date, YearMonth> {

    private static final Logger log = LoggerFactory.getLogger(DateToYearMonthConverter.class);

    private DateToLocalDateConverter dateToLocalDateConverter;

    private LocalDateToYearMonthConverter localDateToYearMonthConverter;


    public DateToYearMonthConverter(DateToLocalDateConverter dateToLocalDateConverter) {
        this.dateToLocalDateConverter = dateToLocalDateConverter;
    }

    @Autowired
    public DateToYearMonthConverter(@Qualifier("localDateToYearMonthConverter") LocalDateToYearMonthConverter localDateToYearMonthConverter,
                                    @Qualifier("dateToLocalDateConverter") DateToLocalDateConverter dateToLocalDateConverter) {
        this.localDateToYearMonthConverter = localDateToYearMonthConverter;
        this.dateToLocalDateConverter = dateToLocalDateConverter;
    }


    public DateToYearMonthConverter() {
    }

    public DateToLocalDateConverter getDateToLocalDateConverter() {
        return dateToLocalDateConverter;
    }

    public DateToYearMonthConverter setDateToLocalDateConverter(DateToLocalDateConverter dateToLocalDateConverter) {
        this.dateToLocalDateConverter = dateToLocalDateConverter;
        return this;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public YearMonth convert(Date source) {

        YearMonth convertedMonth;

        log.debug("Converting {} to YearMonth", source.toString());

        Date convertFrom = nullDateReassignment(source);

        try {

            convertedMonth = localDateToYearMonthConverter.convert(Objects.requireNonNull(dateToLocalDateConverter.convert(convertFrom)));

        } catch (Throwable e) {
            if (dateToLocalDateConverter == null) {
                throw new ConverterException("The dateToLocalDateConverter is null", e);
            } else {
                throw new ConverterException(String.format("Exception thrown while converting %s to YearMonth", source), e);
            }
        }

        return convertedMonth;
    }

    private Date nullDateReassignment(Date source) {

        if (source == null) {

            Date reassignedDate = Date.from(Instant.now());

            log.error("The source data is null.. reassigning to current time : {}", reassignedDate);

            return reassignedDate;
        }

        return source;
    }
}
