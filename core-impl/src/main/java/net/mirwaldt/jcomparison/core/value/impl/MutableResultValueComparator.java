package net.mirwaldt.jcomparison.core.value.impl;

import net.mirwaldt.jcomparison.core.array.impl.CheckingArrayWrapperDecorator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class MutableResultValueComparator implements ValueComparator<Object> {
    private final BiPredicate<Object, Object> comparisonPredicate;
    private final PairFactory pairFactory;
    private final Supplier<MutableValueComparisonResult> mutableValueComparisonResultSupplier;
    private final Function<Object, Object> arrayWrapperFunction;

    public MutableResultValueComparator(BiPredicate<Object, Object> comparisonPredicate,
                                        PairFactory pairFactory,
                                        Supplier<MutableValueComparisonResult> mutableValueComparisonResultSupplier,
                                        Function<Object, Object> arrayWrapperFunction) {
        this.comparisonPredicate = comparisonPredicate;
        this.pairFactory = pairFactory;
        this.mutableValueComparisonResultSupplier = mutableValueComparisonResultSupplier;
        this.arrayWrapperFunction = arrayWrapperFunction;
    }

    public void compareWithMutableComparisonResult(Object leftItem, Object rightItem, MutableValueComparisonResult mutableComparisonResult) throws ComparisonFailedException {
        mutableComparisonResult.reset();

        if (comparisonPredicate.test(leftItem, rightItem)) {
            mutableComparisonResult.setHasSimilarity(true);

            if (leftItem instanceof MutablePrimitive) {
                try {
                    final ImmutableValueSimilarity<Object> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithValue(((MutablePrimitive) leftItem).get());
                    mutableComparisonResult.setSimilarity(valueSimilarity);
                } catch (Exception e) {
                    throw new ComparisonFailedException("Cannot access primitive value.", e, leftItem, rightItem);
                }
            } else {

                final ImmutableValueSimilarity<Object> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithValue(arrayWrapperFunction.apply(leftItem));
                mutableComparisonResult.setSimilarity(valueSimilarity);
            }
        } else {
            mutableComparisonResult.setHasDifference(true);

            // create pair because it will likely be needed and used
            try {
                mutableComparisonResult.setDifference(pairFactory.createPair(arrayWrapperFunction.apply(leftItem), arrayWrapperFunction.apply(rightItem)));
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot create pair.", e, leftItem, rightItem);
            }
        }
    }

    @Override
    public ValueComparisonResult<Object> compare(Object leftItem, Object rightItem) throws ComparisonFailedException {
        MutableValueComparisonResult mutableValueComparisonResult;
        try {
            mutableValueComparisonResult = mutableValueComparisonResultSupplier.get();
            compareWithMutableComparisonResult(leftItem, rightItem, mutableValueComparisonResult);
            return mutableValueComparisonResult;
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot access mutable value comparison result.", e, leftItem, rightItem);
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
