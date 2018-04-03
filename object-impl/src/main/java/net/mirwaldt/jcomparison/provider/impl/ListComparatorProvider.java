package net.mirwaldt.jcomparison.provider.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.facade.BasicComparators;
import net.mirwaldt.jcomparison.provider.api.ComparatorProvider;

import java.util.List;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ListComparatorProvider implements ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> {
    private final List<ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>>> itemComparators;
    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> defaultItemComparator;

    public ListComparatorProvider(List<ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>>> itemComparators, ItemComparator<Object, ? extends ComparisonResult<?,?,?>> defaultItemComparator) {
        this.itemComparators = itemComparators;
        this.defaultItemComparator = defaultItemComparator;
    }

    @Override
    public ItemComparator<Object, ? extends ComparisonResult<?,?,?>> provideComparator(Object objectMeta, Object leftObject, Object rightObject) {
        for (ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> itemComparatorProvider : itemComparators) {
            final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> itemComparator = itemComparatorProvider.provideComparator(objectMeta, leftObject, rightObject);
            if (itemComparator != null && itemComparator != BasicComparators.getNoComparator()) {
                return itemComparator;
            }
        }

        return defaultItemComparator;
    }
}
