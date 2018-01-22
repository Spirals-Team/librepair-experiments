package net.mirwaldt.jcomparison.core.collection.list.duplicates.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableInt;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.util.deduplicator.impl.NoDeduplicator;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
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
public class DefaultDuplicatesListComparator<ValueType> implements DuplicatesListComparator<ValueType> {
    private final Supplier<IntermediateDuplicatesListComparisonResult<ValueType>> intermediateResultField;
    private final Function<IntermediateDuplicatesListComparisonResult<ValueType>, DuplicatesListComparisonResult<ValueType>> resultFunction;

    private final ItemComparator<ValueType, ? extends ComparisonResult<?,?,?>> comparator;
    private final ComparisonFailedExceptionHandlingComparator exceptionHandlingComparator;

    private final EnumSet<ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateDuplicatesListComparisonResult<ValueType>> stopPredicate;

    public DefaultDuplicatesListComparator(Supplier<IntermediateDuplicatesListComparisonResult<ValueType>> intermediateResultField, Function<IntermediateDuplicatesListComparisonResult<ValueType>, DuplicatesListComparisonResult<ValueType>> resultFunction, ItemComparator<ValueType, ? extends ComparisonResult<?,?,?>> comparator, ComparisonFailedExceptionHandler comparisonFailedExceptionHandler, EnumSet<ComparisonFeature> comparisonFeatures, Predicate<IntermediateDuplicatesListComparisonResult<ValueType>> stopPredicate) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparator = comparator;
        this.exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator(comparisonFailedExceptionHandler);
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;
    }

    @Override
    public DuplicatesListComparisonResult<ValueType> compare(List<ValueType> leftList, List<ValueType> rightList, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
        try {
            final IntermediateDuplicatesListComparisonResult<ValueType> intermediateResult = intermediateResultField.get();

            if (!leftList.isEmpty() || !rightList.isEmpty()) {
                compare(leftList, rightList, intermediateResult, visitedObjectsTrace);
            }

            return resultFunction.apply(intermediateResult);
        } catch (InvalidSupplyException e) {
            throw new ComparisonFailedException("Invalid supply of a new list.", e, leftList, rightList);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both lists.", e, leftList, rightList);
        }
    }

    private void compare(List<ValueType> leftList, List<ValueType> rightList, IntermediateDuplicatesListComparisonResult<ValueType> intermediateResult, VisitedObjectsTrace visitedObjectsTrace) throws InvalidSupplyException, ComparisonFailedException {
        final Iterator<ValueType> leftListIterator = leftList.iterator();
        final Iterator<ValueType> rightListIterator = rightList.iterator();

        boolean hasLeftListAnyNextElement = leftListIterator.hasNext();
        boolean hasRightListAnyNextElement = rightListIterator.hasNext();

        for (int index = 0; hasLeftListAnyNextElement || hasRightListAnyNextElement; index++) {
            final ValueType nextElementInLeftList = (hasLeftListAnyNextElement) ? leftListIterator.next() : null;
            final ValueType nextElementInRightList = (hasRightListAnyNextElement) ? rightListIterator.next() : null;

            if (shouldEvaluateFrequencies()) {
                if (hasLeftListAnyNextElement) {
                    countValue(intermediateResult.writeLeftElementFrequencies(), nextElementInLeftList);
                }
                if (hasRightListAnyNextElement) {
                    countValue(intermediateResult.writeRightElementFrequencies(), nextElementInRightList);
                }
            }

            if (hasLeftListAnyNextElement && hasRightListAnyNextElement) {
                final ComparisonResult<?,?,?> comparisonResult = exceptionHandlingComparator.compare(comparator, nextElementInLeftList, nextElementInRightList, () -> resultFunction.apply(intermediateResult), visitedObjectsTrace);

                if (comparisonResult == ComparisonResults.skipComparisonResult()) {
                    // skip
                } else if (comparisonResult == ComparisonResults.stopComparisonResult()) {
                    return;
                } else if (comparisonResult instanceof ValueComparisonResult) {
                    ValueComparisonResult<ValueType> valueComparisonResult = (ValueComparisonResult<ValueType>) comparisonResult;

                    if (shouldEvaluateOccurences()) {
                        if (comparisonResult.hasSimilarity() && comparisonFeatures.contains(ComparisonFeature.SIMILAR_ELEMENTS)) {
                            intermediateResult.writeSimilarValues().put(index, valueComparisonResult.getSimilarity());

                            if (stopPredicate.test(intermediateResult)) {
                                return;
                            }
                        } else if (comparisonResult.hasDifference() && comparisonFeatures.contains(ComparisonFeature.DIFFERENT_ELEMENTS)) {
                            intermediateResult.writeDifferentValues().put(index, valueComparisonResult.getDifference());

                            if (stopPredicate.test(intermediateResult)) {
                                return;
                            }
                        }
                    }
                } else {
                    if(comparisonFeatures.contains(ComparisonFeature.COMPARED_ELEMENTS)) {
                        intermediateResult.writeComparedElements().put(index, comparisonResult);

                        if (stopPredicate.test(intermediateResult)) {
                            return;
                        }
                    }
                }
            } else if (hasLeftListAnyNextElement && comparisonFeatures.contains(ComparisonFeature.ADDITIONAL_ELEMENTS_IN_LEFT_LIST)) {
                intermediateResult.writeAdditionalElementsOnlyInLeftList().put(index, nextElementInLeftList);

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            } else if (hasRightListAnyNextElement && comparisonFeatures.contains(ComparisonFeature.ADDITIONAL_ELEMENTS_IN_RIGHT_LIST)) {
                intermediateResult.writeAdditionalElementsOnlyInRightList().put(index, nextElementInRightList);

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }

            hasLeftListAnyNextElement = leftListIterator.hasNext();
            hasRightListAnyNextElement = rightListIterator.hasNext();
        }

        if (shouldEvaluateFrequencies()) {
            evaluateFrequencies(intermediateResult);
        }
    }

    private boolean shouldEvaluateOccurences() {
        return comparisonFeatures.contains(ComparisonFeature.ADDITIONAL_ELEMENTS_IN_LEFT_LIST) || comparisonFeatures.contains(ComparisonFeature.SIMILAR_ELEMENTS) || comparisonFeatures.contains(ComparisonFeature.DIFFERENT_ELEMENTS) || comparisonFeatures.contains(ComparisonFeature.ADDITIONAL_ELEMENTS_IN_RIGHT_LIST);
    }

    private boolean shouldEvaluateFrequencies() {
        return comparisonFeatures.contains(ComparisonFeature.SIMILAR_FREQUENCIES) || comparisonFeatures.contains(ComparisonFeature.DIFFERENT_FREQUENCIES);
    }

    private void countValue(final Map<ValueType, MutableInt> valueFrequencies, ValueType value) {
        MutableInt frequencyOfValue = valueFrequencies.get(value);
        if (frequencyOfValue == null) {
            frequencyOfValue = new MutableInt(new NoDeduplicator());
            frequencyOfValue.setValue(0);
            valueFrequencies.put(value, frequencyOfValue);
        }
        frequencyOfValue.setValue(frequencyOfValue.getValue() + 1);
    }

    public void evaluateFrequencies(IntermediateDuplicatesListComparisonResult<ValueType> intermediateResult) throws InvalidSupplyException {
        for (Map.Entry<ValueType, MutableInt> leftValueFrequencyEntry : intermediateResult.readLeftElementFrequencies().entrySet()) {
            final ValueType leftValue = leftValueFrequencyEntry.getKey();
            final MutableInt frequencyOfLeftValue = leftValueFrequencyEntry.getValue();
            final MutableInt frequencyOfRightValueAsInteger = intermediateResult.writeRightElementFrequencies().remove(leftValue);
            if (frequencyOfRightValueAsInteger == null) {
                if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_FREQUENCIES)) {
                    intermediateResult.writeDifferentFrequencies().put(leftValue, new ImmutableIntPair(frequencyOfLeftValue.getValue(), 0));

                    if (stopPredicate.test(intermediateResult)) {
                        return;
                    }
                }
            } else {
                if (frequencyOfRightValueAsInteger.equals(frequencyOfLeftValue) && comparisonFeatures.contains(ComparisonFeature.SIMILAR_FREQUENCIES)) {
                    intermediateResult.writeSimilarFrequencies().put(leftValue, frequencyOfLeftValue.getValue());

                    if (stopPredicate.test(intermediateResult)) {
                        return;
                    }
                } else if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_FREQUENCIES)) {
                    intermediateResult.writeDifferentFrequencies().put(leftValue,
                            new ImmutableIntPair(frequencyOfLeftValue.getValue(), frequencyOfRightValueAsInteger.getValue()));

                    if (stopPredicate.test(intermediateResult)) {
                        return;
                    }
                }
            }
        }

        if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_FREQUENCIES)) {
            for (Map.Entry<ValueType, MutableInt> rightValueFrequencyEntry : intermediateResult.readRightElementFrequencies().entrySet()) {
                final ValueType rightValue = rightValueFrequencyEntry.getKey();
                final MutableInt frequencyOfRightValue = rightValueFrequencyEntry.getValue();
                intermediateResult.writeDifferentFrequencies().put(rightValue, new ImmutableIntPair(0, frequencyOfRightValue.getValue()));

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }
        }
    }
}
