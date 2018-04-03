package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;
import net.mirwaldt.jcomparison.core.util.ArrayAccessor;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.util.supplier.impl.LazyAlwaysSupplyingTheSameInstanceSupplier;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntArrayImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import net.mirwaldt.jcomparison.core.value.impl.ImmutableValueSimilarity;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.*;

import static net.mirwaldt.jcomparison.core.array.api.ArrayComparator.ComparisonFeature.*;
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
public class DefaultArrayComparator<ArrayType> implements ArrayComparator<ArrayType> {
    private final Deduplicator deduplicator;
    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementComparator;
    private final UnaryOperator<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> preComparatorFunction;
    
    private final Supplier<IntermediateArrayComparisonResult> intermediateResultField;
    private final BiFunction<IntermediateArrayComparisonResult, Integer, ArrayComparisonResult> resultFunction;

    private final ComparisonFailedExceptionHandler exceptionHandler;
    
    private final EnumSet<ComparisonFeature> comparisonFeatures;
    private final Predicate<Object> elementsFilter;

    private final Predicate<int[]> skipIndexPredicate;
    private final Predicate<int[]> stopIndexPredicate;
    private final Predicate<IntermediateArrayComparisonResult> stopResultPredicate;

    private final Function<Object, Object> arrayWrapperFunction;
    
    /**
     * optimizations
     */
    private final ComparatorOptions comparatorOptions;
    
    public DefaultArrayComparator(
            Deduplicator deduplicator,
            ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementComparator,
            UnaryOperator<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> preComparatorFunction,
            Supplier<IntermediateArrayComparisonResult> intermediateResultField,
            BiFunction<IntermediateArrayComparisonResult, Integer, ArrayComparisonResult> resultFunction,
            EnumSet<ComparisonFeature> comparisonFeatures,
            Predicate<Object> elementsFilter,
            Predicate<int[]> skipIndexPredicate,
            Predicate<int[]> stopIndexPredicate, 
            Predicate<IntermediateArrayComparisonResult> stopResultPredicate,
            ComparisonFailedExceptionHandler exceptionHandler,
            Function<Object, Object> arrayWrapperFunction) {
        this.deduplicator = deduplicator;
        this.elementComparator = elementComparator;
        this.preComparatorFunction = preComparatorFunction;
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparisonFeatures = comparisonFeatures;
        this.elementsFilter = elementsFilter;
        this.skipIndexPredicate = skipIndexPredicate;
        this.stopIndexPredicate = stopIndexPredicate;
        this.stopResultPredicate = stopResultPredicate;
        this.exceptionHandler = exceptionHandler;
        this.arrayWrapperFunction = arrayWrapperFunction;
        
        this.comparatorOptions = new ComparatorOptions(true,
                comparisonFeatures.stream().anyMatch(ComparisonFeature::needsValueComparison),
                comparisonFeatures.stream().anyMatch(ComparisonFeature::needsNonValueComparison)
        );
    }

    @Override
    public ArrayComparisonResult compare(ArrayType leftArrayObject, 
                                         ArrayType rightArrayObject, 
                                         VisitedObjectsTrace visitedObjectsTrace,
                                         ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        try {
            final IntermediateArrayComparisonResult intermediateResult = intermediateResultField.get();

            final String typeString = leftArrayObject.getClass().getName();
            final int[] arrayCursor;
            if (typeString.startsWith("[")) {
                final int indexOfLastOpeningBracket = typeString.lastIndexOf("[");
                final int dimension = indexOfLastOpeningBracket + 1;
                arrayCursor = new int[dimension];
            } else {
                throw new ComparisonFailedException("leftObjectArray is no array.", leftArrayObject, rightArrayObject);
            }

            Arrays.fill(arrayCursor, -1);
            new InternalArrayComparator().compare(leftArrayObject, rightArrayObject, visitedObjectsTrace, comparatorOptions, arrayCursor, intermediateResult);

            final ArrayComparisonResult arrayComparisonResult = resultFunction.apply(intermediateResult, arrayCursor.length);

            return arrayComparisonResult;
        } catch (ComparisonFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both arrays.", e, leftArrayObject, rightArrayObject);
        }
    }

    private Supplier<ComparisonResult<?,?,?>> createSafeResultSupplier(IntermediateArrayComparisonResult intermediateResult, int arrayDimension) {
        return () -> resultFunction.apply(intermediateResult, arrayDimension);
    }

