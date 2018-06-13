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

import org.eclipse.collections.impl.list.mutable.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Simple data-structure that allows additional Fassets-specific functionality like logs
 * and polling of items processed
 *
 * @param <E> Type of item contained by this data structure
 * @author edwin.njeru
 */
public class ProcessingListImpl<E> extends FastList<E> implements ProcessingList<E> {

    private static final Logger log = LoggerFactory.getLogger(ProcessingListImpl.class);

    private int itemsAdded;
    private int itemsProcessed;
    private int remainingItems;

    public ProcessingListImpl() {
        this.itemsAdded = 0;
        this.itemsProcessed = 0;
        this.remainingItems = 0;
    }

    /**
     * Appends the specified element to the end of this list.
     * <p>
     *
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(E e) {

        itemsAdded++;

        log.debug("ProceedsList : adding item : {}", e);

        return super.add(e);
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {

        log.debug("Polling from ProceedsList with consumer : {}", consumer);

        remainingItems = super.size();

        super.forEach(proceed -> {
            itemsProcessed++;
            --remainingItems;
            consumer.accept(proceed);
        });
    }

    @Override
    public int getItemsAdded() {
        return itemsAdded;
    }

    @Override
    public int getItemsProcessed() {
        return itemsProcessed;
    }

    @Override
    public int getRemainingItems() {
        return remainingItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ProcessingListImpl<?> that = (ProcessingListImpl<?>) o;

        return itemsAdded == that.itemsAdded && itemsProcessed == that.itemsProcessed && remainingItems == that.remainingItems;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + itemsAdded;
        result = 31 * result + itemsProcessed;
        result = 31 * result + remainingItems;
        return result;
    }
}
