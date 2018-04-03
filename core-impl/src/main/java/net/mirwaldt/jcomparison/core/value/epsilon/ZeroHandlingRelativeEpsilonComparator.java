package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;

import java.math.BigDecimal;
import java.math.BigInteger;

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
public class ZeroHandlingRelativeEpsilonComparator implements ValueComparator<Object> {
    private final ValueComparator<Object> epsilonComparator;
    private final ValueComparator<Object> equalsComparator;

    private final BigDecimal ANOTHER_ZERO = BigDecimal.valueOf(0d);
    
    public ZeroHandlingRelativeEpsilonComparator(ValueComparator<Object> epsilonComparator, 
                                                 ValueComparator<Object> equalsComparator) {
        this.epsilonComparator = epsilonComparator;
        this.equalsComparator = equalsComparator;
    }

    @Override
    public ValueComparisonResult<Object> compare(Object leftItem,
                                                 Object rightItem,
                                                 VisitedObjectsTrace visitedObjectsTrace,
                                                 ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if (isZero(leftItem, rightItem)) {
            return equalsComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        } else {
            return epsilonComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        }
    }

    private boolean isZero(Object leftItem, Object rightItem) {
        return isZero(leftItem) || isZero(rightItem);
    }

    private boolean isZero(Object item) {
        // BigInteger and BigDecimal are also numbers => their if-clause must be before the if-clause of number 
        if (item instanceof BigInteger) {
            return BigInteger.ZERO.equals(item);
        } else if (item instanceof BigDecimal) {
            return BigDecimal.ZERO.equals(item) || ANOTHER_ZERO.equals(item);
        } if (item instanceof Number) {
            Number number = (Number) item;
            if (number instanceof Float || number instanceof Double) {
                return number.doubleValue() == 0d;
            } else {
                return number.longValue() == 0L;
            }
        } else if (item instanceof MutablePrimitive) {
            final MutablePrimitive<?> mutablePrimitive = (MutablePrimitive) item;
            return mutablePrimitive.isZero();
        } else {
            return false;
        }
    }
}
