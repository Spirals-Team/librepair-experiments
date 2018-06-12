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
package io.github.fasset.fasset.book.keeper.util;


import io.github.ghacupha.cash.Cash;
import io.github.ghacupha.cash.ReadableCash;
import io.github.ghacupha.cash.ReadableHardCash;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Used to convert Cash object state to database column representation and back again
 *
 * @author edwin.njeru
 */
@Converter(autoApply = true)
public class CashAttributeConverter implements AttributeConverter<Cash, String> {

    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param attribute the entity attribute value to be converted
     * @return the converted data to be stored in the database column
     */
    @Override
    public String convertToDatabaseColumn(Cash attribute) {

        ReadableCash cash = new ReadableHardCash(attribute);

        return cash.getString();
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
    public Cash convertToEntityAttribute(String dbData) {

        return ReadableHardCash.parse(dbData);
    }
}
