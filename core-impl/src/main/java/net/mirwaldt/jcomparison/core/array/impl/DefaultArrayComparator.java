package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.util.ArrayAccessor;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.facade.LazySupplier;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntArrayImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
    private final Function<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>, ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> cycleProtectingPreComparatorFunction;
    private final ComparisonFailedExceptionHandlingComparator exceptionHandlingComparator;

    private final Supplier<IntermediateArrayComparisonResult> intermediateResultField;
    private final BiFunction<IntermediateArrayComparisonResult, Integer, ArrayComparisonResult> resultFunction;

    private final EnumSet<ComparisonFeature> comparisonFeatures;
    private final Predicate<Object> elementsFilter;

    private final Predicate<int[]> skipIndexPredicate;
    private final Predicate<int[]> stopIndexPredicate;
    private final Predicate<IntermediateArrayComparisonResult> stopResultPredicate;

    public DefaultArrayComparator(
            Deduplicator deduplicator,
            ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementComparator,
            Function<ItemComparator<Object, ? extends ComparisonResult<?,?,?>>, ItemComparator<Object, ? extends ComparisonResult<?,?,?>>> cycleProtectingPreComparatorFunction,
            Supplier<IntermediateArrayComparisonResult> intermediateResultField,
            BiFunction<IntermediateArrayComparisonResult, Integer, ArrayComparisonResult> resultFunction,
            EnumSet<ComparisonFeature> comparisonFeatures,
            Predicate<Object> elementsFilter,
            Predicate<int[]> skipIndexPredicate,
            Predicate<int[]> stopIndexPredicate, Predicate<IntermediateArrayComparisonResult> stopResultPredicate,
            ComparisonFailedExceptionHandler exceptionHandler) {
        this.deduplicator = deduplicator;
        this.elementComparator = elementComparator;
        this.cycleProtectingPreComparatorFunction = cycleProtectingPreComparatorFunction;
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparisonFeatures = comparisonFeatures;
        this.elementsFilter = elementsFilter;
        this.skipIndexPredicate = skipIndexPredicate;
        this.stopIndexPredicate = stopIndexPredicate;
        this.stopResultPredicate = stopResultPredicate;
        this.exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator(exceptionHandler);
    }

    @Override
    public ArrayComparisonResult compare(ArrayType leftArrayObject, ArrayType rightArrayObject, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
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
            new InternalArrayComparator().compare(leftArrayObject, rightArrayObject, visitedObjectsTrace, arrayCursor, intermediateResult);

            final ArrayComparisonResult arrayComparisonResult = resultFunction.apply(intermediateResult, arrayCursor.length);

            return arrayComparisonResult;
        } catch (InvalidSupplyException e) {
            throw new ComparisonFailedException("Invalid supply of a new map.", e, leftArrayObject, rightArrayObject);
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
        private final LazySupplier<Map<Class<?>, MutablePrimitive<?>>> leftCachedMutablePrimitivesSupplier = new LazySupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));
        private final LazySupplier<Map<Class<?>, MutablePrimitive<?>>> rightCachedMutablePrimitivesSupplier = new LazySupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));

        /**
         * used for copies that are submitted to the user for filtering
         */
        private final LazySupplier<Map<Class<?>, MutablePrimitive<?>>> leftCachedMutablePrimitivesCopySupplier = new LazySupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));
        private final LazySupplier<Map<Class<?>, MutablePrimitive<?>>> rightCachedMutablePrimitivesCopySupplier = new LazySupplier<>(() -> new HashMap<>(NUMBER_OF_PRIMITIVE_TYPES_IN_JAVA));

        private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> cycleProtectedComparator = cycleProtectingPreComparatorFunction.apply(this);

        private int maxDimensions;
        private int[] arrayCursor;
        private int[][] allCurrentArrayPositionForPredicates;
        private VisitedObjectsTrace visitedObjectsTrace;
        private IntermediateArrayComparisonResult intermediateResult;
        private int currentDimension;

        public void compare(Object leftArrayObject, Object rightArrayObject, VisitedObjectsTrace visitedObjectsTrace, int[] arrayCursor, IntermediateArrayComparisonResult intermediateResult) throws Exception {
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
            compare(leftArrayObject, rightArrayObject, visitedObjectsTrace);
        }

        @Override
        public ComparisonResult<?,?,?> compare(Object leftObject, Object rightObject, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
            try {
                final int lengthOfLeftSubArray = ArrayAccessor.getLength(leftObject);
                final int lengthOfRightSubArray = ArrayAccessor.getLength(rightObject);
                final int commonLength = Math.min(lengthOfLeftSubArray, lengthOfRightSubArray);

                if (1 < currentDimension && lengthOfLeftSubArray == 0 && lengthOfRightSubArray == 0) { // both arrays empty
                    final int[] currentArrayPositionForPredicates = provideCurrentArrayPositionForPredicate(allCurrentArrayPositionForPredicates, arrayCursor, currentDimension - 1);
                    if (stopIndexPredicate.test(currentArrayPositionForPredicates)) {
                        return ComparisonResults.stopComparisonResult();
                    }
                    if (skipIndexPredicate.test(currentArrayPositionForPredicates)) {
                        return ComparisonResults.skipComparisonResult();
                    }


                    final Object leftElementCopy = copyIfMutablePrimitive(leftObject, leftCachedMutablePrimitivesCopySupplier);
                    final Object rightElementCopy = copyIfMutablePrimitive(rightObject, rightCachedMutablePrimitivesCopySupplier);
                    if (handleEmptyArraysPair(leftObject, leftElementCopy, rightElementCopy, currentDimension - 1)) {
                        return ComparisonResults.stopComparisonResult();
                    }
                } else {
                    // handle intersection
                    for (int index = 0; index < commonLength; index++) {
                        arrayCursor[currentDimension - 1] = index;

                        final int[] currentArrayPositionForPredicates = provideCurrentArrayPositionForPredicate(allCurrentArrayPositionForPredicates, arrayCursor, currentDimension - 1);
                        if (stopIndexPredicate.test(currentArrayPositionForPredicates)) {
                            return ComparisonResults.stopComparisonResult();
                        }
                        if (skipIndexPredicate.test(currentArrayPositionForPredicates)) {
                            continue;
                        }

                        final Object leftElement = getObjectFromArray(leftObject, index, leftCachedMutablePrimitivesSupplier);
                        final Object rightElement = getObjectFromArray(rightObject, index, rightCachedMutablePrimitivesSupplier);

                        if (currentDimension < maxDimensions && haveSubArrays(leftElement, rightElement)) {
                            currentDimension++;
                            final ComparisonResult<?,?,?> comparisonResult = exceptionHandlingComparator.compare(cycleProtectedComparator, leftElement, rightElement, createSafeResultSupplier(intermediateResult, maxDimensions), visitedObjectsTrace);
                            if (comparisonResult == ComparisonResults.stopComparisonResult()) {
                                return ComparisonResults.stopComparisonResult();
                            }
                            currentDimension--;
                        } else {
                            final Object leftElementCopy = copyIfMutablePrimitive(leftElement, leftCachedMutablePrimitivesCopySupplier);
                            final Object rightElementCopy = copyIfMutablePrimitive(rightElement, rightCachedMutablePrimitivesCopySupplier);
                            if (compareElements(leftElement, rightElement, leftElementCopy, rightElementCopy, elementComparator)) {
                                return ComparisonResults.stopComparisonResult();
                            }
                        }
                    }

                    // handle additional left elements
                    if (commonLength < lengthOfLeftSubArray) {
                        for (int index = commonLength; index < lengthOfLeftSubArray; index++) {
                            arrayCursor[currentDimension - 1] = index;

                            final int[] currentArrayPositionForPredicates = provideCurrentArrayPositionForPredicate(allCurrentArrayPositionForPredicates, arrayCursor, currentDimension - 1);
                            if (stopIndexPredicate.test(currentArrayPositionForPredicates)) {
                                return ComparisonResults.stopComparisonResult();
                            }
                            if (skipIndexPredicate.test(currentArrayPositionForPredicates)) {
                                continue;
                            }

                            final Object leftElement = getObjectFromArray(leftObject, index, leftCachedMutablePrimitivesSupplier);
                            final Object leftElementCopy = copyIfMutablePrimitive(leftElement, leftCachedMutablePrimitivesCopySupplier);
                            if (handleLeftElement(currentDimension, leftElement, leftElementCopy)) {
                                return ComparisonResults.stopComparisonResult();
                            }
                        }
                    }

                    // handle additional right elements
                    if (commonLength < lengthOfRightSubArray) {
                        for (int index = commonLength; index < lengthOfRightSubArray; index++) {
                            arrayCursor[currentDimension - 1] = index;

                            final int[] currentArrayPositionForPredicates = provideCurrentArrayPositionForPredicate(allCurrentArrayPositionForPredicates, arrayCursor, currentDimension - 1);
                            if (stopIndexPredicate.test(currentArrayPositionForPredicates)) {
                                return ComparisonResults.stopComparisonResult();
                            }
                            if (skipIndexPredicate.test(currentArrayPositionForPredicates)) {
                                continue;
                            }

                            final Object rightElement = getObjectFromArray(rightObject, index, rightCachedMutablePrimitivesSupplier);
                            final Object rightElementCopy = copyIfMutablePrimitive(rightElement, rightCachedMutablePrimitivesCopySupplier);
                            if (handleRightElement(currentDimension, rightElement, rightElementCopy)) {
                                return ComparisonResults.stopComparisonResult();
                            }
                        }
                    }
                }

                arrayCursor[currentDimension - 1] = -1;
                return ComparisonResults.emptyComparisonResult();
            } catch (ComparisonFailedException e) {
                throw e;
            } catch (Exception e) {
                throw new ComparisonFailedException("Cannot traverse array", e, leftObject, rightObject);
            }
        }

        private Object copyIfMutablePrimitive(Object potentialMutablePrimitive, LazySupplier<Map<Class<?>, MutablePrimitive<?>>> cachedMutablePrimitivesCopySupplier) {
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

        private boolean compareElements(Object currentLeftObject, Object currentRightObject, Object currentLeftObjectCopy, Object currentRightObjectCopy, ItemComparator<Object, ? extends ComparisonResult<?,?,?>> itemComparator) throws Exception {
            if (elementsFilter.test(currentLeftObjectCopy) && elementsFilter.test(currentRightObjectCopy)) {
                if (compare(currentLeftObject, currentRightObject, itemComparator)) {
                    return true;
                }
            }
            return false;
        }

        private boolean compare(Object currentLeftObject, Object currentRightObject, ItemComparator<Object, ? extends ComparisonResult<?,?,?>> itemComparator) throws ComparisonFailedException, InvalidSupplyException {
            final ComparisonResult<?,?,?> comparisonResult = exceptionHandlingComparator.compare(itemComparator, currentLeftObject, currentRightObject, createSafeResultSupplier(intermediateResult, maxDimensions), visitedObjectsTrace);
            final ImmutableIntList arrayPosition = createArrayPosition(arrayCursor, maxDimensions, currentDimension);
            if (handleComparisonResult(intermediateResult, comparisonResult, arrayPosition, currentLeftObject, currentRightObject)) {
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
                    } else {
                        intermediateResult.writeAdditionalElementsOnlyInLeftArray().put(arrayPosition, element);
                    }

                    return stopResultPredicate.test(intermediateResult);
                }
            }
            return false;
        }

        private Object getObjectFromArray(Object currentObject, int index, LazySupplier<Map<Class<?>, MutablePrimitive<?>>> cachedMutablePrimitivesSupplier) {
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
                    intermediateResult.writeSimilarElements().put(arrayPosition, currentLeftObject);

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

        private boolean handleComparisonResult(IntermediateArrayComparisonResult intermediateResult, ComparisonResult<?,?,?> comparisonResult, ImmutableIntList arrayPosition, Object leftObject, Object rightObject) throws InvalidSupplyException, ComparisonFailedException {
            if (comparisonResult instanceof ValueComparisonResult) {
                final ValueComparisonResult<Object> valueComparisonResult = (ValueComparisonResult<Object>) comparisonResult;
                if (valueComparisonResult.hasSimilarity()) {
                    if (comparisonFeatures.contains(SIMILAR_ELEMENTS)) {
                        intermediateResult.writeSimilarElements().put(arrayPosition, valueComparisonResult.getSimilarity());
                    }
                } else if (valueComparisonResult.hasDifference()) {
                    if (comparisonFeatures.contains(DIFFERENT_VALUES)) {
                        intermediateResult.writeDifferentValues().put(arrayPosition, valueComparisonResult.getDifference());
                    }
                }

                return stopResultPredicate.test(intermediateResult);
            } else if (comparisonResult == ComparisonResults.skipComparisonResult()) {
                return false;
            } else if (comparisonResult == ComparisonResults.stopComparisonResult()) {
                return true;
            } else {
                if (comparisonFeatures.contains(COMPARISON_RESULTS)) {
                    intermediateResult.writeComparisonResults().put(arrayPosition, comparisonResult);
                }

                return stopResultPredicate.test(intermediateResult);
            }
        }
    }
}
