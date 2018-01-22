package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.impl.DefaultArrayComparator;
import net.mirwaldt.jcomparison.core.array.impl.ImmutableArrayComparisonResultBiFunction;
import net.mirwaldt.jcomparison.core.array.impl.IntermediateArrayComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyModifiableFunction;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.decorator.checking.NullAcceptingComparator;
import net.mirwaldt.jcomparison.core.decorator.tracing.CycleDetectingComparator;
import net.mirwaldt.jcomparison.core.decorator.tracing.ExceptionAtCycleComparator;
import net.mirwaldt.jcomparison.core.decorator.tracing.TracingComparator;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;
import net.mirwaldt.jcomparison.core.util.deduplicator.impl.NoDeduplicator;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.mirwaldt.jcomparison.core.facade.ItemComparators.mutableEqualsComparator;

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
public class DefaultArrayComparatorBuilder<ArrayType> {
    private Supplier<Map> createMapSupplier;
    private Function<Map, Map> copyMapFunction;

    private Deduplicator deduplicator;

    private Function<IntermediateArrayComparisonResult, Map<ImmutableIntList, ?>> accessAdditionalElementsOnlyInLeftArray;
    private Function<IntermediateArrayComparisonResult, Map<ImmutableIntList, ?>> accessAdditionalElementsOnlyInRightArray;
    private Function<IntermediateArrayComparisonResult, Map<ImmutableIntList, ?>> accessSimilarElements;
    private Function<IntermediateArrayComparisonResult, Map<ImmutableIntList, Pair<Object>>> accessDifferentValues;
    private Function<IntermediateArrayComparisonResult, Map<ImmutableIntList, ComparisonResult<?,?,?>>> accessComparisonResults;
    private BiFunction<IntermediateArrayComparisonResult, Integer, ArrayComparisonResult> resultFunction;

    private ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementsComparator;
    private ComparisonFailedExceptionHandler exceptionHandler;

    private EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<Object> elementsFilter;
    private Predicate<int[]> skipIndexPredicate;
    private Predicate<int[]> stopIndexPredicate;
    private Predicate<IntermediateArrayComparisonResult> stopPredicate;

    private Function<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>, ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> cycleProtectingPreComparatorFunction;

