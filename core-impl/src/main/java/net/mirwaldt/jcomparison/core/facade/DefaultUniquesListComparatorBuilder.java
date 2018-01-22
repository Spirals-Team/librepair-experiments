package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyModifiableFunction;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.uniques.impl.DefaultUniquesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.uniques.impl.ImmutableUniquesListComparisonResultFunction;
import net.mirwaldt.jcomparison.core.collection.list.uniques.impl.IntermediateUniquesListComparisonResult;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparator.ComparisonFeature.ELEMENTS_IN_BOTH_LISTS_AT_THE_SAME_INDEX;

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
public class DefaultUniquesListComparatorBuilder<ValueType> {
    private Supplier<Map> createMapSupplier;
    private Function<Map, Map> copyMapFunction;

    private Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsOnlyInLeftList;
    private Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsInBothListsAtTheSameIndex;
    private Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Pair<Integer>>> accessElementsMovedBetweenLists;
    private Function<IntermediateUniquesListComparisonResult<ValueType>, Map<ValueType, Integer>> accessElementsOnlyInRightList;
    private Function<IntermediateUniquesListComparisonResult<ValueType>, UniquesListComparisonResult<ValueType>> resultFunction;

    private Predicate<ValueType> elementsFilter;
    private EnumSet<UniquesListComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<IntermediateUniquesListComparisonResult<ValueType>> stopPredicate;

    public DefaultUniquesListComparatorBuilder<ValueType> copyIntermediateResultsForMinCapacity() {
        accessElementsOnlyInLeftList = IntermediateUniquesListComparisonResult::copyElementsOnlyInLeftList;
        accessElementsInBothListsAtTheSameIndex = IntermediateUniquesListComparisonResult::copyElementsInBothListsAtTheSameIndex;
        accessElementsMovedBetweenLists = IntermediateUniquesListComparisonResult::copyElementsMovedBetweenLists;
        accessElementsOnlyInRightList = IntermediateUniquesListComparisonResult::copyElementsOnlyInRightList;

        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> readIntermediateResultsForFinalResult() {
        accessElementsOnlyInLeftList = IntermediateUniquesListComparisonResult::readElementsOnlyInLeftList;
        accessElementsInBothListsAtTheSameIndex = IntermediateUniquesListComparisonResult::readElementsInBothListsAtTheSameIndex;
        accessElementsMovedBetweenLists = IntermediateUniquesListComparisonResult::readElementsMovedBetweenLists;
        accessElementsOnlyInRightList = IntermediateUniquesListComparisonResult::readElementsOnlyInRightList;

        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> comparisonFeatures(EnumSet<UniquesListComparator.ComparisonFeature> comparisonFeatures) {
        this.comparisonFeatures = comparisonFeatures;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> findDifferencesOnly() {
        this.comparisonFeatures = EnumSet.complementOf(EnumSet.of(ELEMENTS_IN_BOTH_LISTS_AT_THE_SAME_INDEX));
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> findSimilaritiesOnly() {
        this.comparisonFeatures = EnumSet.of(ELEMENTS_IN_BOTH_LISTS_AT_THE_SAME_INDEX);
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> findSimilaritiesAndDifferences() {
        this.comparisonFeatures = EnumSet.allOf(UniquesListComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readElementsOnlyInLeftList().isEmpty() ||
                        !intermediateComparisonResult.readElementsMovedBetweenLists().isEmpty() ||
                        !intermediateComparisonResult.readElementsInBothListsAtTheSameIndex().isEmpty() ||
                        !intermediateComparisonResult.readElementsOnlyInRightList().isEmpty();
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <=
                        intermediateComparisonResult.readElementsOnlyInLeftList().size() +
                                intermediateComparisonResult.readElementsMovedBetweenLists().size() +
                                intermediateComparisonResult.readElementsInBothListsAtTheSameIndex().size() +
                                intermediateComparisonResult.readElementsOnlyInRightList().size();

        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> stopPredicate(Predicate<IntermediateUniquesListComparisonResult<ValueType>> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> resultFunction(Function<IntermediateUniquesListComparisonResult<ValueType>, UniquesListComparisonResult<ValueType>> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> useCreateMapSupplier(Supplier<Map> createMapSupplier) {
        this.createMapSupplier = createMapSupplier;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> useCopyMapFunction(Function<Map, Map> copyMapFunction) {
        this.copyMapFunction = copyMapFunction;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> useDefaultCreateMapSupplier() {
        this.createMapSupplier = HashMap::new;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> useDefaultCopyMapFunction() {
        this.copyMapFunction = new CopyIfNonEmptyModifiableFunction<Map>(Map::isEmpty, HashMap::new);
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> allowAllFilter() {
        this.elementsFilter = (value) -> true;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> filterElements(Predicate<ValueType> elementsFilter) {
        this.elementsFilter = elementsFilter;
        return this;
    }

    public DefaultUniquesListComparatorBuilder<ValueType> ignoreNulls() {
        this.elementsFilter = Objects::nonNull;
        return this;
    }

    public DefaultUniquesListComparator<ValueType> build() {
        final Supplier<IntermediateUniquesListComparisonResult<ValueType>> intermediateResultField = () -> new IntermediateUniquesListComparisonResult<>(createMapSupplier, copyMapFunction);

        final Function<IntermediateUniquesListComparisonResult<ValueType>, UniquesListComparisonResult<ValueType>> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableUniquesListComparisonResultFunction<>(accessElementsOnlyInLeftList, accessElementsInBothListsAtTheSameIndex, accessElementsMovedBetweenLists, accessElementsOnlyInRightList);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultUniquesListComparator<>(
                intermediateResultField,
                usedResultFunction,
                elementsFilter,
                comparisonFeatures,
                stopPredicate
        );
    }
}
