package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.facade.EmptyComparisonResults;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

import java.util.Collections;
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
public class ImmutableMapComparisonResultFunction<KeyType, ValueType> implements Function<IntermediateMapComparisonResult<KeyType, ValueType>, MapComparisonResult<KeyType, ValueType>> {
    private final Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueType>> accessEntriesOnlyInLeftMap;
    private final Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueSimilarity<ValueType>>> accessSimilarValueEntries;
    private final Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, Pair<ValueType>>> accessDifferentValueEntries;
    private final Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ComparisonResult<?,?,?>>> accessComparedObjectsEntries;
    private final Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueType>> accessEntriesOnlyInRightMap;

    public ImmutableMapComparisonResultFunction(
            Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueType>> accessEntriesOnlyInLeftMap, 
            Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueSimilarity<ValueType>>> accessSimilarValueEntries, 
            Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, Pair<ValueType>>> accessDifferentValueEntries, 
            Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ComparisonResult<?,?,?>>> accessComparedObjectsEntries, 
            Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueType>> accessEntriesOnlyInRightMap) {
        this.accessEntriesOnlyInLeftMap = accessEntriesOnlyInLeftMap;
        this.accessSimilarValueEntries = accessSimilarValueEntries;
        this.accessDifferentValueEntries = accessDifferentValueEntries;
        this.accessComparedObjectsEntries = accessComparedObjectsEntries;
        this.accessEntriesOnlyInRightMap = accessEntriesOnlyInRightMap;
    }

    @Override
    public MapComparisonResult<KeyType, ValueType> apply(IntermediateMapComparisonResult<KeyType, ValueType> intermediateMapComparisonResult) {
        final boolean hasSimilarity = !intermediateMapComparisonResult.readSimilarValueEntries().isEmpty();
        final boolean hasDifference = !(intermediateMapComparisonResult.readEntriesOnlyInLeftMap().isEmpty() && intermediateMapComparisonResult.readDifferentValueEntries().isEmpty() && intermediateMapComparisonResult.readEntriesOnlyInRightMap().isEmpty());
        final boolean hasComparisons = !(intermediateMapComparisonResult.readComparedObjectEntries().isEmpty());

        if (hasSimilarity || hasDifference || hasComparisons) {
            final Map<KeyType, ValueType> entriesOnlyInLeftMap = accessEntriesOnlyInLeftMap.apply(intermediateMapComparisonResult);
            final Map<KeyType, ValueSimilarity<ValueType>> similarEntries = accessSimilarValueEntries.apply(intermediateMapComparisonResult);
            final Map<KeyType, Pair<ValueType>> differentValueEntries = accessDifferentValueEntries.apply(intermediateMapComparisonResult);
            final Map<KeyType, ComparisonResult<?,?,?>> comparedObjectsEntries = accessComparedObjectsEntries.apply(intermediateMapComparisonResult);
            final Map<KeyType, ValueType> entriesOnlyInRightMap = accessEntriesOnlyInRightMap.apply(intermediateMapComparisonResult);

            final Map<KeyType, ValueSimilarity<ValueType>> mapSimilarity = Collections.unmodifiableMap(similarEntries);
            final MapDifference<KeyType, ValueType> mapDifference = new ImmutableMapDifference<>(entriesOnlyInLeftMap, differentValueEntries, entriesOnlyInRightMap);

            return new ImmutableMapComparisonResult<>(hasSimilarity, mapSimilarity, hasDifference,
                    mapDifference, hasComparisons, comparedObjectsEntries);
        } else {
            return EmptyComparisonResults.emptyMapComparisonResult();
        }
    }
}

