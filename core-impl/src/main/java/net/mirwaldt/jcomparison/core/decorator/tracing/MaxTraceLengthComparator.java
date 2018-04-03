package net.mirwaldt.jcomparison.core.decorator.tracing;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.decorator.DecoratingComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

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
 *
 *
 * Attention:
 * - must be used with TracingComparator!
 * - TracingComparator must come before, not later (otherwise it would stop too late)
 *
 * Created by Michael on 19.08.2017.
 */
public class MaxTraceLengthComparator extends DecoratingComparator<Object> {
    private final int maxTraceLength;
    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> alternativeDelegate;

    public MaxTraceLengthComparator(ItemComparator<Object, ? extends ComparisonResult<?,?,?>> delegate, int maxTraceLength, ItemComparator<Object, ? extends ComparisonResult<?,?,?>> alternativeDelegate) {
        super(delegate);
        this.maxTraceLength = maxTraceLength;
        this.alternativeDelegate = alternativeDelegate;
    }

    @Override
    public ComparisonResult<?,?,?> compare(Object leftItem, 
                                           Object rightItem, 
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if (visitedObjectsTrace.readVisitedLeftObjects().size() < maxTraceLength
                || visitedObjectsTrace.readVisitedRightObjects().size() < maxTraceLength) {
            return super.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        } else {
            return alternativeDelegate.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        }
    }
}
