package net.mirwaldt.jcomparison.core.collection.list.impl;

import net.mirwaldt.jcomparison.core.annotation.NotNullSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.basic.impl.ComparisonFailedExceptionHandlingComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntListFactory;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
@NotNullSafe
public class DefaultListComparator<ValueType> implements ListComparator<ValueType> {
    private final Supplier<IntermediateListComparisonResult<ValueType>> intermediateResultField;
    private final Function<IntermediateListComparisonResult<ValueType>, ListComparisonResult<ValueType>> resultFunction;

    private final ItemComparator<ValueType, ? extends ComparisonResult<?, ?, ?>> comparator;
    private final ComparisonFailedExceptionHandler exceptionHandler;

    private final Predicate<ValueType> elementFilter;
    private final EnumSet<ComparisonFeature> comparisonFeatures;
    private final Predicate<IntermediateListComparisonResult<ValueType>> stopPredicate;


    /**
     * optimizations
     */
    private final ComparatorOptions comparatorOptions;
    private final boolean needsComparison;

    public DefaultListComparator(
            Supplier<IntermediateListComparisonResult<ValueType>> intermediateResultField,
            Function<IntermediateListComparisonResult<ValueType>, ListComparisonResult<ValueType>> resultFunction,
            ItemComparator<ValueType, ? extends ComparisonResult<?, ?, ?>> comparator,
            ComparisonFailedExceptionHandler exceptionHandler,
            Predicate<ValueType> elementFilter,
            EnumSet<ComparisonFeature> comparisonFeatures,
            Predicate<IntermediateListComparisonResult<ValueType>> stopPredicate) {
        this.intermediateResultField = intermediateResultField;
        this.resultFunction = resultFunction;
        this.comparator = comparator;
        this.exceptionHandler = exceptionHandler;
        this.elementFilter = elementFilter;
        this.comparisonFeatures = comparisonFeatures;
        this.stopPredicate = stopPredicate;

        this.needsComparison = comparisonFeatures.stream()
                .anyMatch(comparisonFeature -> comparisonFeature.needsValueComparison() || comparisonFeature.needsNonValueComparison());

        this.comparatorOptions = new ComparatorOptions(true,
                comparisonFeatures.stream().anyMatch(ComparisonFeature::needsValueComparison),
                comparisonFeatures.stream().anyMatch(ComparisonFeature::needsNonValueComparison)
        );
    }

    @Override
    public ListComparisonResult<ValueType> compare(List<ValueType> leftList,
                                                   List<ValueType> rightList,
                                                   VisitedObjectsTrace visitedObjectsTrace,
                                                   ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        try {
            final IntermediateListComparisonResult<ValueType> intermediateResult = intermediateResultField.get();

            if (!leftList.isEmpty() || !rightList.isEmpty()) {
                final ComparisonFailedExceptionHandlingComparator<ValueType> exceptionHandlingComparator = new ComparisonFailedExceptionHandlingComparator<>(exceptionHandler);
                exceptionHandlingComparator.setComparator(comparator);
                exceptionHandlingComparator.setComparisonResultSafeSupplier(() -> resultFunction.apply(intermediateResult));
                compare(leftList, rightList, visitedObjectsTrace, comparatorOptions, intermediateResult, exceptionHandlingComparator);
            }

            return resultFunction.apply(intermediateResult);
        } catch (Exception e) {
            throw new ComparisonFailedException("Cannot compare both lists.", e, leftList, rightList);
        }
    }