    class InternalArrayComparator implements ItemComparator<Object, ComparisonResult<?,?,?>> {
        private final LazyAlwaysSupplyingTheSameInstanceSupplier<Map<Class<?>, MutablePrimitive<?>>> leftCachedMutablePrimitivesSupplier = 
                new LazyAlwaysSupplyingTheSameInstanceSupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));
        private final LazyAlwaysSupplyingTheSameInstanceSupplier<Map<Class<?>, MutablePrimitive<?>>> rightCachedMutablePrimitivesSupplier = 
                new LazyAlwaysSupplyingTheSameInstanceSupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));

        /**
         * used for copies that are submitted to the user for filtering
         */
        private final LazyAlwaysSupplyingTheSameInstanceSupplier<Map<Class<?>, MutablePrimitive<?>>> leftCachedMutablePrimitivesCopySupplier = 
                new LazyAlwaysSupplyingTheSameInstanceSupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));
        private final LazyAlwaysSupplyingTheSameInstanceSupplier<Map<Class<?>, MutablePrimitive<?>>> rightCachedMutablePrimitivesCopySupplier = 
                new LazyAlwaysSupplyingTheSameInstanceSupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));

        private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> cycleProtectedComparator = preComparatorFunction.apply(this);
        private final ComparisonFailedExceptionHandlingComparator<Object> exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator<>(exceptionHandler);
        
        private int maxDimensions;
        private int[] arrayCursor;
        private int[][] allCurrentArrayPositionForPredicates;
        private VisitedObjectsTrace visitedObjectsTrace;
        private IntermediateArrayComparisonResult intermediateResult;
        private int currentDimension;
        
        public void compare(Object leftArrayObject, 
                            Object rightArrayObject, 
                            VisitedObjectsTrace visitedObjectsTrace,
                            ComparatorOptions comparatorOptions, 
                            int[] arrayCursor, 
                            IntermediateArrayComparisonResult intermediateResult) throws Exception {
            this.visitedObjectsTrace = visitedObjectsTrace;
            this.arrayCursor = arrayCursor;
            this.intermediateResult = intermediateResult;

            maxDimensions = arrayCursor.length;
            allCurrentArrayPositionForPredicates = new int[maxDimensions][];

            for (int i = 1; i <= maxDimensions; i++) {
                allCurrentArrayPositionForPredicates[i - 1] = new int[i];
                Arrays.fill(allCurrentArrayPositionForPredicates[i - 1], -1);
            }

            currentDimension = 1;

            exceptionHandlingComparator.setComparisonResultSafeSupplier(createSafeResultSupplier(intermediateResult, maxDimensions));
            
            compare(leftArrayObject, rightArrayObject, visitedObjectsTrace, comparatorOptions);
        }

        @Override
        public ComparisonResult<?,?,?> compare(Object leftObject, 
                                               Object rightObject, 
                                               VisitedObjectsTrace visitedObjectsTrace, 
                                               ComparatorOptions comparatorOptions) throws ComparisonFailedException {
            try {
                final int lengthOfLeftSubArray = ArrayAccessor.getLength(leftObject);
                final int lengthOfRightSubArray = ArrayAccessor.getLength(rightObject);
                final int commonLength = Math.min(lengthOfLeftSubArray, lengthOfRightSubArray);

                if (1 < currentDimension && lengthOfLeftSubArray == 0 && lengthOfRightSubArray == 0) { // both arrays empty
                    final int[] currentArrayPositionForPredicates = provideCurrentArrayPositionForPredicate(allCurrentArrayPositionForPredicates, arrayCursor, currentDimension - 1);
                    if (stopIndexPredicate.test(currentArrayPositionForPredicates)) {
                        return BasicComparisonResults.stopComparisonResult();
                    }
                    if (skipIndexPredicate.test(currentArrayPositionForPredicates)) {
                        return BasicComparisonResults.skipComparisonResult();
                    }


                    final Object leftElementCopy = copyIfMutablePrimitive(leftObject, leftCachedMutablePrimitivesCopySupplier);
                    final Object rightElementCopy = copyIfMutablePrimitive(rightObject, rightCachedMutablePrimitivesCopySupplier);
                    if (handleEmptyArraysPair(leftObject, leftElementCopy, rightElementCopy, currentDimension - 1)) {
                        return BasicComparisonResults.stopComparisonResult();
                    }
                } else {
                    // handle intersection
                    for (int index = 0; index < commonLength; index++) {
                        arrayCursor[currentDimension - 1] = index;

                        final int[] currentArrayPositionForPredicates = provideCurrentArrayPositionForPredicate(allCurrentArrayPositionForPredicates, arrayCursor, currentDimension - 1);
                        if (stopIndexPredicate.test(currentArrayPositionForPredicates)) {
                            return BasicComparisonResults.stopComparisonResult();
                        }
                        if (skipIndexPredicate.test(currentArrayPositionForPredicates)) {
                            continue;
                        }

                        final Object leftElement = getObjectFromArray(leftObject, index, leftCachedMutablePrimitivesSupplier);
                        final Object rightElement = getObjectFromArray(rightObject, index, rightCachedMutablePrimitivesSupplier);

                        if (currentDimension < maxDimensions && haveSubArrays(leftElement, rightElement)) {
                            currentDimension++;
                            
                            exceptionHandlingComparator.setComparator(cycleProtectedComparator);
                            final ComparisonResult<?,?,?> comparisonResult = exceptionHandlingComparator.compare(leftElement, rightElement, visitedObjectsTrace, DefaultArrayComparator.this.comparatorOptions);
                            if (comparisonResult == BasicComparisonResults.stopComparisonResult()) {
                                return BasicComparisonResults.stopComparisonResult();
                            }
                            
                            currentDimension--;
                        } else {
                            final Object leftElementCopy = copyIfMutablePrimitive(leftElement, leftCachedMutablePrimitivesCopySupplier);
                            final Object rightElementCopy = copyIfMutablePrimitive(rightElement, rightCachedMutablePrimitivesCopySupplier);
                            if (compareElements(leftElement, rightElement, comparatorOptions, leftElementCopy, rightElementCopy, elementComparator)) {
                                return BasicComparisonResults.stopComparisonResult();
                            }
                        }
                    }

                    // handle additional left elements
                    if (commonLength < lengthOfLeftSubArray) {
                        for (int index = commonLength; index < lengthOfLeftSubArray; index++) {
                            arrayCursor[currentDimension - 1] = index;

                            final int[] currentArrayPositionForPredicates = provideCurrentArrayPositionForPredicate(allCurrentArrayPositionForPredicates, arrayCursor, currentDimension - 1);
                            if (stopIndexPredicate.test(currentArrayPositionForPredicates)) {
                                return BasicComparisonResults.stopComparisonResult();
                            }
                            if (skipIndexPredicate.test(currentArrayPositionForPredicates)) {
                                continue;
                            }

                            final Object leftElement = getObjectFromArray(leftObject, index, leftCachedMutablePrimitivesSupplier);
                            final Object leftElementCopy = copyIfMutablePrimitive(leftElement, leftCachedMutablePrimitivesCopySupplier);
                            if (handleLeftElement(currentDimension, leftElement, leftElementCopy)) {
                                return BasicComparisonResults.stopComparisonResult();
                            }
                        }
                    }

                    // handle additional right elements
                    if (commonLength < lengthOfRightSubArray) {
                        for (int index = commonLength; index < lengthOfRightSubArray; index++) {
                            arrayCursor[currentDimension - 1] = index;

                            final int[] currentArrayPositionForPredicates = provideCurrentArrayPositionForPredicate(allCurrentArrayPositionForPredicates, arrayCursor, currentDimension - 1);
                            if (stopIndexPredicate.test(currentArrayPositionForPredicates)) {
                                return BasicComparisonResults.stopComparisonResult();
                            }
                            if (skipIndexPredicate.test(currentArrayPositionForPredicates)) {
                                continue;
                            }

                            final Object rightElement = getObjectFromArray(rightObject, index, rightCachedMutablePrimitivesSupplier);
                            final Object rightElementCopy = copyIfMutablePrimitive(rightElement, rightCachedMutablePrimitivesCopySupplier);
                            if (handleRightElement(currentDimension, rightElement, rightElementCopy)) {
                                return BasicComparisonResults.stopComparisonResult();
                            }
                        }
                    }
                }

                arrayCursor[currentDimension - 1] = -1;
                return BasicComparisonResults.emptyNonValueComparisonResult();
            } catch (ComparisonFailedException e) {
                throw e;
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot traverse array", e, leftObject, rightObject);
            }
        }

        private Object copyIfMutablePrimitive(Object potentialMutablePrimitive, LazyAlwaysSupplyingTheSameInstanceSupplier<Map<Class<?>, MutablePrimitive<?>>> cachedMutablePrimitivesCopySupplier) {
            if(potentialMutablePrimitive instanceof MutablePrimitive) {
                MutablePrimitive<?> mutablePrimitive = (MutablePrimitive<?>) potentialMutablePrimitive;
                return mutablePrimitive.copy(cachedMutablePrimitivesCopySupplier.get());
            } else {
                return potentialMutablePrimitive;
            }
        }

        private boolean haveSubArrays(Object leftObject, Object rightObject) {
            return leftObject != null && rightObject != null && leftObject.getClass().isArray() && rightObject.getClass().isArray();
        }

        private int[] provideCurrentArrayPositionForPredicate(int[][] currentArrayPositionForPredicates, int[] currentArrayPosition, int dimension) {
            if (-1 < dimension) {
                if (dimension < currentArrayPosition.length) {
                    final int[] currentArrayPositionForPredicate = currentArrayPositionForPredicates[dimension];

                    System.arraycopy(currentArrayPosition, 0, currentArrayPositionForPredicate, 0, currentArrayPositionForPredicate.length);

                    return currentArrayPositionForPredicate;
                } else {
                    throw new IllegalArgumentException("Parameter value for dimension (=" + dimension + ") must be lower than the max possible dimension (=" + currentArrayPosition.length + ").");
                }
            } else {
                throw new IllegalArgumentException("Parameter value for dimension (=" + dimension + ") must be positive or 0.");
            }
        }

        private boolean compareElements(Object currentLeftObject, 
                                        Object currentRightObject,
                                        ComparatorOptions comparatorOptions, 
                                        Object currentLeftObjectCopy, 
                                        Object currentRightObjectCopy, 
                                        ItemComparator<Object, ? extends ComparisonResult<?,?,?>> itemComparator) throws Exception {
            if (elementsFilter.test(currentLeftObjectCopy) && elementsFilter.test(currentRightObjectCopy)) {
                if (compare(currentLeftObject, currentRightObject, comparatorOptions, itemComparator)) {
                    return true;
                }
            }
            return false;
        }

        private boolean compare(Object currentLeftObject, 
                                Object currentRightObject,
                                ComparatorOptions comparatorOptions, 
                                ItemComparator<Object, ? extends ComparisonResult<?,?,?>> itemComparator) throws ComparisonFailedException {
            exceptionHandlingComparator.setComparator(itemComparator);
            final ComparisonResult<?,?,?> comparisonResult = exceptionHandlingComparator.compare(currentLeftObject, currentRightObject, visitedObjectsTrace, DefaultArrayComparator.this.comparatorOptions);
            
            final ImmutableIntList arrayPosition = createArrayPosition(arrayCursor, maxDimensions, currentDimension);
            if (handleComparisonResult(intermediateResult, comparisonResult, arrayPosition)) {
                return true;
            }
            return false;
        }

        private boolean handleRightElement(int currentDimension, Object element, Object elementCopy) throws Exception {
            if (comparisonFeatures.contains(ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY)) {
                if (elementsFilter.test(elementCopy)) {
                    final ImmutableIntList arrayPosition = createArrayPosition(arrayCursor, maxDimensions, currentDimension);
                    if (element instanceof MutablePrimitive) {
                        intermediateResult.writeAdditionalElementsOnlyInRightArray().put(arrayPosition, ((MutablePrimitive) element).get());
                    } else if(element != null && element.getClass().isArray()) {
                        intermediateResult.writeAdditionalElementsOnlyInRightArray().put(arrayPosition, arrayWrapperFunction.apply(element));
                    } else {
                        intermediateResult.writeAdditionalElementsOnlyInRightArray().put(arrayPosition, element);
                    }

                    return stopResultPredicate.test(intermediateResult);
                }
            }
            return false;
        }

        private boolean handleLeftElement(int currentDimension, Object element, Object elementCopy) throws Exception {
            if (comparisonFeatures.contains(ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY)) {
                if (elementsFilter.test(elementCopy)) {
                    final ImmutableIntList arrayPosition = createArrayPosition(arrayCursor, maxDimensions, currentDimension);
                    if (element instanceof MutablePrimitive) {
                        intermediateResult.writeAdditionalElementsOnlyInLeftArray().put(arrayPosition, ((MutablePrimitive) element).get());
                    } else if(element != null && element.getClass().isArray()) {
                        intermediateResult.writeAdditionalElementsOnlyInLeftArray().put(arrayPosition, arrayWrapperFunction.apply(element));
                    } else {
                        intermediateResult.writeAdditionalElementsOnlyInLeftArray().put(arrayPosition, element);
                    }

                    return stopResultPredicate.test(intermediateResult);
                }
            }
            return false;
        }

        private Object getObjectFromArray(Object currentObject, int index, LazyAlwaysSupplyingTheSameInstanceSupplier<Map<Class<?>, MutablePrimitive<?>>> cachedMutablePrimitivesSupplier) {
            final Object element;
            if (cachedMutablePrimitivesSupplier == null) {
                element = ArrayAccessor.getElementAtIndex(currentObject, index);
            } else {
                element = ArrayAccessor.getElementAtIndex(currentObject, index, cachedMutablePrimitivesSupplier, deduplicator);
            }
            return element;
        }

        private boolean handleEmptyArraysPair(Object currentLeftObject, Object currentLeftObjectCopy, Object currentRightObjectCopy, int currentDimension) throws Exception {
            if (comparisonFeatures.contains(SIMILAR_ELEMENTS)) {
                if (elementsFilter.test(currentLeftObjectCopy) && elementsFilter.test(currentRightObjectCopy)) {
                    final ImmutableIntList arrayPosition = createArrayPosition(arrayCursor, maxDimensions, currentDimension);

                    final ValueSimilarity<?> valueSimilarity = ImmutableValueSimilarity.createValueSimilarityWithValue(currentLeftObject);
                    intermediateResult.writeSimilarElements().put(arrayPosition, valueSimilarity);

                    return stopResultPredicate.test(intermediateResult);
                }
            }
            return false;
        }

        private ImmutableIntList createArrayPosition(int[] arrayPosition, int arrayDimension, int maxDimension) {
            if (3 <= arrayDimension) {
                if(arrayPosition[1] == -1) {
                    return new ImmutableOneElementImmutableIntList(arrayPosition[0]);
                } else if(arrayPosition[2] == -1) {
                    return new ImmutableTwoElementsImmutableIntList(arrayPosition[0], arrayPosition[1]);
                } else {
                    final int[] indexes = Arrays.copyOfRange(arrayPosition, 0, maxDimension);
                    return new ImmutableIntArrayImmutableIntList(indexes);
                }
            } else if (2 == arrayDimension) {
                if(arrayPosition[1] == -1) {
                    return new ImmutableOneElementImmutableIntList(arrayPosition[0]);
                } else {
                    return new ImmutableTwoElementsImmutableIntList(arrayPosition[0], arrayPosition[1]);
                }
            } else {
                return new ImmutableOneElementImmutableIntList(arrayPosition[0]);
            }
        }

        private boolean handleComparisonResult(IntermediateArrayComparisonResult intermediateResult, 
                                               ComparisonResult<?,?,?> comparisonResult, 
                                               ImmutableIntList arrayPosition) throws ComparisonFailedException {
            if (comparisonResult instanceof ValueComparisonResult) {
                final ValueComparisonResult<Object> valueComparisonResult = (ValueComparisonResult<Object>) comparisonResult;
                if (valueComparisonResult.hasSimilarities()) {
                    if (comparisonFeatures.contains(SIMILAR_ELEMENTS)) {
                        intermediateResult.writeSimilarElements().put(arrayPosition, valueComparisonResult.getSimilarities());
                    }
                } else if (valueComparisonResult.hasDifferences()) {
                    if (comparisonFeatures.contains(DIFFERENT_ELEMENTS)) {
                        intermediateResult.writeDifferentValues().put(arrayPosition, valueComparisonResult.getDifferences());
                    }
                }

                return stopResultPredicate.test(intermediateResult);
            } else if (comparisonResult == BasicComparisonResults.skipComparisonResult()) {
                return false;
            } else if (comparisonResult == BasicComparisonResults.stopComparisonResult()) {
                return true;
            } else {
                if (comparisonFeatures.contains(COMPARE_ELEMENTS_DEEP)) {
                    intermediateResult.writeComparisonResults().put(arrayPosition, comparisonResult);
                }

                return stopResultPredicate.test(intermediateResult);
            }
        }
    }
}
