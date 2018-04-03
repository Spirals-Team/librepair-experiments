package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
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
public class ImmutableArrayComparisonResultBiFunction implements BiFunction<IntermediateArrayComparisonResult, Integer, ArrayComparisonResult> {
    private final Function<IntermediateArrayComparisonResult, Map<List<Integer>, ?>> accessAdditionalElementsOnlyInLeftArray;
    private final Function<IntermediateArrayComparisonResult, Map<List<Integer>, ?>> accessAdditionalElementsOnlyInRightArray;
    private final Function<IntermediateArrayComparisonResult, Map<List<Integer>, ValueSimilarity<?>>> accessSimilarElements;
    private final Function<IntermediateArrayComparisonResult, Map<List<Integer>, Pair<Object>>> accessDifferentValues;
    private final Function<IntermediateArrayComparisonResult, Map<List<Integer>, ComparisonResult<?,?,?>>> accessComparisonResults;

    public ImmutableArrayComparisonResultBiFunction(
            Function<IntermediateArrayComparisonResult, Map<List<Integer>, ?>> accessAdditionalElementsOnlyInLeftArray,
            Function<IntermediateArrayComparisonResult, Map<List<Integer>, ?>> accessAdditionalElementsOnlyInRightArray,
            Function<IntermediateArrayComparisonResult, Map<List<Integer>, ValueSimilarity<?>>> accessSimilarElements,
            Function<IntermediateArrayComparisonResult, Map<List<Integer>, Pair<Object>>> accessDifferentValues,
            Function<IntermediateArrayComparisonResult, Map<List<Integer>, ComparisonResult<?,?,?>>> accessComparisonResults) {
        this.accessAdditionalElementsOnlyInLeftArray = accessAdditionalElementsOnlyInLeftArray;
        this.accessAdditionalElementsOnlyInRightArray = accessAdditionalElementsOnlyInRightArray;
        this.accessSimilarElements = accessSimilarElements;
        this.accessDifferentValues = accessDifferentValues;
        this.accessComparisonResults = accessComparisonResults;
    }

    @Override
    public ArrayComparisonResult apply(IntermediateArrayComparisonResult intermediateArrayComparisonResult, Integer arrayDimension) {
        final boolean hasSimilarity = !intermediateArrayComparisonResult.readSimilarElements().isEmpty();
        final boolean hasDifference = !(intermediateArrayComparisonResult.readAdditionalElementsOnlyInLeftArray().isEmpty() && intermediateArrayComparisonResult.readAdditionalElementsOnlyInRightArray().isEmpty() && intermediateArrayComparisonResult.readDifferentValues().isEmpty());
        final boolean hasComparisonResults = !intermediateArrayComparisonResult.readComparisonResults().isEmpty();

        if (hasSimilarity || hasDifference || hasComparisonResults) {
            final Map additionalElementsOnlyInLeftArray = accessAdditionalElementsOnlyInLeftArray.apply(intermediateArrayComparisonResult);
            final Map additionalElementsOnlyInRightArray = accessAdditionalElementsOnlyInRightArray.apply(intermediateArrayComparisonResult);
            final Map similarElements = accessSimilarElements.apply(intermediateArrayComparisonResult);
            final Map differentValues = accessDifferentValues.apply(intermediateArrayComparisonResult);
            final Map comparisonResults = accessComparisonResults.apply(intermediateArrayComparisonResult);

            final ArrayDifference arrayDifference = new ImmutableArrayDifference(additionalElementsOnlyInLeftArray, additionalElementsOnlyInRightArray, differentValues);
            return new ImmutableArrayComparisonResult(hasSimilarity, similarElements, hasDifference, arrayDifference, hasComparisonResults, comparisonResults);
        } else {
            return BasicComparisonResults.emptyArrayComparisonResult();
        }
    }
}
