package net.mirwaldt.jcomparison.core.object.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparatorProvider;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.exception.CannotAccessFieldException;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.facade.LazySupplier;
import net.mirwaldt.jcomparison.core.object.api.FieldValueAccessor;
import net.mirwaldt.jcomparison.core.object.api.ObjectComparator;
import net.mirwaldt.jcomparison.core.object.api.ObjectComparisonResult;
import net.mirwaldt.jcomparison.core.object.api.PrimitiveSupportingFieldValueAccessor;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive.NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA;

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
public class DefaultObjectComparator<ObjectType>
        implements ObjectComparator<ObjectType> {

    private final Supplier<IntermediateObjectComparisonResult> intermediateResultField;

    // TODO: must the function always return ImmutableObjectComparisonResult? why not just an ObjectComparisonResult?
    private final Function<IntermediateObjectComparisonResult, ImmutableObjectComparisonResult> resultFunction;

    private final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorProvider;
    private final ComparisonFailedExceptionHandlingComparator exceptionHandlingComparator;

    private final EnumSet<ObjectComparator.ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateObjectComparisonResult> stopPredicate;
    private final Predicate<Field> fieldPredicate;

    private final Deduplicator deduplicator;
    private final FieldValueAccessor fieldValueAccessor;

    public DefaultObjectComparator(Supplier<IntermediateObjectComparisonResult> intermediateResultField, Function<IntermediateObjectComparisonResult, ImmutableObjectComparisonResult> resultFunction, ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorProvider, ComparisonFailedExceptionHandler exceptionHandler, EnumSet<ObjectComparator.ComparisonFeature> comparisonFeatures, Predicate<IntermediateObjectComparisonResult> stopPredicate, Predicate<Field> fieldPredicate, Deduplicator deduplicator, FieldValueAccessor fieldValueAccessor) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparatorProvider = comparatorProvider;
        this.exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator(exceptionHandler);
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;
        this.fieldPredicate = fieldPredicate;
        this.deduplicator = deduplicator;
        this.fieldValueAccessor = fieldValueAccessor;
    }

    @Override
    public ObjectComparisonResult compare(ObjectType leftObject, ObjectType rightObject, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
        try {
            final IntermediateObjectComparisonResult intermediateResult = intermediateResultField.get();

            compare(leftObject, rightObject, visitedObjectsTrace, intermediateResult);

            final ImmutableObjectComparisonResult finalResult = resultFunction.apply(intermediateResult);

            return finalResult;
        } catch (InvalidSupplyException e) {
            throw new ComparisonFailedException("Invalid supply of a new map.", e, leftObject, rightObject);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both objects.", e, leftObject, rightObject);
        }
    }

    public void compare(ObjectType leftObject, ObjectType rightObject, VisitedObjectsTrace visitedObjectsTrace, IntermediateObjectComparisonResult intermediateResult) throws ComparisonFailedException, InvalidSupplyException {
        final Class<?> type = leftObject.getClass();

        final LazySupplier<Map<Class<?>, MutablePrimitive<?>>> leftCachedMutablePrimitivesSupplier = new LazySupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));
        final LazySupplier<Map<Class<?>, MutablePrimitive<?>>> rightCachedMutablePrimitivesSupplier = new LazySupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));

        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            if(fieldPredicate.test(field)) {
                final Object leftFieldValue = accessField(fieldValueAccessor, type, field, leftObject, leftObject, rightObject, "left", leftCachedMutablePrimitivesSupplier);
                final Object rightFieldValue = accessField(fieldValueAccessor, type, field, rightObject, leftObject, rightObject, "right", rightCachedMutablePrimitivesSupplier);

                final ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> fieldComparator = comparatorProvider.provideComparator(field, leftFieldValue, rightFieldValue);
                final ComparisonResult<?, ?, ?> comparisonResult = exceptionHandlingComparator.compare(fieldComparator, leftFieldValue, rightFieldValue, () -> resultFunction.apply(intermediateResult), visitedObjectsTrace);

                if (handleComparisonResult(field, comparisonResult, intermediateResult)) {
                    break;
                }
            }
        }
    }

    private Object accessField(FieldValueAccessor fieldValueAccessor, Class<?> type, Field targetField, ObjectType targetObject, ObjectType leftObject, ObjectType rightObject, String side, Supplier<Map<Class<?>, MutablePrimitive<?>>> cachedMutablePrimitivesSupplier) throws ComparisonFailedException {
        try {
            if (fieldValueAccessor instanceof PrimitiveSupportingFieldValueAccessor) {
                final PrimitiveSupportingFieldValueAccessor primitiveSupportingFieldValueAccessor = (PrimitiveSupportingFieldValueAccessor) fieldValueAccessor;
                return primitiveSupportingFieldValueAccessor.accessFieldValue(type, targetField, targetObject, cachedMutablePrimitivesSupplier, deduplicator);
            } else {
                return fieldValueAccessor.accessFieldValue(type, targetField, targetObject);
            }
        } catch (CannotAccessFieldException e) {
            throw new ComparisonFailedException("Cannot access " + side + " object's field '" + targetField + "'!", e, leftObject, rightObject);
        }
    }

    @SuppressWarnings("unchecked")
    private boolean handleComparisonResult(Field field, final ComparisonResult<?, ?, ?> comparisonResult, IntermediateObjectComparisonResult intermediateResult) throws ComparisonFailedException, InvalidSupplyException {
        if (comparisonResult == ComparisonResults.skipComparisonResult()) {
            return false;
        } else if (comparisonResult == ComparisonResults.stopComparisonResult()) {
            return true;
        } else if (comparisonResult instanceof ValueComparisonResult) {
            final ValueComparisonResult<Object> valueComparisonResult = (ValueComparisonResult<Object>) comparisonResult;
            return handleValueComparisonResult(field, valueComparisonResult, intermediateResult);
        } else {
            if(comparisonFeatures.contains(ComparisonFeature.COMPARISON_RESULTS_OF_FIELDS)) {
                intermediateResult.writeComparisonResultOfFields().put(field, comparisonResult);
            }

            return stopPredicate.test(intermediateResult);
        }
    }

    private boolean handleValueComparisonResult(Field field, final ValueComparisonResult<Object> valueComparisonResult, IntermediateObjectComparisonResult intermediateResult) throws InvalidSupplyException {
        if (valueComparisonResult.hasSimilarity()) {
            if (comparisonFeatures.contains(ComparisonFeature.SIMILAR_VALUES_OF_FIELDS)) {
                intermediateResult.writeSimilarSimilarValuesOfFields().put(field, valueComparisonResult.getSimilarity());
            }

            return stopPredicate.test(intermediateResult);
        } else if (valueComparisonResult.hasDifference()) {
            if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_VALUES_OF_FIELDS)) {
                intermediateResult.writeDifferentValuesOfFields().put(field, valueComparisonResult.getDifference());
            }

            return stopPredicate.test(intermediateResult);
        }
        return false;
    }
}
