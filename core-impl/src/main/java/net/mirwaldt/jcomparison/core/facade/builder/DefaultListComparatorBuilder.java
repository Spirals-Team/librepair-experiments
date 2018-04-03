package net.mirwaldt.jcomparison.core.facade.builder;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.impl.DefaultListComparator;
import net.mirwaldt.jcomparison.core.collection.list.impl.ImmutableListComparisonResultFunction;
import net.mirwaldt.jcomparison.core.collection.list.impl.IntermediateListComparisonResult;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.facade.ComparatorDecorators;
import net.mirwaldt.jcomparison.core.facade.ComparisonFailedExceptionHandlers;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyFunction;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.mirwaldt.jcomparison.core.collection.list.api.ListComparator.ComparisonFeature.SIMILAR_ELEMENTS;
import static net.mirwaldt.jcomparison.core.collection.list.api.ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class DefaultListComparatorBuilder<ValueType> {
    private Supplier<Map> createMapSupplier;
    private Function<Map, Map> copyMapFunction;

    private Function<IntermediateListComparisonResult<ValueType>, Map<Integer, ValueSimilarity<ValueType>>> accessSimilarValues;
    private Function<IntermediateListComparisonResult<ValueType>, Map<Integer, Pair<ValueType>>> accessDifferentValues;
    private Function<IntermediateListComparisonResult<ValueType>, Map<Integer, ComparisonResult<?, ?, ?>>> accessComparedElements;
    private Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Integer>> accessSimilarFrequencies;
    private Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessDifferentFrequencies;

    private Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessSimilarPositions;
    private Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, Pair<ImmutableIntList>>> accessDifferentPositions;

    private Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessElementsOnlyInLeftList;
    private Function<IntermediateListComparisonResult<ValueType>, Map<ValueType, ImmutableIntList>> accessElementsOnlyInRightList;

    private Function<IntermediateListComparisonResult<ValueType>, ListComparisonResult<ValueType>> resultFunction;

    private ItemComparator<ValueType, ? extends ComparisonResult<?, ?, ?>> comparator;
    private ComparisonFailedExceptionHandler exceptionHandler;

    private Predicate<ValueType> elementsFilter;
    private EnumSet<ListComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<IntermediateListComparisonResult<ValueType>> stopPredicate;

    public DefaultListComparatorBuilder<ValueType> copyIntermediateResultsForMinCapacity() {
        accessSimilarValues = IntermediateListComparisonResult::copySimilarValues;
        accessDifferentValues = IntermediateListComparisonResult::copyDifferentValues;
        accessComparedElements = IntermediateListComparisonResult::copyComparedElements;

        accessSimilarFrequencies = IntermediateListComparisonResult::copySimilarFrequencies;
        accessDifferentFrequencies = IntermediateListComparisonResult::copyDifferentFrequencies;

        accessSimilarPositions = IntermediateListComparisonResult::copySimilarPositions;
        accessDifferentPositions = IntermediateListComparisonResult::copyDifferentPositions;

        accessElementsOnlyInLeftList = IntermediateListComparisonResult::copyElementsOnlyInLeftList;
        accessElementsOnlyInRightList = IntermediateListComparisonResult::copyElementsOnlyInRightList;

        return this;
    }

    public DefaultListComparatorBuilder<ValueType> readIntermediateResultsForFinalResult() {
        accessSimilarValues = IntermediateListComparisonResult::readSimilarValues;
        accessDifferentValues = IntermediateListComparisonResult::readDifferentValues;
        accessComparedElements = IntermediateListComparisonResult::readComparedElements;

        accessSimilarFrequencies = IntermediateListComparisonResult::readSimilarFrequencies;
        accessDifferentFrequencies = IntermediateListComparisonResult::readDifferentFrequencies;

        accessSimilarPositions = IntermediateListComparisonResult::readSimilarPositions;
        accessDifferentPositions = IntermediateListComparisonResult::readDifferentPositions;

        accessElementsOnlyInLeftList = IntermediateListComparisonResult::readElementsOnlyInLeftList;
        accessElementsOnlyInRightList = IntermediateListComparisonResult::readElementsOnlyInRightList;

        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useComparator(ItemComparator<ValueType, ? extends ComparisonResult<?, ?, ?>> comparator) {
        this.comparator = comparator;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useDefaultComparator() {
        this.comparator = ComparatorDecorators.specializing(ComparatorDecorators.nullAccepting(ValueComparators.switchingEqualsComparator()));
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useExceptionHandler(ComparisonFailedExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useDefaultExceptionHandler() {
        this.exceptionHandler = ComparisonFailedExceptionHandlers.getRethrowingHandler();
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useComparisonFeatures(EnumSet<ListComparator.ComparisonFeature> comparisonFeatures) {
        this.comparisonFeatures = comparisonFeatures;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> findDifferencesOnly() {
        return findDifferencesOnly(true, true, true);
    }

    public DefaultListComparatorBuilder<ValueType> findDifferencesOnly(boolean occurences, boolean frequencies, boolean positions) {
        if (occurences || frequencies || positions) {
            this.comparisonFeatures =
                    EnumSet.copyOf(Arrays.stream(ListComparator.ComparisonFeature.values())
                            .filter(ListComparator.ComparisonFeature::isDifference)
                            .filter((feature) -> feature.considersOccurence() == occurences)
                            .filter((feature) -> feature.considersFrequency() == frequencies)
                            .filter((feature) -> feature.considersPositions() == positions)
                            .collect(Collectors.toList()));
        } else {
            throw new IllegalArgumentException("Neither parameter occurences nor parameter frequencies is true. At least one of both must be true.");
        }
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> findSimilaritiesOnly() {
        return findSimilaritiesOnly(true, true);
    }

    public DefaultListComparatorBuilder<ValueType> findSimilaritiesOnly(boolean occurences, boolean frequencies) {
        if (occurences && frequencies) {
            this.comparisonFeatures = EnumSet.of(SIMILAR_ELEMENTS, SIMILAR_FREQUENCIES);
        } else if (occurences) {
            this.comparisonFeatures = EnumSet.of(SIMILAR_ELEMENTS);
        } else if (frequencies) {
            this.comparisonFeatures = EnumSet.of(SIMILAR_FREQUENCIES);
        } else {
            throw new IllegalArgumentException("Neither parameter occurences nor parameter frequencies is true. At least one of both must be true.");
        }
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> findComparedObjectsOnly() {
        this.comparisonFeatures = EnumSet.of(ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP);
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> findSimilaritiesAndDifferencesAndComparedObjects() {
        this.comparisonFeatures = EnumSet.allOf(ListComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readElementsOnlyInLeftList().isEmpty() ||
                        !intermediateComparisonResult.readSimilarValues().isEmpty() ||
                        !intermediateComparisonResult.readDifferentValues().isEmpty() ||
                        !intermediateComparisonResult.readComparedElements().isEmpty() ||
                        !intermediateComparisonResult.readElementsOnlyInRightList().isEmpty() ||
                        !intermediateComparisonResult.readSimilarFrequencies().isEmpty() ||
                        !intermediateComparisonResult.readDifferentFrequencies().isEmpty() ||
                        !intermediateComparisonResult.readSimilarPositions().isEmpty() ||
                        !intermediateComparisonResult.readDifferentPositions().isEmpty();
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <=
                        intermediateComparisonResult.readElementsOnlyInLeftList().size() +
                                intermediateComparisonResult.readSimilarValues().size() +
                                intermediateComparisonResult.readDifferentValues().size() +
                                intermediateComparisonResult.readComparedElements().size() +
                                intermediateComparisonResult.readElementsOnlyInRightList().size() +
                                intermediateComparisonResult.readSimilarFrequencies().size() +
                                intermediateComparisonResult.readDifferentFrequencies().size() +
                                intermediateComparisonResult.readSimilarPositions().size() +
                                intermediateComparisonResult.readDifferentPositions().size();
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> stopPredicate(Predicate<IntermediateListComparisonResult<ValueType>> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> resultFunction(Function<IntermediateListComparisonResult<ValueType>, ListComparisonResult<ValueType>> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useCreateMapSupplier(Supplier<Map> createMapSupplier) {
        this.createMapSupplier = createMapSupplier;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useCopyMapFunction(Function<Map, Map> copyMapFunction) {
        this.copyMapFunction = copyMapFunction;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useDefaultCreateMapSupplier() {
        this.createMapSupplier = HashMap::new;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useDefaultCopyMapFunction() {
        this.copyMapFunction = new CopyIfNonEmptyFunction<Map>(Map::isEmpty, HashMap::new);
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> allowAllFilter() {
        this.elementsFilter = (value) -> true;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> filterElements(Predicate<ValueType> elementsFilter) {
        this.elementsFilter = elementsFilter;
        return this;
    }

    public DefaultListComparatorBuilder<ValueType> ignoreNulls() {
        this.elementsFilter = Objects::nonNull;
        return this;
    }

    public DefaultListComparator<ValueType> build() {
        final Supplier<IntermediateListComparisonResult<ValueType>> intermediateResultField = () -> new IntermediateListComparisonResult<>(createMapSupplier, copyMapFunction);

        final Function<IntermediateListComparisonResult<ValueType>, ListComparisonResult<ValueType>> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableListComparisonResultFunction<>(
                    accessSimilarValues,
                    accessDifferentValues,
                    accessComparedElements,
                    accessSimilarFrequencies,
                    accessDifferentFrequencies,
                    accessSimilarPositions,
                    accessDifferentPositions,
                    accessElementsOnlyInLeftList,
                    accessElementsOnlyInRightList);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultListComparator<>(
                intermediateResultField,
                usedResultFunction,
                comparator,
                exceptionHandler,
                elementsFilter,
                comparisonFeatures,
                stopPredicate
        );
    }
}
