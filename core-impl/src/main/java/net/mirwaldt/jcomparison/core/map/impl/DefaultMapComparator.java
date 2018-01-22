package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.fields.api.SafeSupplier;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.map.api.MapComparator;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;

import java.util.EnumSet;
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
@NotNullSafe
public class DefaultMapComparator<KeyType, ValueType> implements MapComparator<KeyType, ValueType> {

    private final Supplier<IntermediateMapComparisonResult<KeyType, ValueType>> intermediateResultField;
    private final Function<IntermediateMapComparisonResult<KeyType, ValueType>, MapComparisonResult<KeyType, ValueType>> resultFunction;

    private final ItemComparator<ValueType,? extends ComparisonResult<?,?,?>> comparator;
    private final ComparisonFailedExceptionHandlingComparator exceptionHandlingComparator;

    private final EnumSet<ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateMapComparisonResult<KeyType, ValueType>> stopPredicate;
    private final Predicate<Map.Entry<KeyType, ValueType>> entryFilter;

    public DefaultMapComparator(Supplier<IntermediateMapComparisonResult<KeyType, ValueType>> intermediateResultField, Function<IntermediateMapComparisonResult<KeyType, ValueType>, MapComparisonResult<KeyType, ValueType>> resultFunction, ItemComparator<ValueType,? extends ComparisonResult<?,?,?>> comparator, ComparisonFailedExceptionHandler comparisonFailedExceptionHandler, EnumSet<ComparisonFeature> comparisonFeatures, Predicate<IntermediateMapComparisonResult<KeyType, ValueType>> stopPredicate, Predicate<Map.Entry<KeyType, ValueType>> entryFilter) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparator = comparator;
        this.exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator(comparisonFailedExceptionHandler);
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;
        this.entryFilter = entryFilter;
    }

    private Supplier<ComparisonResult<?, ?, ?>> createResultSupplier(IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult) {
        return () -> resultFunction.apply(intermediateResult);
    }

    @Override
    public MapComparisonResult<KeyType, ValueType> compare(Map<KeyType, ValueType> leftMap,
                                                           Map<KeyType, ValueType> rightMap, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
        try {
            final IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult = intermediateResultField.get();

            if (!leftMap.isEmpty() || !rightMap.isEmpty()) {
                findSimilaritiesAndDifferences(leftMap, rightMap, entryFilter, intermediateResult, visitedObjectsTrace);
            }

            return resultFunction.apply(intermediateResult);
        } catch (InvalidSupplyException e) {
            throw new ComparisonFailedException("Invalid supply of a new set.", e, leftMap, rightMap);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both maps.", e, leftMap, rightMap);
        }
    }

    private void findSimilaritiesAndDifferences(Map<KeyType, ValueType> leftMap, Map<KeyType, ValueType> rightMap,
                                                Predicate<Map.Entry<KeyType, ValueType>> entryFilter, IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult, VisitedObjectsTrace visitedObjectsTrace) throws Exception {
        final Map<KeyType, ValueType> bigLoopMap;
        final SafeSupplier<Map<KeyType, ValueType>> bigLoopResultMapSupplier;

        final Map<KeyType, ValueType> smallLoopMap;
        final SafeSupplier<Map<KeyType, ValueType>> smallLoopResultMapSupplier;

        if (leftMap.size() <= rightMap.size()) {
            bigLoopMap = leftMap;
            bigLoopResultMapSupplier = intermediateResult::writeEntriesOnlyInLeftMap;

            smallLoopMap = rightMap;
            smallLoopResultMapSupplier = intermediateResult::writeEntriesOnlyInRightMap;
        } else {
            bigLoopMap = rightMap;
            bigLoopResultMapSupplier = intermediateResult::writeEntriesOnlyInRightMap;

            smallLoopMap = leftMap;
            smallLoopResultMapSupplier = intermediateResult::writeEntriesOnlyInLeftMap;
        }

        if (iterateBig(leftMap, intermediateResult, bigLoopMap, smallLoopMap, bigLoopResultMapSupplier, visitedObjectsTrace))
            return;

        if(hasComparisonFeature(smallLoopMap == leftMap)) {
            iterateSmall(intermediateResult, bigLoopMap, smallLoopMap, smallLoopResultMapSupplier);
        }
    }

    private void iterateSmall(IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult, Map<KeyType, ValueType> bigLoopMap, Map<KeyType, ValueType> smallLoopMap, SafeSupplier<Map<KeyType, ValueType>> smallLoopResultMapSupplier) throws Exception {
        for (Map.Entry<KeyType, ValueType> entry : smallLoopMap.entrySet()) {
            if (entryFilter.test(entry) && !bigLoopMap.containsKey(entry.getKey())) {
                smallLoopResultMapSupplier.get().put(entry.getKey(), entry.getValue());

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }
        }
    }

    private boolean iterateBig(Map<KeyType, ValueType> leftMap, IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult, Map<KeyType, ValueType> bigLoopMap, Map<KeyType, ValueType> smallLoopMap, SafeSupplier<Map<KeyType, ValueType>> bigLoopResultMapSupplier, VisitedObjectsTrace visitedObjectsTrace) throws Exception {
        for (Map.Entry<KeyType, ValueType> entry : bigLoopMap.entrySet()) {
            if (entryFilter.test(entry)) {

                final KeyType key = entry.getKey();
                final ValueType value = entry.getValue();

                final boolean containsKey = smallLoopMap.containsKey(key);
                final ValueType otherValue = smallLoopMap.get(key);

                if (containsKey) {
                    final ComparisonResult<?, ?, ?> comparisonResult;
                    if (bigLoopMap == leftMap) {
                        comparisonResult = exceptionHandlingComparator.compare(comparator, value, otherValue, createResultSupplier(intermediateResult), visitedObjectsTrace);

                        if (handleComparisonResult(value, otherValue, intermediateResult, key, comparisonResult)){
                            return true;
                        }
                    } else {
                        comparisonResult = exceptionHandlingComparator.compare(comparator, otherValue, value, createResultSupplier(intermediateResult), visitedObjectsTrace);

                        if (handleComparisonResult(otherValue, value, intermediateResult, key, comparisonResult)){
                            return true;
                        }
                    }
                } else {
                    if(hasComparisonFeature(bigLoopMap == leftMap)) {
                        bigLoopResultMapSupplier.get().put(key, value);

                        if(stopPredicate.test(intermediateResult)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean handleComparisonResult(ValueType leftValue, ValueType rightValue, IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult, KeyType key, ComparisonResult<?,?,?> comparisonResult) throws InvalidSupplyException, ComparisonFailedException {
        if (comparisonResult == ComparisonResults.skipComparisonResult()) {
            return false;
        } else if (comparisonResult == ComparisonResults.stopComparisonResult()) {
            return true;
        } else {
            if (comparisonResult instanceof ValueComparisonResult) {
                final ValueComparisonResult<ValueType> valueComparisonResult = (ValueComparisonResult<ValueType>) comparisonResult;

                if (comparisonFeatures.contains(ComparisonFeature.SIMILAR_VALUE_ENTRIES) && valueComparisonResult.hasSimilarity()) {
                    intermediateResult.writeSimilarSimilarEntries().put(key, valueComparisonResult.getSimilarity());

                    return stopPredicate.test(intermediateResult);
                } else if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_VALUE_ENTRIES) && valueComparisonResult.hasDifference()) {
                    intermediateResult.writeDifferentValueEntries().put(key, valueComparisonResult.getDifference());

                    return stopPredicate.test(intermediateResult);
                }
            } else {
                if(comparisonFeatures.contains(ComparisonFeature.COMPARED_OBJECT_ENTRIES)) {
                    intermediateResult.writeComparedObjectEntries().put(key, comparisonResult);

                    return stopPredicate.test(intermediateResult);
                }
            }
            return false;
        }
    }

    private boolean hasComparisonFeature(boolean isLeft) {
        if (isLeft) {
            return comparisonFeatures.contains(ComparisonFeature.ENTRIES_ONLY_IN_LEFT_MAP);
        } else {
            return comparisonFeatures.contains(ComparisonFeature.ENTRIES_ONLY_IN_RIGHT_MAP);
        }
    }
}