    private void compare(List<ValueType> leftList,
                         List<ValueType> rightList,
                         VisitedObjectsTrace visitedObjectsTrace,
                         ComparatorOptions comparatorOptions,
                         IntermediateListComparisonResult<ValueType> intermediateResult,
                         ComparisonFailedExceptionHandlingComparator exceptionHandlingComparator) throws ComparisonFailedException {
        final Iterator<ValueType> leftListIterator = leftList.iterator();
        final Iterator<ValueType> rightListIterator = rightList.iterator();

        boolean hasLeftListAnyNextElement = leftListIterator.hasNext();
        boolean hasRightListAnyNextElement = rightListIterator.hasNext();

        for (int index = 0; hasLeftListAnyNextElement || hasRightListAnyNextElement; index++) {
            final ValueType nextElementInLeftList = (hasLeftListAnyNextElement) ? leftListIterator.next() : null;
            final ValueType nextElementInRightList = (hasRightListAnyNextElement) ? rightListIterator.next() : null;

            if (hasLeftListAnyNextElement && elementFilter.test(nextElementInLeftList)) {
                addPosition(intermediateResult.writeLeftElementPositions(), nextElementInLeftList, index);
            }
            if (hasRightListAnyNextElement && elementFilter.test(nextElementInRightList)) {
                addPosition(intermediateResult.writeRightElementPositions(), nextElementInRightList, index);
            }

            if (hasLeftListAnyNextElement && hasRightListAnyNextElement) {
                if (needsComparison && elementFilter.test(nextElementInLeftList) && elementFilter.test(nextElementInRightList)) {
                    final ComparisonResult<?, ?, ?> comparisonResult = exceptionHandlingComparator.compare(nextElementInLeftList, nextElementInRightList, visitedObjectsTrace, this.comparatorOptions);

                    if (handleComparisonResult(intermediateResult, index, comparisonResult)) {
                        return;
                    }
                }
            }

            hasLeftListAnyNextElement = leftListIterator.hasNext();
            hasRightListAnyNextElement = rightListIterator.hasNext();
        }

        evaluatePositions(intermediateResult);
    }

    private boolean handleComparisonResult(IntermediateListComparisonResult<ValueType> intermediateResult, 
                                           int index, ComparisonResult<?, ?, ?> comparisonResult) {
        if (comparisonResult == BasicComparisonResults.skipComparisonResult()) {
            return false;
        } else if (comparisonResult == BasicComparisonResults.stopComparisonResult()) {
            return true;
        } else if (comparisonResult instanceof ValueComparisonResult) {
            ValueComparisonResult<ValueType> valueComparisonResult = (ValueComparisonResult<ValueType>) comparisonResult;

            if (comparisonResult.hasSimilarities() && comparisonFeatures.contains(ComparisonFeature.SIMILAR_ELEMENTS)) {
                intermediateResult.writeSimilarValues().put(index, valueComparisonResult.getSimilarities());

                return stopPredicate.test(intermediateResult);
            } else if (comparisonResult.hasDifferences() && comparisonFeatures.contains(ComparisonFeature.DIFFERENT_ELEMENTS)) {
                intermediateResult.writeDifferentValues().put(index, valueComparisonResult.getDifferences());

                return stopPredicate.test(intermediateResult);
            }
        } else {
            if (comparisonFeatures.contains(ComparisonFeature.COMPARE_ELEMENTS_DEEP)) {
                intermediateResult.writeComparedElements().put(index, comparisonResult);

                return stopPredicate.test(intermediateResult);
            }
        }
        return false;
    }


    private void addPosition(Map<ValueType, List<Integer>> positionsOfElements, ValueType nextElementInList, int index) {
        final List<Integer> positionsOfElement = positionsOfElements.computeIfAbsent(nextElementInList, k -> new ArrayList<>());
        positionsOfElement.add(index);
    }

