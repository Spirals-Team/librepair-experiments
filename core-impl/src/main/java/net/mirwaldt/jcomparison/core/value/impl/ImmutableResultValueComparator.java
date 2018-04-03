package net.mirwaldt.jcomparison.core.value.impl;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

import java.util.function.BiPredicate;
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
public class ImmutableResultValueComparator implements ValueComparator<Object> {
    private final BiPredicate<Object, Object> comparisonPredicate;
    private final PairFactory pairFactory;
    private final Function<Object, Object> arrayWrapperFunction;

    public ImmutableResultValueComparator(BiPredicate<Object, Object> comparisonPredicate, 
                                          PairFactory pairFactory, 
                                          Function<Object, Object> arrayWrapperFunction) {
        this.comparisonPredicate = comparisonPredicate;
        this.pairFactory = pairFactory;
        this.arrayWrapperFunction = arrayWrapperFunction;
    }

    @Override
    public ValueComparisonResult<Object> compare(Object leftItem, Object rightItem) throws ComparisonFailedException {
        if (comparisonPredicate.test(leftItem, rightItem)) {
            final Object similarItem;
            if(leftItem instanceof MutablePrimitive<?>) {
                MutablePrimitive<?> mutablePrimitive = (MutablePrimitive<?>) leftItem;
                similarItem = mutablePrimitive.get();
            } else {
                similarItem = leftItem;
            }
            final ImmutableValueSimilarity<Object> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithValue(similarItem);
            return new ImmutableValueComparisonResult<>(true, valueSimilarity, false, null);
        } else {
            try {
                return new ImmutableValueComparisonResult(false, null, true,
                        pairFactory.createPair(arrayWrapperFunction.apply(leftItem), arrayWrapperFunction.apply(rightItem)));
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot compare both items.", e, leftItem, rightItem);
            }
        }
    }

    @Override
    public ValueComparisonResult<Object> compare(Object leftItem, 
                                           Object rightItem, 
                                           VisitedObjectsTrace visitedObjectsTrace, 
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        return compare(leftItem, rightItem);
    }

}
