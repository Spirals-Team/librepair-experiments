package net.mirwaldt.jcomparison.core.collection.list.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.api.ListDifference;
import net.mirwaldt.jcomparison.core.facade.EmptyComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.api.ListSimilarity;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

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
public class ImmutableListComparisonResultFunction<ValueType> implements Function<IntermediateListComparisonResult<ValueType>, ListComparisonResult<ValueType>> {
    private final Function<IntermediateListComparisonResult<ValueType>, Map<Integer, ValueSimilarity<ValueType>>> accessSimilarValues;
    private final Function<IntermediateListComparisonResult<ValueType>, Map<Integer, Pair<ValueType>>> accessDifferentValues;
    private final Function<IntermediateListComparisonResult<ValueType>, Map<Integer, ComparisonResult<?,?,?>>> accessComparedElements;

    private final Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Integer>> accessSimilarFrequencies;
    private final Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessDifferentFrequencies;

    private final Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessSimilarPositions;
    private final Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Pair<ImmutableIntList>>> accessDifferentPositions;

    private final Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessElementsOnlyInLeftList;
    private final Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessElementsOnlyInRightList;

    public ImmutableListComparisonResultFunction(
            Function<IntermediateListComparisonResult<ValueType>, Map<Integer, ValueSimilarity<ValueType>>> accessSimilarValues, 
            Function<IntermediateListComparisonResult<ValueType>, Map<Integer, Pair<ValueType>>> accessDifferentValues, 
            Function<IntermediateListComparisonResult<ValueType>, Map<Integer, ComparisonResult<?,?,?>>> accessComparedElements,  
            Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Integer>> accessSimilarFrequencies, 
            Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessDifferentFrequencies,
            Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessSimilarPositions,
            Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Pair<ImmutableIntList>>> accessDifferentPositions,
            Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessElementsOnlyInLeftList,
            Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessElementsOnlyInRightList
    ) {
        this.accessSimilarValues = accessSimilarValues;
        this.accessDifferentValues = accessDifferentValues;
        this.accessComparedElements = accessComparedElements;
        this.accessSimilarFrequencies = accessSimilarFrequencies;
        this.accessDifferentFrequencies = accessDifferentFrequencies;
        this.accessSimilarPositions = accessSimilarPositions;
        this.accessDifferentPositions = accessDifferentPositions;
        this.accessElementsOnlyInLeftList = accessElementsOnlyInLeftList;
        this.accessElementsOnlyInRightList = accessElementsOnlyInRightList;
    }

    @Override
    public ListComparisonResult<ValueType> apply(IntermediateListComparisonResult<ValueType> intermediateListComparisonResult) {
        final boolean hasSimilarity = 
                !(intermediateListComparisonResult.readSimilarValues().isEmpty() 
                && intermediateListComparisonResult.readSimilarFrequencies().isEmpty()
                && intermediateListComparisonResult.readSimilarPositions().isEmpty());
        final boolean hasDifference = 
                !(intermediateListComparisonResult.readDifferentValues().isEmpty() 
                && intermediateListComparisonResult.readDifferentFrequencies().isEmpty() 
                && intermediateListComparisonResult.readDifferentPositions().isEmpty()
                && intermediateListComparisonResult.readElementsOnlyInLeftList().isEmpty()
                && intermediateListComparisonResult.readElementsOnlyInRightList().isEmpty());
        final boolean hasComparedObjects = !intermediateListComparisonResult.readComparedElements().isEmpty();

        if (hasSimilarity || hasDifference || hasComparedObjects) {
            final Map<Integer, ValueSimilarity<ValueType>> similarValues = accessSimilarValues.apply(intermediateListComparisonResult);
            final Map<Integer, Pair<ValueType>> differentValues = accessDifferentValues.apply(intermediateListComparisonResult);
            final Map<Integer, ComparisonResult<?,?,?>> comparedElements = accessComparedElements.apply(intermediateListComparisonResult);
  
            final Map<ValueType, Integer> similarFrequencies = accessSimilarFrequencies.apply(intermediateListComparisonResult);
            final Map<ValueType, Pair<Integer>> differentFrequencies = accessDifferentFrequencies.apply(intermediateListComparisonResult);

            final Map<ValueType, ImmutableIntList> similarPositions = accessSimilarPositions.apply(intermediateListComparisonResult);
            final Map<ValueType, Pair<ImmutableIntList>> differentPositions = accessDifferentPositions.apply(intermediateListComparisonResult);
            final Map<ValueType, ImmutableIntList> elementsOnlyInLeftList = accessElementsOnlyInLeftList.apply(intermediateListComparisonResult);
            final Map<ValueType, ImmutableIntList> elementsOnlyInRightList = accessElementsOnlyInRightList.apply(intermediateListComparisonResult);
                    
            final ListSimilarity<ValueType> similarity = new ImmutableListSimilarity<>(similarValues, similarFrequencies, similarPositions);
            final ListDifference<ValueType> difference = new ImmutableListDifference<>(
                    differentValues, 
                    differentFrequencies,
                    differentPositions,
                    elementsOnlyInLeftList,
                    elementsOnlyInRightList);

            return new ImmutableListComparisonResult<>(hasSimilarity, similarity, hasDifference, difference, hasComparedObjects, comparedElements);
        } else {
            return EmptyComparisonResults.emptyListComparisonResult();
        }
    }
}