    private void evaluatePositions(IntermediateListComparisonResult<ValueType> intermediateResult) {
        //TODO: use smaller entry set (can be left one but also can be right one!)
        for (Map.Entry<ValueType, List<Integer>> leftElementPositionsEntry : intermediateResult.readLeftElementPositions().entrySet()) {
            final ValueType leftElement = leftElementPositionsEntry.getKey();
            final List<Integer> positionsOfLeftElement = leftElementPositionsEntry.getValue();
            final List<Integer> positionsOfRightElement = intermediateResult.writeRightElementPositions().remove(leftElement);

            if (positionsOfRightElement == null) {
                if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_POSITIONS)) {
                    intermediateResult.writeDifferentPositions().put(leftElement,
                            new ImmutablePair<>(
                                    ImmutableIntListFactory.create(positionsOfLeftElement),
                                    ImmutableIntListFactory.create(Collections.emptyList())));

                    if (stopPredicate.test(intermediateResult)) {
                        return;
                    }
                }

                if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_FREQUENCIES)) {
                    intermediateResult.writeDifferentFrequencies().put(leftElement,
                            new ImmutableIntPair(positionsOfLeftElement.size(), 0));

                    if (stopPredicate.test(intermediateResult)) {
                        return;
                    }
                }

                if (comparisonFeatures.contains(ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST)) {
                    intermediateResult.writeElementsOnlyInLeftList().put(leftElement,
                            ImmutableIntListFactory.create(positionsOfLeftElement));

                    if (stopPredicate.test(intermediateResult)) {
                        return;
                    }
                }
            } else {
                final List<Integer> similarPositions = new ArrayList<>(positionsOfLeftElement);
                similarPositions.retainAll(positionsOfRightElement);
                                
                if (!similarPositions.isEmpty()) {
                    if (comparisonFeatures.contains(ComparisonFeature.SIMILAR_POSITIONS)) {
                        intermediateResult.writeSimilarPositions().put(
                                leftElement, ImmutableIntListFactory.create(similarPositions));

                        if (stopPredicate.test(intermediateResult)) {
                            return;
                        }
                    }
                }

                final List<Integer> differentPositionsInLeftList = new ArrayList<>(positionsOfLeftElement);
                differentPositionsInLeftList.removeAll(similarPositions);
                
                final List<Integer> differentPositionsInRightList = new ArrayList<>(positionsOfRightElement);
                differentPositionsInRightList.removeAll(similarPositions);
                
                if(!differentPositionsInLeftList.isEmpty() || !differentPositionsInRightList.isEmpty()){
                    if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_POSITIONS)) {
                        intermediateResult.writeDifferentPositions().put(leftElement,
                                new ImmutablePair<>(ImmutableIntListFactory.create(differentPositionsInLeftList),
                                        ImmutableIntListFactory.create(differentPositionsInRightList)));

                        if (stopPredicate.test(intermediateResult)) {
                            return;
                        }
                    }
                }

                if (positionsOfRightElement.size() == positionsOfLeftElement.size()) {
                    if (comparisonFeatures.contains(ComparisonFeature.SIMILAR_FREQUENCIES)) {
                        intermediateResult.writeSimilarFrequencies().put(leftElement, positionsOfLeftElement.size());

                        if (stopPredicate.test(intermediateResult)) {
                            return;
                        }
                    }
                } else {
                    if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_FREQUENCIES)) {
                        intermediateResult.writeDifferentFrequencies().put(leftElement,
                                new ImmutableIntPair(positionsOfLeftElement.size(), positionsOfRightElement.size()));

                        if (stopPredicate.test(intermediateResult)) {
                            return;
                        }
                    }
                }
            }
        }


        for (Map.Entry<ValueType, List<Integer>> rightValueFrequencyEntry : intermediateResult.readRightElementPositions().entrySet()) {
            final ValueType rightElement = rightValueFrequencyEntry.getKey();
            final List<Integer> positionsOfRightElement = rightValueFrequencyEntry.getValue();

            if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_POSITIONS)) {
                intermediateResult.writeDifferentPositions().put(rightElement,
                        new ImmutablePair<>(ImmutableIntListFactory.create(Collections.emptyList()),
                                ImmutableIntListFactory.create(positionsOfRightElement)));

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }

            if (comparisonFeatures.contains(ComparisonFeature.DIFFERENT_FREQUENCIES)) {
                intermediateResult.writeDifferentFrequencies().put(rightElement,
                        new ImmutableIntPair(0, positionsOfRightElement.size()));

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }

            if (comparisonFeatures.contains(ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST)) {
                intermediateResult.writeElementsOnlyInRightList().put(rightElement,
                        ImmutableIntListFactory.create(positionsOfRightElement));

                if (stopPredicate.test(intermediateResult)) {
                    return;
                }
            }
        }

    }
}
