package net.mirwaldt.jcomparison.core.collection.list.duplicates.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListDifference;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListSimilarity;

import java.util.Map;
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
public class ImmutableDuplicatesListComparisonResultFunction<ValueType> implements Function<IntermediateDuplicatesListComparisonResult<ValueType>, DuplicatesListComparisonResult<ValueType>> {
    private final Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessAdditionalElementsOnlyInLeftList;
    private final Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessSimilarValues;
    private final Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, Pair<ValueType>>> accessDifferentValues;
    private final Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ComparisonResult<?,?,?>>> accessComparedElements;
    private final Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessAdditionalElementsOnlyInRightList;

    private final Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessSimilarFrequencies;
    private final Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessDifferentFrequencies;

    public ImmutableDuplicatesListComparisonResultFunction(Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessAdditionalElementsOnlyInLeftList, Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessSimilarValues, Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, Pair<ValueType>>> accessDifferentValues, Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ComparisonResult<?,?,?>>> accessComparedElements, Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessAdditionalElementsOnlyInRightList, Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessSimilarFrequencies, Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessDifferentFrequencies) {
        this.accessAdditionalElementsOnlyInLeftList = accessAdditionalElementsOnlyInLeftList;
        this.accessSimilarValues = accessSimilarValues;
        this.accessDifferentValues = accessDifferentValues;
        this.accessComparedElements = accessComparedElements;
        this.accessAdditionalElementsOnlyInRightList = accessAdditionalElementsOnlyInRightList;
        this.accessSimilarFrequencies = accessSimilarFrequencies;
        this.accessDifferentFrequencies = accessDifferentFrequencies;
    }

    @Override
    public DuplicatesListComparisonResult<ValueType> apply(IntermediateDuplicatesListComparisonResult<ValueType> intermediateDuplicatesListComparisonResult) {
        final boolean hasSimilarity = !(intermediateDuplicatesListComparisonResult.readSimilarValues().isEmpty() && intermediateDuplicatesListComparisonResult.readSimilarFrequencies().isEmpty());
        final boolean hasDifference = !(intermediateDuplicatesListComparisonResult.readAdditionalElementsOnlyInLeftList().isEmpty() && intermediateDuplicatesListComparisonResult.readDifferentValues().isEmpty() && intermediateDuplicatesListComparisonResult.readDifferentFrequencies().isEmpty() && intermediateDuplicatesListComparisonResult.readAdditionalElementsOnlyInRightList().isEmpty());
        final boolean hasComparedObjects = !intermediateDuplicatesListComparisonResult.readComparedElements().isEmpty();

        if (hasSimilarity || hasDifference || hasComparedObjects) {
            final Map<Integer, ValueType> additionalElementsOnlyInLeftList = accessAdditionalElementsOnlyInLeftList.apply(intermediateDuplicatesListComparisonResult);
            final Map<Integer, ValueType> similarValues = accessSimilarValues.apply(intermediateDuplicatesListComparisonResult);
            final Map<Integer, Pair<ValueType>> differentValues = accessDifferentValues.apply(intermediateDuplicatesListComparisonResult);
            final Map<Integer, ComparisonResult<?,?,?>> comparedElements = accessComparedElements.apply(intermediateDuplicatesListComparisonResult);
            final Map<Integer, ValueType> additionalElementsOnlyInRightList = accessAdditionalElementsOnlyInRightList.apply(intermediateDuplicatesListComparisonResult);

            final Map<ValueType, Integer> similarFrequencies = accessSimilarFrequencies.apply(intermediateDuplicatesListComparisonResult);
            final Map<ValueType, Pair<Integer>> differentFrequencies = accessDifferentFrequencies.apply(intermediateDuplicatesListComparisonResult);

            final DuplicatesListSimilarity<ValueType> similarity = new ImmutableDuplicatesListSimilarity<>(similarValues, similarFrequencies);
            final DuplicatesListDifference<ValueType> difference = new ImmutableDuplicatesListDifference<>(additionalElementsOnlyInLeftList, differentValues, additionalElementsOnlyInRightList, differentFrequencies);

            return new ImmutableDuplicatesListComparisonResult<>(hasSimilarity, similarity, hasDifference, difference, hasComparedObjects, comparedElements);
        } else {
            return ComparisonResults.emptyDuplicatesListComparisonResult();
        }
    }
}
