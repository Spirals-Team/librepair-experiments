package net.mirwaldt.jcomparison.core.facade.builder;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.facade.ComparatorDecorators;
import net.mirwaldt.jcomparison.core.facade.ComparisonFailedExceptionHandlers;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyFunction;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.map.api.MapComparator;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.impl.DefaultMapComparator;
import net.mirwaldt.jcomparison.core.map.impl.ImmutableMapComparisonResultFunction;
import net.mirwaldt.jcomparison.core.map.impl.IntermediateMapComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

import java.util.EnumSet;
import java.util.HashMap;
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
@NotThreadSafe
public class DefaultMapComparatorBuilder<KeyType, ValueType> {
    private Supplier<Map> createMapSupplier;
    private Function<Map, Map> copyMapFunction;

    private Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueType>> accessEntriesOnlyInLeftMap;
    private Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueSimilarity<ValueType>>> accessSimilarValueEntries;
    private Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, Pair<ValueType>>> accessDifferentValueEntries;
    private Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ComparisonResult<?,?,?>>> accessComparedObjectEntries;
    private Function<IntermediateMapComparisonResult<KeyType, ValueType>, Map<KeyType, ValueType>> accessEntriesOnlyInRightMap;
    private Function<IntermediateMapComparisonResult<KeyType, ValueType>, MapComparisonResult<KeyType, ValueType>> resultFunction;

    private ItemComparator<ValueType,? extends ComparisonResult<?,?,?>> comparator;
    private ComparisonFailedExceptionHandler exceptionHandler;

    private EnumSet<MapComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<IntermediateMapComparisonResult<KeyType, ValueType>> stopPredicate;
    private Predicate<Map.Entry<KeyType, ValueType>> entryFilter;

    public DefaultMapComparatorBuilder<KeyType, ValueType> copyIntermediateResultsForMinCapacity() {
        accessEntriesOnlyInLeftMap = IntermediateMapComparisonResult::copyEntriesOnlyInLeftMap;
        accessSimilarValueEntries = IntermediateMapComparisonResult::copySimilarValueEntries;
        accessDifferentValueEntries = IntermediateMapComparisonResult::copyDifferentValueEntries;
        accessComparedObjectEntries = IntermediateMapComparisonResult::copyComparedObjectEntries;
        accessEntriesOnlyInRightMap = IntermediateMapComparisonResult::copyEntriesOnlyInRightMap;

        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> readIntermediateResultsForFinalResult() {
        accessEntriesOnlyInLeftMap = IntermediateMapComparisonResult::readEntriesOnlyInLeftMap;
        accessSimilarValueEntries = IntermediateMapComparisonResult::readSimilarValueEntries;
        accessDifferentValueEntries = IntermediateMapComparisonResult::readDifferentValueEntries;
        accessComparedObjectEntries = IntermediateMapComparisonResult::readComparedObjectEntries;
        accessEntriesOnlyInRightMap = IntermediateMapComparisonResult::readEntriesOnlyInRightMap;

        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useComparator(ItemComparator<ValueType,? extends ComparisonResult<?,?,?>> comparator) {
        this.comparator = comparator;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useDefaultComparator() {
        this.comparator = ComparatorDecorators.specializing(ComparatorDecorators.nullAccepting(ValueComparators.switchingEqualsComparator()));
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useExceptionHandler(ComparisonFailedExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useDefaultExceptionHandler() {
        this.exceptionHandler = ComparisonFailedExceptionHandlers.getRethrowingHandler();
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> considerComparisonFeatures(EnumSet<MapComparator.ComparisonFeature> comparisonFeatures) {
        this.comparisonFeatures = comparisonFeatures;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> findDifferencesOnly() {
        this.comparisonFeatures = EnumSet.complementOf(EnumSet.of(MapComparator.ComparisonFeature.SIMILAR_ENTRIES, MapComparator.ComparisonFeature.COMPARE_ENTRIES_DEEP));
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> findSimilaritiesOnly() {
        this.comparisonFeatures = EnumSet.of(MapComparator.ComparisonFeature.SIMILAR_ENTRIES);
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> compareEntriesDeepOnly() {
        this.comparisonFeatures = EnumSet.of(MapComparator.ComparisonFeature.COMPARE_ENTRIES_DEEP);
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> findSimilaritiesAndDifferencesAndComparedObjects() {
        this.comparisonFeatures = EnumSet.allOf(MapComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readEntriesOnlyInLeftMap().isEmpty() ||
                        !intermediateComparisonResult.readSimilarValueEntries().isEmpty() ||
                        !intermediateComparisonResult.readDifferentValueEntries().isEmpty() ||
                        !intermediateComparisonResult.readEntriesOnlyInRightMap().isEmpty() ||
                        !intermediateComparisonResult.readComparedObjectEntries().isEmpty();
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <=
                        intermediateComparisonResult.readEntriesOnlyInLeftMap().size() +
                                intermediateComparisonResult.readSimilarValueEntries().size() +
                                intermediateComparisonResult.readDifferentValueEntries().size() +
                                intermediateComparisonResult.readEntriesOnlyInRightMap().size() +
                                intermediateComparisonResult.readComparedObjectEntries().size();
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> stopPredicate(Predicate<IntermediateMapComparisonResult<KeyType, ValueType>> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> resultFunction(Function<IntermediateMapComparisonResult<KeyType, ValueType>, MapComparisonResult<KeyType, ValueType>> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useCreateMapSupplier(Supplier<Map> createMapSupplier) {
        this.createMapSupplier = createMapSupplier;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useCopyMapFunction(Function<Map, Map> copyMapFunction) {
        this.copyMapFunction = copyMapFunction;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useDefaultCreateMapSupplier() {
        this.createMapSupplier = HashMap::new;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useDefaultCopyMapFunction() {
        this.copyMapFunction = new CopyIfNonEmptyFunction<Map>(Map::isEmpty, HashMap::new);
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> allowAllFilter() {
        this.entryFilter = (value) -> true;
        return this;
    }

    public DefaultMapComparatorBuilder<KeyType, ValueType> filterElements(Predicate<Map.Entry<KeyType, ValueType>> entryFilter) {
        this.entryFilter = entryFilter;
        return this;
    }

    public DefaultMapComparator<KeyType, ValueType> build() {
        final Supplier<IntermediateMapComparisonResult<KeyType, ValueType>> intermediateResultField = () -> new IntermediateMapComparisonResult<>(createMapSupplier, copyMapFunction);

        final Function<IntermediateMapComparisonResult<KeyType, ValueType>, MapComparisonResult<KeyType, ValueType>> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableMapComparisonResultFunction<>(accessEntriesOnlyInLeftMap, accessSimilarValueEntries, accessDifferentValueEntries, accessComparedObjectEntries, accessEntriesOnlyInRightMap);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultMapComparator<>(
                intermediateResultField,
                usedResultFunction,
                comparator,
                exceptionHandler,
                comparisonFeatures,
                stopPredicate,
                entryFilter
        );
    }
}
