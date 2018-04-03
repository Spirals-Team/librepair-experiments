package net.mirwaldt.jcomparison.object.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;
import net.mirwaldt.jcomparison.core.util.supplier.impl.LazyAlwaysSupplyingTheSameInstanceSupplier;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.exception.CannotAccessFieldException;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;
import net.mirwaldt.jcomparison.object.api.FieldValueAccessor;
import net.mirwaldt.jcomparison.object.api.ObjectComparator;
import net.mirwaldt.jcomparison.object.api.ObjectComparisonResult;
import net.mirwaldt.jcomparison.object.api.PrimitiveSupportingFieldValueAccessor;
import net.mirwaldt.jcomparison.provider.api.ComparatorProvider;

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

    private final Function<IntermediateObjectComparisonResult, ObjectComparisonResult> resultFunction;

    private final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorProvider;
    private final ComparisonFailedExceptionHandler exceptionHandler;

    private final EnumSet<ObjectComparator.ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateObjectComparisonResult> stopPredicate;
    private final Predicate<Field> fieldPredicate;

    private final Deduplicator deduplicator;
    private final FieldValueAccessor fieldValueAccessor;

    public DefaultObjectComparator(
            Supplier<IntermediateObjectComparisonResult> intermediateResultField, 
            Function<IntermediateObjectComparisonResult, ObjectComparisonResult> resultFunction, 
            ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorProvider, 
            ComparisonFailedExceptionHandler exceptionHandler, 
            EnumSet<ObjectComparator.ComparisonFeature> comparisonFeatures, 
            Predicate<IntermediateObjectComparisonResult> stopPredicate, 
            Predicate<Field> fieldPredicate, 
            Deduplicator deduplicator, 
            FieldValueAccessor fieldValueAccessor) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparatorProvider = comparatorProvider;
        this.exceptionHandler = exceptionHandler;
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;
        this.fieldPredicate = fieldPredicate;
        this.deduplicator = deduplicator;
        this.fieldValueAccessor = fieldValueAccessor;
    }

    @Override
    public ObjectComparisonResult compare(ObjectType leftObject, 
                                          ObjectType rightObject, 
                                          VisitedObjectsTrace visitedObjectsTrace,
                                          ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        try {
            final IntermediateObjectComparisonResult intermediateResult = intermediateResultField.get();

            final ComparisonFailedExceptionHandlingComparator<Object> exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator<>(exceptionHandler);
            exceptionHandlingComparator.setComparisonResultSafeSupplier(() -> resultFunction.apply(intermediateResult));
            
            compare(leftObject, rightObject, comparatorOptions, visitedObjectsTrace, intermediateResult, exceptionHandlingComparator);

            return resultFunction.apply(intermediateResult);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both objects.", e, leftObject, rightObject);
        }
    }

    public void compare(ObjectType leftObject, 
                        ObjectType rightObject,
                        ComparatorOptions comparatorOptions,
                        VisitedObjectsTrace visitedObjectsTrace, 
                        IntermediateObjectComparisonResult intermediateResult,
                        ComparisonFailedExceptionHandlingComparator<Object> exceptionHandlingComparator    
    ) throws ComparisonFailedException {
        final Class<?> type = leftObject.getClass();

        final LazyAlwaysSupplyingTheSameInstanceSupplier<Map<Class<?>, MutablePrimitive<?>>> leftCachedMutablePrimitivesSupplier = new LazyAlwaysSupplyingTheSameInstanceSupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));
        final LazyAlwaysSupplyingTheSameInstanceSupplier<Map<Class<?>, MutablePrimitive<?>>> rightCachedMutablePrimitivesSupplier = new LazyAlwaysSupplyingTheSameInstanceSupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));

        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            if(fieldPredicate.test(field)) {
                final Object leftFieldValue = accessField(fieldValueAccessor, type, field, leftObject, leftObject, rightObject, "left", leftCachedMutablePrimitivesSupplier);
                final Object rightFieldValue = accessField(fieldValueAccessor, type, field, rightObject, leftObject, rightObject, "right", rightCachedMutablePrimitivesSupplier);

                final ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> fieldComparator = comparatorProvider.provideComparator(field, leftFieldValue, rightFieldValue);
                exceptionHandlingComparator.setComparator(fieldComparator);
                final ComparisonResult<?, ?, ?> comparisonResult = exceptionHandlingComparator.compare(leftFieldValue, rightFieldValue, visitedObjectsTrace, comparatorOptions);

                if (handleComparisonResult(field, comparisonResult, intermediateResult)) {
                    break;
                }
            }
        }
    }

    private Object accessField(FieldValueAccessor fieldValueAccessor, 
                               Class<?> type, 
                               Field targetField, 
                               ObjectType targetObject, 
                               ObjectType leftObject, 
                               ObjectType rightObject, 
                               String side, 
                               Supplier<Map<Class<?>, MutablePrimitive<?>>> cachedMutablePrimitivesSupplier) throws ComparisonFailedException {
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
    private boolean handleComparisonResult(Field field, 
                                           final ComparisonResult<?, ?, ?> comparisonResult, 
                                           IntermediateObjectComparisonResult intermediateResult) throws ComparisonFailedException {
        if (comparisonResult == BasicComparisonResults.skipComparisonResult()) {
            return false;
        } else if (comparisonResult == BasicComparisonResults.stopComparisonResult()) {
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

    private boolean handleValueComparisonResult(Field field, 
                                                final ValueComparisonResult<Object> valueComparisonResult, 
                                                IntermediateObjectComparisonResult intermediateResult) {
        if (valueComparisonResult.hasSimilarities()) {
            if (comparisonFeatures.contains(ComparisonFeature.SIMILAR_VALUES_OF_FIELDS)) {
                intermediateResult.writeSimilarSimilarValuesOfFields().put(field, valueComparisonResult.getSimilarities());
            }

            return stopPredicate.test(intermediateResult);
        } else if (valueComparisonResult.hasDifferences()) {
            if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_VALUES_OF_FIELDS)) {
                intermediateResult.writeDifferentValuesOfFields().put(field, valueComparisonResult.getDifferences());
            }

            return stopPredicate.test(intermediateResult);
        }
        return false;
    }
}
