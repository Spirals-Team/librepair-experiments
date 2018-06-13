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
package io.github.fasset.fasset.book.keeper;

import io.github.ghacupha.time.point.DateRange;
import io.github.ghacupha.time.point.SimpleDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateRangeTest {

    private DateRange dateRange;

    @Before
    public void setUp() throws Exception {
        dateRange = new DateRange(SimpleDate.newMoment(2017, 9, 30), SimpleDate.newMoment(2017, 12, 30));
    }

    @Test
    public void upTo() {

        assertTrue(dateRange.includes(SimpleDate.newMoment(2017, 11, 30)));
        assertTrue(dateRange.includes(SimpleDate.newMoment(2017, 12, 30)));
        assertFalse(dateRange.includes(SimpleDate.newMoment(2017, 12, 31)));

        DateRange infiniteStart = DateRange.upTo((SimpleDate) SimpleDate.newMoment(2017, 11, 30));
        DateRange infiniteEnd = DateRange.startingOn((SimpleDate) SimpleDate.newMoment(2017, 11, 30));

        assertTrue(infiniteStart.includes(SimpleDate.newMoment(1900, 01, 01)));
        assertTrue(infiniteEnd.includes(SimpleDate.newMoment(9999, 01, 01)));
    }


}