package net.mirwaldt.jcomparison.core.collection.list.uniques.impl;

import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListDifference;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static net.mirwaldt.jcomparison.core.util.UnmodifiableChecker.isUnmodifiable;

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
public class ImmutableUniquesListComparisonResultFunction<ValueType> implements Function<IntermediateUniquesListComparisonResult<ValueType>, UniquesListComparisonResult<ValueType>> {
    private final Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsOnlyInLeftList;
    private final Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsInBothListsAtTheSameIndex;
    private final Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessElementsMovedBetweenLists;
    private final Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsOnlyInRightList;

    public ImmutableUniquesListComparisonResultFunction(
            Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsOnlyInLeftList,
            Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsInBothListsAtTheSameIndex,
            Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessElementsMovedBetweenLists,
            Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsOnlyInRightList) {
        this.accessElementsOnlyInLeftList = accessElementsOnlyInLeftList;
        this.accessElementsInBothListsAtTheSameIndex = accessElementsInBothListsAtTheSameIndex;
        this.accessElementsMovedBetweenLists = accessElementsMovedBetweenLists;
        this.accessElementsOnlyInRightList = accessElementsOnlyInRightList;
    }

    @Override
    public UniquesListComparisonResult<ValueType> apply(IntermediateUniquesListComparisonResult<ValueType> intermediateUniquesListComparisonResult) {
        final boolean hasSimilarity = !intermediateUniquesListComparisonResult.readElementsInBothListsAtTheSameIndex().isEmpty();
        final boolean hasDifference = !(intermediateUniquesListComparisonResult.readElementsOnlyInLeftList().isEmpty() && intermediateUniquesListComparisonResult.readElementsOnlyInRightList().isEmpty() && intermediateUniquesListComparisonResult.readElementsMovedBetweenLists().isEmpty());

        if(hasSimilarity || hasDifference) {
            final Map<ValueType, Integer> elementsOnlyInLeftList = accessElementsOnlyInLeftList.apply(intermediateUniquesListComparisonResult);
            final Map<ValueType, Integer> elementsInBothListsAtTheSameIndex = accessElementsInBothListsAtTheSameIndex.apply(intermediateUniquesListComparisonResult);
            final Map<ValueType, Pair<Integer>> elementsMovedBetweenLists = accessElementsMovedBetweenLists.apply(intermediateUniquesListComparisonResult);
            final Map<ValueType, Integer> elementsOnlyInRightList = accessElementsOnlyInRightList.apply(intermediateUniquesListComparisonResult);

            final Map<ValueType, Integer> similarity;
            if(isUnmodifiable(elementsInBothListsAtTheSameIndex)) {
                similarity = elementsInBothListsAtTheSameIndex;
            } else {
                similarity = Collections.unmodifiableMap(elementsInBothListsAtTheSameIndex);
            }
            final UniquesListDifference<ValueType> difference = new ImmutableUniquesListDifference<>(elementsOnlyInLeftList, elementsMovedBetweenLists, elementsOnlyInRightList);

            return new ImmutableUniquesListComparisonResult<>(hasSimilarity, similarity,  hasDifference, difference);
        } else {
            return ComparisonResults.emptyUniquesListComparisonResult();
        }
    }
}
