package net.mirwaldt.jcomparison.core.collection.list.uniques.impl;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.fields.api.SafeSupplier;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
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
public class DefaultUniquesListComparator<ValueType> implements UniquesListComparator<ValueType> {

    private final Supplier<IntermediateUniquesListComparisonResult<ValueType>> intermediateResultField;
    private final Function<IntermediateUniquesListComparisonResult<ValueType>, UniquesListComparisonResult<ValueType>> resultFunction;

    private final Predicate<ValueType> elementFilter;
    private final EnumSet<UniquesListComparator.ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateUniquesListComparisonResult<ValueType>> stopPredicate;

    public DefaultUniquesListComparator(Supplier<IntermediateUniquesListComparisonResult<ValueType>> intermediateResultField, Function<IntermediateUniquesListComparisonResult<ValueType>, UniquesListComparisonResult<ValueType>> resultFunction, Predicate<ValueType> elementFilter, EnumSet<ComparisonFeature> comparisonFeatures, Predicate<IntermediateUniquesListComparisonResult<ValueType>> stopPredicate) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.elementFilter = elementFilter;
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;
    }

    @Override
    public UniquesListComparisonResult<ValueType> compare(List<ValueType> leftList, List<ValueType> rightList, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
        return compare(leftList, rightList);
    }

    @Override
    public UniquesListComparisonResult<ValueType> compare(List<ValueType> leftList, List<ValueType> rightList) throws ComparisonFailedException {
        try {
            final IntermediateUniquesListComparisonResult<ValueType> intermediateResult = intermediateResultField.get();

            if (!leftList.isEmpty() || !rightList.isEmpty()) {
                compare(leftList, rightList, intermediateResult);
            }

            final UniquesListComparisonResult<ValueType> uniquesListComparisonResult = resultFunction.apply(intermediateResult);

            return uniquesListComparisonResult;
        } catch (InvalidSupplyException e) {
            throw new ComparisonFailedException("Invalid supply of a new set.", e, leftList, rightList);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both lists.", e, leftList, rightList);
        }
    }

    private void compare(List<ValueType> leftList, List<ValueType> rightList, IntermediateUniquesListComparisonResult<ValueType> intermediateResult) throws Exception {
        final List<ValueType> bigLoopList;
        final List<ValueType> smallLoopList;

        final SafeSupplier<Map<ValueType, Integer>> bigLoopResultListSupplier;
        final SafeSupplier<Map<ValueType, Integer>> smallLoopResultListSupplier;

        if (leftList.size() <= rightList.size()) {
            bigLoopList = leftList;
            bigLoopResultListSupplier = intermediateResult::writeElementsOnlyInLeftList;

            smallLoopList = rightList;
            smallLoopResultListSupplier = intermediateResult::writeElementsOnlyInRightList;
        } else {
            bigLoopList = rightList;
            bigLoopResultListSupplier = intermediateResult::writeElementsOnlyInRightList;

            smallLoopList = leftList;
            smallLoopResultListSupplier = intermediateResult::writeElementsOnlyInLeftList;
        }

        if (iterateBig(intermediateResult, bigLoopList, bigLoopResultListSupplier, smallLoopList, leftList, rightList)) {
            return;
        }

        if (hasComparisonFeature(smallLoopList == leftList)) {
            iterateSmall(intermediateResult, bigLoopList, smallLoopList, smallLoopResultListSupplier, leftList, rightList);
        }
    }

    private boolean iterateBig(IntermediateUniquesListComparisonResult<ValueType> intermediateResult, List<ValueType> bigLoopList, SafeSupplier<Map<ValueType, Integer>> bigLoopResultListSupplier, List<ValueType> smallLoopList, List<ValueType> leftList, List<ValueType> rightList) throws Exception {
        int index = 0;
        for (ValueType value : bigLoopList) {
            if (elementFilter.test(value)) {
                final int indexInSmallLoopList = smallLoopList.indexOf(value);
                if (indexInSmallLoopList == -1) {
                    if (hasComparisonFeature(bigLoopList == leftList)) {
                        final Integer previousIndex = bigLoopResultListSupplier.get().put(value, index);
                        checkIndexOfValue(index, previousIndex, (bigLoopList == leftList) ? "left" : "right", leftList, rightList);

                        if(stopPredicate.test(intermediateResult)) {
                            return true;
                        }
                    }
                } else if (indexInSmallLoopList == index) {
                    if (comparisonFeatures.contains(ComparisonFeature.ELEMENTS_IN_BOTH_LISTS_AT_THE_SAME_INDEX)) {
                        final Integer previousIndex = intermediateResult.writeElementsInBothListsAtTheSameIndex().put(value, index);
                        checkIndexOfValue(index, previousIndex, (bigLoopList == leftList) ? "left" : "right", leftList, rightList);

                        if(stopPredicate.test(intermediateResult)) {
                            return true;
                        }
                    }
                } else {
                    if (comparisonFeatures.contains(ComparisonFeature.ELEMENTS_MOVED_BETWEEN_LISTS)) {
                        final ImmutableIntPair pair;
                        if (bigLoopList == leftList) {
                            pair = new ImmutableIntPair(index, indexInSmallLoopList);
                        } else {
                            pair = new ImmutableIntPair(indexInSmallLoopList, index);
                        }

                        final Pair<Integer> previousIndexPair = intermediateResult.writeElementsMovedBetweenLists().put(value, pair);
                        if (previousIndexPair != null) {
                            throw new ComparisonFailedException("Found duplicate at index "
                                    + ((bigLoopList == leftList) ? previousIndexPair.getLeft() : previousIndexPair.getRight()) + " and at index " + index + " in " + ((bigLoopList == leftList) ? "left" : "right") + " list.", leftList, rightList);
                        }

                        if(stopPredicate.test(intermediateResult)) {
                            return true;
                        }
                    }
                }
            }
            index++;
        }
        return false;
    }

    private void iterateSmall(IntermediateUniquesListComparisonResult<ValueType> intermediateResult, List<ValueType> bigLoopList, List<ValueType> smallLoopList, SafeSupplier<Map<ValueType, Integer>> smallLoopResultListSupplier, List<ValueType> leftList, List<ValueType> rightList) throws Exception {
        int index = 0;
        for (ValueType value : smallLoopList) {
            if (elementFilter.test(value) && !bigLoopList.contains(value)) {
                final Integer previousIndex = smallLoopResultListSupplier.get().put(value, index);
                checkIndexOfValue(index, previousIndex, (smallLoopList == leftList) ? "left" : "right", leftList, rightList);

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }
            index++;
        }
    }

    private void checkIndexOfValue(int index, final Integer otherIndex, String listName, List<ValueType> leftList, List<ValueType> rightList)
            throws ComparisonFailedException {
        if (otherIndex != null) {
            throw new ComparisonFailedException("Found duplicate at index "
                    + otherIndex + " and at index " + index + " in " + listName + " list.", leftList, rightList);
        }
    }

    private boolean hasComparisonFeature(boolean isLeft) {
        if (isLeft) {
            return comparisonFeatures.contains(ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST);
        } else {
            return comparisonFeatures.contains(ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST);
        }
    }
}
