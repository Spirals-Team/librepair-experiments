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
import io.github.ghacupha.cash.ReadableHardCash;
import org.junit.Before;
import org.junit.Test;

import static io.github.ghacupha.cash.HardCash.shilling;
import static org.junit.Assert.assertEquals;

public class CashAttributeConverterTest {

    private CashAttributeConverter converter;

    @Before
    public void setUp() throws Exception {

        converter = new CashAttributeConverter();
    }

    @Test
    public void convertToDatabaseColumn() {

        assertEquals("KES 15623.45", converter.convertToDatabaseColumn(shilling(15623.45)));
    }

    @Test
    public void convertToEntityAttribute() {

        Cash readableCash = ReadableHardCash.parse("KES 15623.45");

        assertEquals(readableCash, converter.convertToEntityAttribute("KES 15623.45"));
    }
}