    public DefaultArrayComparatorBuilder<ArrayType> useDeduplicator(Deduplicator deduplicator) {
        this.deduplicator = deduplicator;

        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useNoDeduplicator() {
        // TODO: use one NoDeduplicator instance at all in the framework
        this.deduplicator = new NoDeduplicator();

        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> copyIntermediateResultsForMinCapacity() {
        accessAdditionalElementsOnlyInLeftArray = IntermediateArrayComparisonResult::copyAdditionalElementsOnlyInLeftArray;
        accessAdditionalElementsOnlyInRightArray = IntermediateArrayComparisonResult::copyAdditionalElementsOnlyInRightArray;
        accessSimilarElements = IntermediateArrayComparisonResult::copySimilarElements;
        accessDifferentValues = IntermediateArrayComparisonResult::copyDifferentValues;
        accessComparisonResults = IntermediateArrayComparisonResult::copyComparisonResults;

        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> readIntermediateResultsForFinalResult() {
        accessAdditionalElementsOnlyInLeftArray = IntermediateArrayComparisonResult::readAdditionalElementsOnlyInLeftArray;
        accessAdditionalElementsOnlyInRightArray = IntermediateArrayComparisonResult::readAdditionalElementsOnlyInRightArray;
        accessSimilarElements = IntermediateArrayComparisonResult::readSimilarElements;
        accessDifferentValues = IntermediateArrayComparisonResult::readDifferentValues;
        accessComparisonResults = IntermediateArrayComparisonResult::readComparisonResults;

        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useElementsComparator(ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementsComparator) {
        this.elementsComparator = elementsComparator;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useDefaultElementsComparator() {
        this.elementsComparator = new NullAcceptingComparator<>(mutableEqualsComparator());
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useExceptionHandler(ComparisonFailedExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useDefaultExceptionHandler() {
        this.exceptionHandler = ComparisonFailedExceptionHandlers.RETHROWING_HANDLER;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> comparisonFeatures(EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures) {
        this.comparisonFeatures = comparisonFeatures;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> findDifferencesOnly() {
        this.comparisonFeatures = EnumSet.complementOf(EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS, ArrayComparator.ComparisonFeature.COMPARISON_RESULTS));
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> findSimilaritiesOnly() {
        this.comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS);
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> findComparedObjectsOnly() {
        this.comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.COMPARISON_RESULTS);
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> findSimilaritiesAndDifferencesAndComparedObjects() {
        this.comparisonFeatures = EnumSet.allOf(ArrayComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> findFirstResultOnly() {
        return findMaxNumberOfResults(1);
    }

    public DefaultArrayComparatorBuilder<ArrayType> findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <=
                        intermediateComparisonResult.readAdditionalElementsOnlyInLeftArray().size() +
                                intermediateComparisonResult.readAdditionalElementsOnlyInRightArray().size() +
                                intermediateComparisonResult.readSimilarElements().size() +
                                intermediateComparisonResult.readDifferentValues().size() +
                                intermediateComparisonResult.readComparisonResults().size();
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> stopPredicate(Predicate<IntermediateArrayComparisonResult> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> resultFunction(BiFunction<IntermediateArrayComparisonResult, Integer, ArrayComparisonResult> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useCreateMapSupplier(Supplier<Map> createMapSupplier) {
        this.createMapSupplier = createMapSupplier;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useCopyMapFunction(Function<Map, Map> copyMapFunction) {
        this.copyMapFunction = copyMapFunction;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useDefaultCreateMapSupplier() {
        this.createMapSupplier = HashMap::new;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useDefaultCopyMapFunction() {
        this.copyMapFunction = new CopyIfNonEmptyModifiableFunction<Map>(Map::isEmpty, HashMap::new);
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> allowAllElementsFilter() {
        this.elementsFilter = (element) -> true;
        return this;
    }

    /**
     * important: uses MuatblePrimitive-instances to avoid heap pollution from boxing!
     *
     * @param elementsFilter the object itself if it is non-primitive, otherwise a MuatblePrimitive-instance!
     * @return the builder again
     */
    public DefaultArrayComparatorBuilder<ArrayType> filterElements(Predicate<Object> elementsFilter) {
        this.elementsFilter = elementsFilter;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> considerAllPositions() {
        this.skipIndexPredicate = (indexes) -> false;
        this.stopIndexPredicate = (indexes) -> false;
        return this;
    }

    /**
     * do not save array position in predicate when calling test() because it is reused/mutable
     */
    public DefaultArrayComparatorBuilder<ArrayType> stopAtPosition(Predicate<int[]> stopIndexPredicate) {
        this.stopIndexPredicate = stopIndexPredicate;
        return this;
    }

    /**
     * do not save array position in predicate when calling test() because it is reused/mutable
     */
    public DefaultArrayComparatorBuilder<ArrayType> skipAtPosition(Predicate<int[]> skipIndexPredicate) {
        this.skipIndexPredicate = skipIndexPredicate;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useCycleProtectingPreComparatorFunction(Function<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>, ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> cycleProtectingPreComparatorFunction) {
        this.cycleProtectingPreComparatorFunction = cycleProtectingPreComparatorFunction;
        return this;
    }

    public DefaultArrayComparatorBuilder<ArrayType> useDefaultCycleProtectingPreComparatorFunction() {
        this.cycleProtectingPreComparatorFunction = (itemComparator) -> new CycleDetectingComparator(new TracingComparator(itemComparator), new ExceptionAtCycleComparator());;
        return this;
    }

    public DefaultArrayComparator<ArrayType> build() {
        final Supplier<IntermediateArrayComparisonResult> intermediateResultField = () -> new IntermediateArrayComparisonResult(createMapSupplier, copyMapFunction);

        final BiFunction<IntermediateArrayComparisonResult, Integer, ArrayComparisonResult> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableArrayComparisonResultBiFunction(accessAdditionalElementsOnlyInLeftArray, accessAdditionalElementsOnlyInRightArray, accessSimilarElements, accessDifferentValues, accessComparisonResults);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultArrayComparator<>(
                deduplicator,
                elementsComparator,
                cycleProtectingPreComparatorFunction,
                intermediateResultField,
                usedResultFunction,
                comparisonFeatures,
                elementsFilter,
                skipIndexPredicate,
                stopIndexPredicate,
                stopPredicate,
                exceptionHandler
        );
    }
}
