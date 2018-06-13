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
package io.github.fasset.fasset.book.keeper.unit.time;

import io.github.ghacupha.time.point.ReadableDate;
import io.github.ghacupha.time.point.ReadableTime;
import io.github.ghacupha.time.point.SimpleDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReadableDateTest {

    private ReadableTime readableTime;

    @Before
    public void setUp() throws Exception {

        readableTime = new ReadableDate("dd/MM/yyyy", SimpleDate.newMoment(2018, 05, 02));
    }

    @Test
    public void parseString() throws Exception {

        assertEquals(SimpleDate.on(2018, 4, 03), readableTime.parseString("03/04/2018"));
    }

    @Test
    public void parseStringWithPattern() throws Exception {

        assertEquals(SimpleDate.on(2018, 8, 03), readableTime.parseString("08/03/2018", "MM/dd/yyyy"));
    }

    @Test
    public void toStringTest() {

        assertEquals("02/05/2018", readableTime.toString());
    }
}