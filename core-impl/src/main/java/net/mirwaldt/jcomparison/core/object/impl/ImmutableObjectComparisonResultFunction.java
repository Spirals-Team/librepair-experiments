package net.mirwaldt.jcomparison.core.object.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;

import java.lang.reflect.Field;
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
public class ImmutableObjectComparisonResultFunction implements Function<IntermediateObjectComparisonResult, ImmutableObjectComparisonResult> {
    private final Function<IntermediateObjectComparisonResult, Map<Field, Object>> accessSimilarValuesOfFields;
    private final Function<IntermediateObjectComparisonResult, Map<Field, Pair<Object>>> accessDifferentValuesOfFields;
    private final Function<IntermediateObjectComparisonResult, Map<Field, ComparisonResult<?,?,?>>> accessComparisonResultOfFields;

    public ImmutableObjectComparisonResultFunction(Function<IntermediateObjectComparisonResult, Map<Field, Object>> accessSimilarValuesOfFields, Function<IntermediateObjectComparisonResult, Map<Field, Pair<Object>>> accessDifferentValuesOfFields, Function<IntermediateObjectComparisonResult, Map<Field, ComparisonResult<?,?,?>>> accessComparisonResultOfFields) {
        this.accessSimilarValuesOfFields = accessSimilarValuesOfFields;
        this.accessDifferentValuesOfFields = accessDifferentValuesOfFields;
        this.accessComparisonResultOfFields = accessComparisonResultOfFields;
    }

    @Override
    public ImmutableObjectComparisonResult apply(IntermediateObjectComparisonResult intermediateObjectComparisonResult) {
        final boolean hasSimilarity = !intermediateObjectComparisonResult.readSimilarValuesOfFields().isEmpty();
        final boolean hasDifference = !intermediateObjectComparisonResult.readDifferentValuesOfFields().isEmpty();
        final boolean hasComparisons = !intermediateObjectComparisonResult.readComparisonResultOfFields().isEmpty();

        if (hasSimilarity || hasDifference || hasComparisons) {
            final Map<Field, Object> similarValuesOfFields = accessSimilarValuesOfFields.apply(intermediateObjectComparisonResult);
            final Map<Field, Pair<Object>> differentValuesOfFields = accessDifferentValuesOfFields.apply(intermediateObjectComparisonResult);
            final Map<Field, ComparisonResult<?,?,?>> comparisonResultOfFields = accessComparisonResultOfFields.apply(intermediateObjectComparisonResult);

            final Map<Field, Object> objectSimilarity;
            if(isUnmodifiable(similarValuesOfFields)) {
                objectSimilarity = similarValuesOfFields;
            } else {
                objectSimilarity = Collections.unmodifiableMap(similarValuesOfFields);
            }

            final Map<Field, Pair<Object>> objectDifference;
            if(isUnmodifiable(differentValuesOfFields)) {
                objectDifference = differentValuesOfFields;
            } else {
                objectDifference = Collections.unmodifiableMap(differentValuesOfFields);
            }

            final Map<Field, ComparisonResult<?,?,?>> comparisonResults;
            if(isUnmodifiable(comparisonResultOfFields)) {
                comparisonResults = comparisonResultOfFields;
            } else {
                comparisonResults = Collections.unmodifiableMap(comparisonResultOfFields);
            }

            return new ImmutableObjectComparisonResult(hasSimilarity, objectSimilarity, hasDifference,
                    objectDifference, hasComparisons, comparisonResults);
        } else {
            // TODO: replace by a null-object
            return new ImmutableObjectComparisonResult(false, null, false, null, false, null);
        }
    }
}
