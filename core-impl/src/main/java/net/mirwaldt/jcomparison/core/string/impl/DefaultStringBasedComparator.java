package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

import java.util.function.Function;

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
public class DefaultStringBasedComparator<ObjectType> implements ItemComparator<ObjectType, ComparisonResult<?,?,?>> {
    private final Function<ObjectType, String> toStringFunction;
    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> substringComparator;

    public DefaultStringBasedComparator(Function<ObjectType, String> toStringFunction, ItemComparator<Object, ? extends ComparisonResult<?,?,?>> substringComparator) {
        this.toStringFunction = toStringFunction;
        this.substringComparator = substringComparator;
    }

    @Override
    public ComparisonResult<?,?,?> compare(ObjectType leftItem, 
                                           ObjectType rightItem, 
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        return compare(leftItem, rightItem);
    }

    @Override
    public ComparisonResult<?,?,?> compare(ObjectType leftItem, ObjectType rightItem) throws ComparisonFailedException {
        return substringComparator.compare(toStringFunction.apply(leftItem), toStringFunction.apply(rightItem));
    }
}
