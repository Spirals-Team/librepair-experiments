package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.primitive.impl.*;
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
public abstract class EpsilonLongComparator implements ValueComparator<Object> {
    
    private final PairFactory pairFactory;

    public EpsilonLongComparator(PairFactory pairFactory) {
        this.pairFactory = pairFactory;
    }

    @Override
    public ValueComparisonResult<Object> compare(Object leftValue, Object rightValue, VisitedObjectsTrace visitedObjectsTrace, ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if (leftValue instanceof Number && rightValue instanceof Number) {
            return compareLongs(((Number) leftValue).longValue(), ((Number) rightValue).longValue(), null, null, (Class<? extends Number>) leftValue.getClass());
        } else if (leftValue instanceof MutablePrimitive && rightValue instanceof MutablePrimitive) {
            if(leftValue instanceof MutableByte && rightValue instanceof MutableByte) {
                MutableByte leftMutable = (MutableByte) leftValue;
                MutableByte rightMutable = (MutableByte) rightValue;
                return compareLongs(leftMutable.getValue(), rightMutable.getValue(), leftMutable, rightMutable, null);
            } else if(leftValue instanceof MutableShort && rightValue instanceof MutableShort) {
                MutableShort leftMutable = (MutableShort) leftValue;
                MutableShort rightMutable = (MutableShort) rightValue;
                return compareLongs(leftMutable.getValue(), rightMutable.getValue(), leftMutable, rightMutable, null);
            } else if(leftValue instanceof MutableInt && rightValue instanceof MutableInt) {
                MutableInt leftMutable = (MutableInt) leftValue;
                MutableInt rightMutable = (MutableInt) rightValue;
                return compareLongs(leftMutable.getValue(), rightMutable.getValue(), leftMutable, rightMutable, null);
            } else if(leftValue instanceof MutableLong && rightValue instanceof MutableLong) {
                MutableLong leftMutable = (MutableLong) leftValue;
                MutableLong rightMutable = (MutableLong) rightValue;
                return compareLongs(leftMutable.getValue(), rightMutable.getValue(), leftMutable, rightMutable, null);
            } else {
                return throwException(leftValue, rightValue);
            }
        } else {
            return throwException(leftValue, rightValue);
        }
    }

    private ValueComparisonResult<Object> throwException(Object leftValue, Object rightValue) throws ComparisonFailedException {
        throw new ComparisonFailedException(String.format(
                "At least one value is neither a byte nor a short nor an int nor a long " + 
                        "nor a primitive mutable byte nor primitive mutable short." +
                        "nor a primitive mutable int nor primitive mutable long. Or both types are different."
                +"(The type of leftValue is '%s' and the type of the right value is '%s')",
                leftValue.getClass().getName(), rightValue.getClass().getName()), leftValue, rightValue);
    }


    protected Pair<?> createPair(long leftLong, 
                                 long rightLong, 
                                 MutablePrimitive<?> leftMutable, 
                                 MutablePrimitive<?> rightMutable, 
                                 Class<? extends Number> primitiveType) throws ComparisonFailedException {
        Pair<?> doublePair;
        if (leftMutable != null && rightMutable != null) {
            try {
                doublePair = pairFactory.createPair(leftMutable, rightMutable);
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot create double pair!", e, leftMutable, rightMutable);
            }
        } else {
            try {
                doublePair = pairFactory.createPair(leftLong, rightLong, primitiveType);
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot create double pair!", e, leftLong, rightLong);
            }
        }
        return doublePair;
    }

    protected abstract ValueComparisonResult<Object> compareLongs(long leftLong,
                                                                  long rightLong,
                                                                  MutablePrimitive<?> leftMutable,
                                                                  MutablePrimitive<?> rightMutable,
                                                                  Class<? extends Number> primitiveType) throws ComparisonFailedException;
}
