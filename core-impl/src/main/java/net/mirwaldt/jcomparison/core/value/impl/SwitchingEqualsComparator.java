package net.mirwaldt.jcomparison.core.value.impl;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;

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
public class SwitchingEqualsComparator<ValueType> implements ValueComparator<ValueType> {
    private final ValueComparator<ValueType> immutableResultEqualsComparator;
    private final ValueComparator<ValueType> threadSafeMutableResultEqualsComparator;

    public SwitchingEqualsComparator(ValueComparator<ValueType> immutableResultEqualsComparator, 
                                     ValueComparator<ValueType> threadSafeMutableResultEqualsComparator) {
        this.immutableResultEqualsComparator = immutableResultEqualsComparator;
        this.threadSafeMutableResultEqualsComparator = threadSafeMutableResultEqualsComparator;
    }

    @Override
    public ValueComparisonResult<ValueType> compare(ValueType leftItem, 
                                                    ValueType rightItem, 
                                                    VisitedObjectsTrace visitedObjectsTrace, 
                                                    ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if(comparatorOptions.canReturnMutuableResult()) {
            return threadSafeMutableResultEqualsComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        } else {
            return immutableResultEqualsComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        }
    }
}
