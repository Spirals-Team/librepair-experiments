package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyModifiableFunction;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.impl.DefaultDuplicatesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.impl.ImmutableDuplicatesListComparisonResultFunction;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.impl.IntermediateDuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.decorator.checking.NullAcceptingComparator;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparator.ComparisonFeature.*;
import static net.mirwaldt.jcomparison.core.facade.ItemComparators.equalsComparator;

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
public class DefaultDuplicatesListComparatorBuilder<ValueType> {
    private Supplier<Map> createMapSupplier;
    private Function<Map, Map> copyMapFunction;

    private Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessAdditionalElementsOnlyInLeftList;
    private Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessSimilarValues;
    private Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, Pair<ValueType>>> accessDifferentValues;
    private Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ComparisonResult<?, ?, ?>>> accessComparedElements;
    private Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<Integer, ValueType>> accessAdditionalElementsOnlyInRightList;
    private Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessSimilarFrequencies;
    private Function<IntermediateDuplicatesListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessDifferentFrequencies;

    private Function<IntermediateDuplicatesListComparisonResult<ValueType>, DuplicatesListComparisonResult<ValueType>> resultFunction;

    private ItemComparator<ValueType, ? extends ComparisonResult<?, ?, ?>> comparator;
    private ComparisonFailedExceptionHandler exceptionHandler;

    private EnumSet<DuplicatesListComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<IntermediateDuplicatesListComparisonResult<ValueType>> stopPredicate;

