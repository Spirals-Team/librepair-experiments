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

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class enables the JPA specification to convert a {@link java.time.YearMonth} variable
 * into database-friendly format that is not persisted as a blob through implementation of
 * JPA 2.1's {@link javax.persistence.AttributeConverter}
 * interface.
 * It is applied automatically to any {@link YearMonth} variable that is being persisted to the
 * database, just before the persistence engines flushes it to the database.
 * Again just before the model is rehydrated with database values, the data is converted back from
 * {@link Date} to {@link YearMonth} object maintaining user accessibility in both the database and
 * the application.
 *
 * @author edwin.njeru
 */
@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, Date> {

    private static final Logger log = LoggerFactory.getLogger(YearMonthAttributeConverter.class);

    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param attribute the entity attribute value to be converted
     * @return the converted data to be stored in the database column
     */
    @Override
    public Date convertToDatabaseColumn(YearMonth attribute) {

        Date dbDate = null;

        if (attribute != null) {
            log.trace("Converting attribute : {} to database column", attribute);
            try {
                dbDate = Date.from(attribute.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            } catch (Throwable e) {
                String message = String.format("Exception thrown while converting %s to java.sql.Date", attribute);
                throw new ConverterException(message, e);
            }
        } else {
            log.trace("The attribute passed for conversion to database column is null");
        }

        log.trace("Returning month as : {}", dbDate);

        return dbDate;
    }

    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct dbData type for the corresponding column
     * for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param dbData the data from the database column to be converted
     * @return the converted value to be stored in the entity attribute
     */
    @Override
    public YearMonth convertToEntityAttribute(Date dbData) {

        log.trace("Converting database data : {} to entity YearMonth attribute", dbData);

        YearMonth retval = null;

        try {
            retval = YearMonth.from(dbData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } catch (Throwable e) {
            String message = String.format("Exception thrown while converting date: %s to yearMonth", dbData);
            throw new ConverterException(message, e);
        }

        log.trace("Returning month as : {}", retval);

        return retval;
    }
}
