package net.mirwaldt.jcomparison.core.collection.set.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparator;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
@NotNullSafe
public class DefaultSetComparator<ValueType> implements SetComparator<ValueType> {

    private final Supplier<IntermediateSetComparisonResult<ValueType>> intermediateResultField;
    private final Function<IntermediateSetComparisonResult<ValueType>, SetComparisonResult<ValueType>> resultFunction;

    private final EnumSet<ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateSetComparisonResult<ValueType>> stopPredicate;
    private final Predicate<ValueType> elementsFilter;

    public DefaultSetComparator(Supplier<IntermediateSetComparisonResult<ValueType>> intermediateResultField, Function<IntermediateSetComparisonResult<ValueType>, SetComparisonResult<ValueType>> resultFunction, EnumSet<ComparisonFeature> comparisonFeatures, Predicate<IntermediateSetComparisonResult<ValueType>> stopPredicate, Predicate<ValueType> elementsFilter) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;
        this.elementsFilter = elementsFilter;
    }

    @Override
    public SetComparisonResult<ValueType> compare(Set<ValueType> leftSet, 
                                                  Set<ValueType> rightSet, 
                                                  VisitedObjectsTrace visitedObjectsTrace,
                                                  ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        return compare(leftSet, rightSet);
    }

    @Override
    public SetComparisonResult<ValueType> compare(Set<ValueType> leftSet, Set<ValueType> rightSet) throws ComparisonFailedException {
        try {
            final IntermediateSetComparisonResult<ValueType> intermediateResult = intermediateResultField.get();

            if (!leftSet.isEmpty() || !rightSet.isEmpty()) {
                findSimilaritiesAndDifferences(leftSet, rightSet, intermediateResult);
            }

            return resultFunction.apply(intermediateResult);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both sets.", e, leftSet, rightSet);
        }
    }

    private void findSimilaritiesAndDifferences(Set<ValueType> leftSet, Set<ValueType> rightSet, IntermediateSetComparisonResult<ValueType> intermediateResult) throws Exception {
        final Set<ValueType> bigLoopSet;
        final Set<ValueType> smallLoopSet;

        final Supplier<Set<ValueType>> bigLoopResultSetSupplier;
        final Supplier<Set<ValueType>> smallLoopResultSetSupplier;

        if (leftSet.size() <= rightSet.size()) {
            bigLoopSet = leftSet;
            bigLoopResultSetSupplier = intermediateResult::writeElementsOnlyInLeftSet;

            smallLoopSet = rightSet;
            smallLoopResultSetSupplier = intermediateResult::writeElementsOnlyInRightSet;
        } else {
            bigLoopSet = rightSet;
            bigLoopResultSetSupplier = intermediateResult::writeElementsOnlyInRightSet;

            smallLoopSet = leftSet;
            smallLoopResultSetSupplier = intermediateResult::writeElementsOnlyInLeftSet;
        }

        if (iterateBig(intermediateResult, bigLoopSet, smallLoopSet, bigLoopResultSetSupplier, leftSet)) {
            return;
        }

        if(hasComparisonFeature(smallLoopSet == leftSet)) {
            iterateSmall(intermediateResult, bigLoopSet, smallLoopSet, smallLoopResultSetSupplier, leftSet);
        }
    }

    private void iterateSmall(IntermediateSetComparisonResult<ValueType> intermediateResult, 
                              Set<ValueType> bigLoopSet, 
                              Set<ValueType> smallLoopSet, 
                              Supplier<Set<ValueType>> smallLoopResultSetSupplier, 
                              Set<ValueType> leftSet) throws Exception {
        for (ValueType element : smallLoopSet) {
            if (elementsFilter.test(element) && !bigLoopSet.contains(element)) {
                smallLoopResultSetSupplier.get().add(element);

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }
        }
    }

    private boolean iterateBig(IntermediateSetComparisonResult<ValueType> intermediateResult, 
                               Set<ValueType> bigLoopSet, 
                               Set<ValueType> smallLoopSet, 
                               Supplier<Set<ValueType>> bigLoopResultSetSupplier, 
                               Set<ValueType> leftSet) throws Exception {
        for (ValueType element : bigLoopSet) {
            if (elementsFilter.test(element)) {
                if (smallLoopSet.contains(element)) {
                    if(comparisonFeatures.contains(ComparisonFeature.ELEMENTS_IN_BOTH_SETS)) {
                        intermediateResult.writeElementsInBothSets().add(element);

                        if(stopPredicate.test(intermediateResult)) {
                            return true;
                        }
                    }
                } else {
                    if(hasComparisonFeature(bigLoopSet == leftSet)) {
                        bigLoopResultSetSupplier.get().add(element);

                        if(stopPredicate.test(intermediateResult)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean hasComparisonFeature(boolean isLeft) {
        if (isLeft) {
            return comparisonFeatures.contains(ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_SET);
        } else {
            return comparisonFeatures.contains(ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_SET);
        }
    }
}
