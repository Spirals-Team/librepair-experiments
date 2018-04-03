package net.mirwaldt.jcomparison.core.decorator.tracing;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.basic.impl.IdentityComparingItem;
import net.mirwaldt.jcomparison.core.decorator.DecoratingComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;

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
public class TracingComparator extends DecoratingComparator<Object> {

    public TracingComparator(ItemComparator<Object, ? extends ComparisonResult<?,?,?>> delegate) {
        super(delegate);
    }

    @Override
    public ComparisonResult<?,?,?> compare(Object leftItem, 
                                           Object rightItem, 
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        final boolean isLeftItemTraced = isTraced(leftItem);
        IdentityComparingItem leftIdentityComparingItem = null;
        if(isLeftItemTraced) {
            leftIdentityComparingItem = new IdentityComparingItem(leftItem);
            visitedObjectsTrace.writeVisitedLeftObjects().add(leftIdentityComparingItem);
        }

        final boolean isRightItemTraced = isTraced(rightItem);
        IdentityComparingItem rightIdentityComparingItem = null;
        if(isRightItemTraced) {
            rightIdentityComparingItem = new IdentityComparingItem(rightItem);
            visitedObjectsTrace.writeVisitedRightObjects().add(rightIdentityComparingItem);
        }

        try {
            return super.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        } finally {
            if(isLeftItemTraced) {
                visitedObjectsTrace.writeVisitedLeftObjects().remove(leftIdentityComparingItem);
            }
            if(isRightItemTraced) {
                visitedObjectsTrace.writeVisitedRightObjects().remove(rightIdentityComparingItem);
            }
        }
    }

    private boolean isTraced(Object item) {
        if(item == null) {
            return false;
        } else if(item.getClass().isPrimitive()) {
            return false;
        } else if(MutablePrimitive.class.isAssignableFrom(item.getClass())) {
            return false;
        } else {
            return true;
        }
    }
}
