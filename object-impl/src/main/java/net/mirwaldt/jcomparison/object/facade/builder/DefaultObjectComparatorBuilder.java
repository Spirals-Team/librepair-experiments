package net.mirwaldt.jcomparison.object.facade.builder;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.facade.ComparisonFailedExceptionHandlers;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyFunction;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.decorator.NullAcceptingComparator;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;
import net.mirwaldt.jcomparison.core.util.deduplicator.impl.NoDeduplicator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import net.mirwaldt.jcomparison.object.api.FieldValueAccessor;
import net.mirwaldt.jcomparison.object.api.ObjectComparator;
import net.mirwaldt.jcomparison.object.api.ObjectComparisonResult;
import net.mirwaldt.jcomparison.object.impl.*;
import net.mirwaldt.jcomparison.provider.api.ComparatorProvider;
import net.mirwaldt.jcomparison.provider.impl.SameComparatorProvidingItemComparatorProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
public class DefaultObjectComparatorBuilder<ObjectType> {
    private Function<Map, Map> copyMapFunction;

    private Function<IntermediateObjectComparisonResult, Map<Field, ValueSimilarity<Object>>> accessSimilarValuesOfFields;
    private Function<IntermediateObjectComparisonResult, Map<Field, Pair<Object>>> accessDifferentValuesOfFields;
    private Function<IntermediateObjectComparisonResult, Map<Field, ComparisonResult<?, ?, ?>>> accessComparisonResultOfFields;
    private Function<IntermediateObjectComparisonResult, ObjectComparisonResult> resultFunction;

    private ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorProvider;
    private ComparisonFailedExceptionHandler exceptionHandler;

    private EnumSet<ObjectComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<IntermediateObjectComparisonResult> stopPredicate;
    private Predicate<Field> fieldPredicate;

    private Deduplicator deduplicator;
    private FieldValueAccessor fieldValueAccessor;

    public DefaultObjectComparatorBuilder<ObjectType> copyIntermediateResultsForMinCapacity() {
        accessSimilarValuesOfFields = IntermediateObjectComparisonResult::copySimilarValuesOfFields;
        accessDifferentValuesOfFields = IntermediateObjectComparisonResult::copyDifferentValuesOfFields;
        accessComparisonResultOfFields = IntermediateObjectComparisonResult::copyComparisonResultOfFields;

        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> readIntermediateResultsForFinalResult() {
        accessSimilarValuesOfFields = IntermediateObjectComparisonResult::readSimilarValuesOfFields;
        accessDifferentValuesOfFields = IntermediateObjectComparisonResult::readDifferentValuesOfFields;
        accessComparisonResultOfFields = IntermediateObjectComparisonResult::readComparisonResultOfFields;

        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useDeduplicator(Deduplicator deduplicator) {
        this.deduplicator = deduplicator;

        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useNoDeduplicator() {
        this.deduplicator = new NoDeduplicator();

        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useExceptionHandler(ComparisonFailedExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useDefaultExceptionHandler() {
        this.exceptionHandler = ComparisonFailedExceptionHandlers.getRethrowingHandler();
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> findDifferencesOnly() {
        this.comparisonFeatures = EnumSet.of(ObjectComparator.ComparisonFeature.SIMILAR_VALUES_OF_FIELDS);
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> findSimilaritiesOnly() {
        this.comparisonFeatures = EnumSet.of(ObjectComparator.ComparisonFeature.DIFFERENT_VALUES_OF_FIELDS);
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> findComparedObjectsOnly() {
        this.comparisonFeatures = EnumSet.of(ObjectComparator.ComparisonFeature.COMPARISON_RESULTS_OF_FIELDS);
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> findSimilaritiesAndDifferencesAndComparedObjects() {
        this.comparisonFeatures = EnumSet.allOf(ObjectComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readSimilarValuesOfFields().isEmpty() ||
                        !intermediateComparisonResult.readDifferentValuesOfFields().isEmpty() ||
                        !intermediateComparisonResult.readComparisonResultOfFields().isEmpty();
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <=
                        intermediateComparisonResult.readSimilarValuesOfFields().size() +
                                intermediateComparisonResult.readDifferentValuesOfFields().size() +
                                intermediateComparisonResult.readComparisonResultOfFields().size();
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> stopPredicate(Predicate<IntermediateObjectComparisonResult> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> resultFunction(Function<IntermediateObjectComparisonResult, ObjectComparisonResult> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useCopyMapFunction(Function<Map, Map> copyMapFunction) {
        this.copyMapFunction = copyMapFunction;
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useDefaultCopyMapFunction() {
        this.copyMapFunction = new CopyIfNonEmptyFunction<Map>(Map::isEmpty, HashMap::new);
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> allowAllNonStaticANdNonFinalFields() {
        this.fieldPredicate = (field) -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers());
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> filterFields(Predicate<Field> fieldPredicate) {
        this.fieldPredicate = fieldPredicate;
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useDefaultFieldValueAccessor() {
        this.fieldValueAccessor = new DirectFieldValueAccessor();
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useFieldValueAccessor(FieldValueAccessor fieldValueAccessor) {
        this.fieldValueAccessor = fieldValueAccessor;
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useDefaultComparatorProvider() {
        this.comparatorProvider = new SameComparatorProvidingItemComparatorProvider(new NullAcceptingComparator<>(ValueComparators.immutableResultEqualsComparator(), Function.identity()));
        return this;
    }

    public DefaultObjectComparatorBuilder<ObjectType> useComparatorProvider(ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorProvider) {
        this.comparatorProvider = comparatorProvider;
        return this;
    }

    public DefaultObjectComparator<ObjectType> build() {
        final Supplier<IntermediateObjectComparisonResult> intermediateResultField = () -> new IntermediateObjectComparisonResult(copyMapFunction);

        final Function<IntermediateObjectComparisonResult, ObjectComparisonResult> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableObjectComparisonResultFunction(
                    accessSimilarValuesOfFields, 
                    accessDifferentValuesOfFields, 
                    accessComparisonResultOfFields);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultObjectComparator<>(
                intermediateResultField,
                usedResultFunction,
                comparatorProvider,
                exceptionHandler,
                comparisonFeatures,
                stopPredicate,
                fieldPredicate,
                deduplicator,
                fieldValueAccessor
        );
    }
}
