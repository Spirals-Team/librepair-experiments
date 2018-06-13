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
package io.github.fasset;

import io.github.fasset.fasset.kernel.util.ProcessingList;
import io.github.fasset.fasset.kernel.util.ProcessingListImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProcessingListPrototypeTest {

    private ProcessingList<Integer> processingList;

    @Before
    public void setUp() throws Exception {
        processingList = new ProcessingListImpl<>();
    }

    @Test
    public void processingListWorks() throws Exception {

        processingList.add(21);
        processingList.add(43);
        processingList.add(32);
        processingList.add(25);
        processingList.add(51);

        System.out.println(processingList.getItemsAdded());
        System.out.println(processingList.getItemsProcessed());
        System.out.println(processingList.getRemainingItems());

        Assert.assertEquals(5, processingList.getItemsAdded());
        Assert.assertEquals(0, processingList.getItemsProcessed());
        Assert.assertEquals(0, processingList.getRemainingItems());

        processingList.forEach(System.out::println);

        Assert.assertEquals(5, processingList.getItemsAdded());
        Assert.assertEquals(5, processingList.getItemsProcessed());
        Assert.assertEquals(0, processingList.getRemainingItems());

    }
}
