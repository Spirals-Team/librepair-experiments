package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

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
 *
 * Whenever a primitive value itself is passed to a item comparator, it must be boxed because only objects are allowed.
 * Primitive values are no objects and must be boxed which automatically happens.
 * (MutablePrimitive instances avoid that)
 * Only a small range of primitive values is internally cached in the JVM.
 * Whenever primitive values are collected in maps as values, they must be boxed.
 * Therefore many boxed primitive values put the gc much under pressure or even cause an OutOfMemory exception.
 * This comparator replaces instances similar to String.intern().
 * <p>
 * This comparator uses a weak hash map so that the garbage collector has a chance to collect garbage is memory runs full.
 * Otherwise caching the boxed primitive values itself harms the performance.
 * <p>
 * Created by Michael on 20.07.2017.
 */
@NotThreadSafe
public class BoxedPrimitiveValuesDeduplicatingItemComparator extends DecoratingComparator<Object> {
    private final Deduplicator deduplicator;

    public BoxedPrimitiveValuesDeduplicatingItemComparator(ItemComparator<Object, ? extends ComparisonResult<?,?,?>> delegate, Deduplicator deduplicator) {
        super(delegate);
        this.deduplicator = deduplicator;
    }

    @Override
    public ComparisonResult<?,?,?> compare(Object leftObject, Object rightObject, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {

        final Object finalLeftObject;
        if (leftObject.getClass().isPrimitive()) {
            try {
                finalLeftObject = deduplicator.deduplicate(leftObject);
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot deduplicate left object.", e, leftObject, rightObject);
            }
        } else {
            finalLeftObject = leftObject;
        }

        final Object finalRightObject;
        if (rightObject.getClass().isPrimitive()) {
            try {
                finalRightObject = deduplicator.deduplicate(rightObject);
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot deduplicate right object.", e, leftObject, rightObject);
            }
        } else {
            finalRightObject = rightObject;
        }

        return super.compare(finalLeftObject, finalRightObject, visitedObjectsTrace);
    }
}
