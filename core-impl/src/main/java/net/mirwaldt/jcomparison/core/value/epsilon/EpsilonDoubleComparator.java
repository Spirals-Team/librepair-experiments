package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableDouble;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableFloat;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueSimilarity;

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
public abstract class EpsilonDoubleComparator implements ValueComparator<Object> {

    private final PairFactory pairFactory;

    public EpsilonDoubleComparator(PairFactory pairFactory) {
        this.pairFactory = pairFactory;
    }

    @Override
    public ValueComparisonResult<Object> compare(Object leftValue, Object rightValue, VisitedObjectsTrace visitedObjectsTrace, ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if (leftValue instanceof Double && rightValue instanceof Double) {
            return compareDouble((double) leftValue, (double) rightValue, null, null);
        } else if (leftValue instanceof Float && rightValue instanceof Float) {
            return compareFloats((float) leftValue, (float) rightValue, null, null);
        } if (leftValue instanceof MutableDouble && rightValue instanceof MutableDouble) {
            MutableDouble leftMutableDouble = (MutableDouble) leftValue;
            MutableDouble rightMutableDouble = (MutableDouble) rightValue;
            return compareDouble(leftMutableDouble.getValue(), rightMutableDouble.getValue(), leftMutableDouble, rightMutableDouble);
        } else if (leftValue instanceof MutableFloat && rightValue instanceof MutableFloat) {
            MutableFloat leftMutableFloat = (MutableFloat) leftValue;
            MutableFloat rightMutableFloat = (MutableFloat) rightValue;
            return compareFloats(leftMutableFloat.getValue(), rightMutableFloat.getValue(), leftMutableFloat, rightMutableFloat);
        } else {
            throw new ComparisonFailedException(String.format(
                    "At least one value is neither a double nor a float nor a primitive mutable float or double. Or both types are different"
                            + " (The type of leftValue is '%s' and the type of the right value is '%s')",
                    leftValue.getClass().getName(), rightValue.getClass().getName()), leftValue, rightValue);
        }
    }

    protected abstract ValueComparisonResult<Object> compareFloats(float leftFloat, 
                                                                   float rightFloat, 
                                                                   MutableFloat leftMutableFloat,
                                                                   MutableFloat rightMutableFloat) throws ComparisonFailedException;

    protected abstract ValueComparisonResult<Object> compareDouble(double leftDouble, 
                                                                   double rightDouble,
                                                                   MutableDouble leftMutableDouble,
                                                                   MutableDouble rightMutableDouble) throws ComparisonFailedException;

    ValueComparisonResult<Object> compareFloats(float leftFloat,
                                                       float rightFloat,
                                                       MutableFloat leftMutableFloat,
                                                       MutableFloat rightMutableFloat,
                                                       double absoluteEpsilon) throws ComparisonFailedException {
        final double absoluteDistance = Math.abs(leftFloat - rightFloat);
        final Pair<?> floatPair = createFloatPair(leftFloat, rightFloat, leftMutableFloat, rightMutableFloat);
        if (absoluteDistance <= absoluteEpsilon) {
            final ImmutableValueSimilarity<Object> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithPair(floatPair);
            return new ImmutableValueComparisonResult<>(true, valueSimilarity, false, null);
        } else {
            return new ImmutableValueComparisonResult(false, null, true,
                    floatPair);
        }
    }

    ValueComparisonResult<Object> compareDouble(double leftDouble,
                                                       double rightDouble,
                                                       MutableDouble leftMutableDouble,
                                                       MutableDouble rightMutableDouble,
                                                       double absoluteEpsilon) throws ComparisonFailedException {
        final double absoluteDistance = Math.abs(leftDouble - rightDouble);
        final Pair<?> doublePair = createDoublePair(leftDouble, rightDouble, leftMutableDouble, rightMutableDouble);
        if (absoluteDistance <= absoluteEpsilon) {
            final ImmutableValueSimilarity<Object> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithPair(doublePair);
            return new ImmutableValueComparisonResult<>(true, valueSimilarity, false, null);
        } else {
            return new ImmutableValueComparisonResult(false, null, true,
                    doublePair);
        }
    }
    
    Pair<?> createFloatPair(float leftFloat, 
                                   float rightFloat, 
                                   MutableFloat leftMutableFloat, 
                                   MutableFloat rightMutableFloat) throws ComparisonFailedException {
        Pair<?> doublePair;
        if (leftMutableFloat != null && rightMutableFloat != null) {
            try {
                doublePair = pairFactory.createPair(leftMutableFloat, rightMutableFloat);
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot create double pair!", e, leftMutableFloat, rightMutableFloat);
            }
        } else {
            try {
                doublePair = pairFactory.createPair(leftFloat, rightFloat);
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot create double pair!", e, leftFloat, rightFloat);
            }
        }
        return doublePair;
    }

    Pair<?> createDoublePair(double leftDouble, 
                             double rightDouble, 
                             MutableDouble leftMutableDouble, 
                             MutableDouble rightMutableDouble) throws ComparisonFailedException {
        Pair<?> doublePair;
        if (leftMutableDouble != null && rightMutableDouble != null) {
            try {
                doublePair = pairFactory.createPair(leftMutableDouble, rightMutableDouble);
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot create double pair!", e, leftMutableDouble, rightMutableDouble);
            }
        } else {
            try {
                doublePair = pairFactory.createPair(leftDouble, rightDouble);
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot create double pair!", e, leftDouble, rightDouble);
            }
        }
        return doublePair;
    }

}