    public DefaultDuplicatesListComparatorBuilder<ValueType> copyIntermediateResultsForMinCapacity() {
        accessAdditionalElementsOnlyInLeftList = IntermediateDuplicatesListComparisonResult::copyAdditionalElementsOnlyInLeftList;
        accessSimilarValues = IntermediateDuplicatesListComparisonResult::copySimilarValues;
        accessDifferentValues = IntermediateDuplicatesListComparisonResult::copyDifferentValues;
        accessComparedElements = IntermediateDuplicatesListComparisonResult::copyComparedElements;
        accessAdditionalElementsOnlyInRightList = IntermediateDuplicatesListComparisonResult::copyAdditionalElementsOnlyInRightList;

        accessSimilarFrequencies = IntermediateDuplicatesListComparisonResult::copySimilarFrequencies;
        accessDifferentFrequencies = IntermediateDuplicatesListComparisonResult::copyDifferentFrequencies;

        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> readIntermediateResultsForFinalResult() {
        accessAdditionalElementsOnlyInLeftList = IntermediateDuplicatesListComparisonResult::readAdditionalElementsOnlyInLeftList;
        accessSimilarValues = IntermediateDuplicatesListComparisonResult::readSimilarValues;
        accessDifferentValues = IntermediateDuplicatesListComparisonResult::readDifferentValues;
        accessComparedElements = IntermediateDuplicatesListComparisonResult::readComparedElements;
        accessAdditionalElementsOnlyInRightList = IntermediateDuplicatesListComparisonResult::readAdditionalElementsOnlyInRightList;

        accessSimilarFrequencies = IntermediateDuplicatesListComparisonResult::readSimilarFrequencies;
        accessDifferentFrequencies = IntermediateDuplicatesListComparisonResult::readDifferentFrequencies;

        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useComparator(ItemComparator<ValueType, ? extends ComparisonResult<?, ?, ?>> comparator) {
        this.comparator = comparator;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useDefaultComparator() {
        this.comparator = new NullAcceptingComparator<>(equalsComparator());
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useExceptionHandler(ComparisonFailedExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useDefaultExceptionHandler() {
        this.exceptionHandler = ComparisonFailedExceptionHandlers.RETHROWING_HANDLER;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> comparisonFeatures(EnumSet<DuplicatesListComparator.ComparisonFeature> comparisonFeatures) {
        this.comparisonFeatures = comparisonFeatures;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> findDifferencesOnly() {
        return findDifferencesOnly(true, true);
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> findDifferencesOnly(boolean occurences, boolean frequencies) {
        if (occurences && frequencies) {
            this.comparisonFeatures = EnumSet.of(ADDITIONAL_ELEMENTS_IN_LEFT_LIST, DIFFERENT_ELEMENTS, ADDITIONAL_ELEMENTS_IN_RIGHT_LIST, DIFFERENT_FREQUENCIES);
        } else if (occurences) {
            this.comparisonFeatures = EnumSet.of(ADDITIONAL_ELEMENTS_IN_LEFT_LIST, DIFFERENT_ELEMENTS, ADDITIONAL_ELEMENTS_IN_RIGHT_LIST);
        } else if (frequencies) {
            this.comparisonFeatures = EnumSet.of(DIFFERENT_FREQUENCIES);
        } else {
            throw new IllegalArgumentException("Neither parameter occurences nor parameter frequencies is true. At least one of both must be true.");
        }
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> findSimilaritiesOnly() {
        return findSimilaritiesOnly(true, true);
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> findSimilaritiesOnly(boolean occurences, boolean frequencies) {
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

    public DefaultDuplicatesListComparatorBuilder<ValueType> findComparedObjectsOnly() {
        this.comparisonFeatures = EnumSet.of(DuplicatesListComparator.ComparisonFeature.COMPARED_ELEMENTS);
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> findSimilaritiesAndDifferencesAndComparedObjects() {
        this.comparisonFeatures = EnumSet.allOf(DuplicatesListComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readAdditionalElementsOnlyInLeftList().isEmpty() ||
                        !intermediateComparisonResult.readSimilarValues().isEmpty() ||
                        !intermediateComparisonResult.readDifferentValues().isEmpty() ||
                        !intermediateComparisonResult.readComparedElements().isEmpty() ||
                        !intermediateComparisonResult.readAdditionalElementsOnlyInRightList().isEmpty() ||
                        !intermediateComparisonResult.readSimilarFrequencies().isEmpty() ||
                        !intermediateComparisonResult.readDifferentFrequencies().isEmpty();
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <=
                        intermediateComparisonResult.readAdditionalElementsOnlyInLeftList().size() +
                                intermediateComparisonResult.readSimilarValues().size() +
                                intermediateComparisonResult.readDifferentValues().size() +
                                intermediateComparisonResult.readComparedElements().size() +
                                intermediateComparisonResult.readAdditionalElementsOnlyInRightList().size() +
                                intermediateComparisonResult.readSimilarFrequencies().size() +
                                intermediateComparisonResult.readDifferentFrequencies().size();

        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> stopPredicate(Predicate<IntermediateDuplicatesListComparisonResult<ValueType>> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> resultFunction(Function<IntermediateDuplicatesListComparisonResult<ValueType>, DuplicatesListComparisonResult<ValueType>> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useCreateMapSupplier(Supplier<Map> createMapSupplier) {
        this.createMapSupplier = createMapSupplier;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useCopyMapFunction(Function<Map, Map> copyMapFunction) {
        this.copyMapFunction = copyMapFunction;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useDefaultCreateMapSupplier() {
        this.createMapSupplier = HashMap::new;
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useDefaultCopyMapFunction() {
        this.copyMapFunction = new CopyIfNonEmptyModifiableFunction<Map>(Map::isEmpty, HashMap::new);
        return this;
    }

    public DefaultDuplicatesListComparatorBuilder<ValueType> useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultDuplicatesListComparator<ValueType> build() {
        final Supplier<IntermediateDuplicatesListComparisonResult<ValueType>> intermediateResultField = () -> new IntermediateDuplicatesListComparisonResult<>(createMapSupplier, copyMapFunction);

        final Function<IntermediateDuplicatesListComparisonResult<ValueType>, DuplicatesListComparisonResult<ValueType>> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableDuplicatesListComparisonResultFunction<>(accessAdditionalElementsOnlyInLeftList, accessSimilarValues, accessDifferentValues, accessComparedElements, accessAdditionalElementsOnlyInRightList, accessSimilarFrequencies, accessDifferentFrequencies);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultDuplicatesListComparator<>(
                intermediateResultField,
                usedResultFunction,
                comparator,
                exceptionHandler,
                comparisonFeatures,
                stopPredicate
        );
    }
}
