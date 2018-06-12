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
package io.github.fasset.fasset.kernel.util;

import java.util.List;

/**
 * This is an extension used to carry data through the depreciation chain from one handler to the next
 * and then to the batch process writer
 *
 * @param <E> Type of element in the collection
 */
public interface ProcessingList<E> extends List<E> {

    /**
     * @return # of items added in the list
     */
    int getItemsAdded();

    /**
     * @return # of items processed
     */
    int getItemsProcessed();

    /**
     * @return # of items remaining to be processed
     */
    int getRemainingItems();
}
