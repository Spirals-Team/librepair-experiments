package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;
import net.mirwaldt.jcomparison.core.map.api.MapComparator;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;

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
    private final ComparisonFailedExceptionHandler exceptionHandler;

    private final EnumSet<ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateMapComparisonResult<KeyType, ValueType>> stopPredicate;
    private final Predicate<Map.Entry<KeyType, ValueType>> entryFilter;

    /**
     * optimizations
     */
    private final ComparatorOptions comparatorOptions;
    private final boolean needsComparison;

    public DefaultMapComparator(
            Supplier<IntermediateMapComparisonResult<KeyType, ValueType>> intermediateResultField,
            Function<IntermediateMapComparisonResult<KeyType, ValueType>, MapComparisonResult<KeyType, ValueType>> resultFunction, 
            ItemComparator<ValueType,? extends ComparisonResult<?,?,?>> comparator, 
            ComparisonFailedExceptionHandler exceptionHandler, 
            EnumSet<ComparisonFeature> comparisonFeatures, 
            Predicate<IntermediateMapComparisonResult<KeyType, ValueType>> stopPredicate, 
            Predicate<Map.Entry<KeyType, ValueType>> entryFilter) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparator = comparator;
        this.exceptionHandler = exceptionHandler;
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;
        this.entryFilter = entryFilter;

        this.needsComparison = comparisonFeatures.stream()
                .anyMatch(comparisonFeature -> comparisonFeature.needsValueComparison() || comparisonFeature.needsNonValueComparison());

        this.comparatorOptions = new ComparatorOptions(true,
                comparisonFeatures.stream().anyMatch(ComparisonFeature::needsValueComparison),
                comparisonFeatures.stream().anyMatch(ComparisonFeature::needsNonValueComparison)
        );
    }

    private Supplier<ComparisonResult<?, ?, ?>> createResultSupplier(IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult) {
        return () -> resultFunction.apply(intermediateResult);
    }

    @Override
    public MapComparisonResult<KeyType, ValueType> compare(Map<KeyType, ValueType> leftMap,
                                                           Map<KeyType, ValueType> rightMap, 
                                                           VisitedObjectsTrace visitedObjectsTrace,
                                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        try {
            final IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult = intermediateResultField.get();

            if (!leftMap.isEmpty() || !rightMap.isEmpty()) {
                final ComparisonFailedExceptionHandlingComparator<ValueType> exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator<>(exceptionHandler);
                exceptionHandlingComparator.setComparator(comparator);
                exceptionHandlingComparator.setComparisonResultSafeSupplier(createResultSupplier(intermediateResult));
                findSimilaritiesAndDifferences(leftMap, rightMap, visitedObjectsTrace, comparatorOptions, intermediateResult, exceptionHandlingComparator);
            }

            return resultFunction.apply(intermediateResult);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both maps.", e, leftMap, rightMap);
        }
    }

    private void findSimilaritiesAndDifferences(Map<KeyType, ValueType> leftMap, 
                                                Map<KeyType, ValueType> rightMap,
                                                VisitedObjectsTrace visitedObjectsTrace,
                                                ComparatorOptions comparatorOptions, 
                                                IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult, 
                                                ComparisonFailedExceptionHandlingComparator<ValueType> exceptionHandlingComparator) throws Exception {
        final Map<KeyType, ValueType> bigLoopMap;
        final Supplier<Map<KeyType, ValueType>> bigLoopResultMapSupplier;

        final Map<KeyType, ValueType> smallLoopMap;
        final Supplier<Map<KeyType, ValueType>> smallLoopResultMapSupplier;

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

        if (iterateBig(leftMap, visitedObjectsTrace, comparatorOptions, intermediateResult, bigLoopMap, smallLoopMap, bigLoopResultMapSupplier, exceptionHandlingComparator))
            return;

        if(hasComparisonFeature(smallLoopMap == leftMap)) {
            iterateSmall(intermediateResult, bigLoopMap, smallLoopMap, smallLoopResultMapSupplier);
        }
    }

    private void iterateSmall(IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult, 
                              Map<KeyType, ValueType> bigLoopMap, 
                              Map<KeyType, ValueType> smallLoopMap, 
                              Supplier<Map<KeyType, ValueType>> smallLoopResultMapSupplier) throws Exception {
        for (Map.Entry<KeyType, ValueType> entry : smallLoopMap.entrySet()) {
            if (entryFilter.test(entry) && !bigLoopMap.containsKey(entry.getKey())) {
                smallLoopResultMapSupplier.get().put(entry.getKey(), entry.getValue());

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }
        }
    }

    private boolean iterateBig(Map<KeyType, ValueType> leftMap, 
                               VisitedObjectsTrace visitedObjectsTrace,
                               ComparatorOptions comparatorOptions,
                               IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult, 
                               Map<KeyType, ValueType> bigLoopMap, Map<KeyType, ValueType> smallLoopMap, 
                               Supplier<Map<KeyType, ValueType>> bigLoopResultMapSupplier, 
                               ComparisonFailedExceptionHandlingComparator<ValueType> exceptionHandlingComparator) throws Exception {
        for (Map.Entry<KeyType, ValueType> entry : bigLoopMap.entrySet()) {
            if (entryFilter.test(entry)) {

                final KeyType key = entry.getKey();
                final ValueType value = entry.getValue();

                final boolean containsKey = smallLoopMap.containsKey(key);
                final ValueType otherValue = smallLoopMap.get(key);

                if (containsKey) {
                    if(needsComparison) {
                        final ComparisonResult<?, ?, ?> comparisonResult;
                        if (bigLoopMap == leftMap) {
                            comparisonResult = exceptionHandlingComparator.compare(value, otherValue, visitedObjectsTrace, this.comparatorOptions);

                            if (handleComparisonResult(intermediateResult, key, comparisonResult)){
                                return true;
                            }
                        } else {
                            comparisonResult = exceptionHandlingComparator.compare(otherValue, value, visitedObjectsTrace, this.comparatorOptions);

                            if (handleComparisonResult(intermediateResult, key, comparisonResult)){
                                return true;
                            }
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

    private boolean handleComparisonResult(IntermediateMapComparisonResult<KeyType, ValueType> intermediateResult, 
                                           KeyType key, ComparisonResult<?,?,?> comparisonResult) throws ComparisonFailedException {
        if (comparisonResult == BasicComparisonResults.skipComparisonResult()) {
            return false;
        } else if (comparisonResult == BasicComparisonResults.stopComparisonResult()) {
            return true;
        } else {
            if (comparisonResult instanceof ValueComparisonResult) {
                final ValueComparisonResult<ValueType> valueComparisonResult = (ValueComparisonResult<ValueType>) comparisonResult;

                if (comparisonFeatures.contains(ComparisonFeature.SIMILAR_ENTRIES) && valueComparisonResult.hasSimilarities()) {
                    intermediateResult.writeSimilarSimilarEntries().put(key, valueComparisonResult.getSimilarities());

                    return stopPredicate.test(intermediateResult);
                } else if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_ENTRIES) && valueComparisonResult.hasDifferences()) {
                    intermediateResult.writeDifferentValueEntries().put(key, valueComparisonResult.getDifferences());

                    return stopPredicate.test(intermediateResult);
                }
            } else {
                if(comparisonFeatures.contains(ComparisonFeature.COMPARE_ENTRIES_DEEP)) {
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
