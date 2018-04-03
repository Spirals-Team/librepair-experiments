package net.mirwaldt.jcomparison.core.facade.builder;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyFunction;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparator;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.collection.set.impl.DefaultSetComparator;
import net.mirwaldt.jcomparison.core.collection.set.impl.ImmutableSetComparisonResultFunction;
import net.mirwaldt.jcomparison.core.collection.set.impl.IntermediateSetComparisonResult;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
@NotThreadSafe
public class DefaultSetComparatorBuilder<ValueType> {
    private Supplier<Set> createSetSupplier;
    private Function<Set, Set> copySetFunction;

    private Function<IntermediateSetComparisonResult<ValueType>, Set<ValueType>> accessAddedElements;
    private Function<IntermediateSetComparisonResult<ValueType>, Set<ValueType>> accessRetainedElements;
    private Function<IntermediateSetComparisonResult<ValueType>, Set<ValueType>> accessRemovedElements;
    private Function<IntermediateSetComparisonResult<ValueType>, SetComparisonResult<ValueType>> resultFunction;

    private EnumSet<SetComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<IntermediateSetComparisonResult<ValueType>> stopPredicate;
    private Predicate<ValueType> elementsFilter;

    public DefaultSetComparatorBuilder<ValueType> copyIntermediateResultsForMinCapacity() {
        accessAddedElements = IntermediateSetComparisonResult::copyElementsOnlyInLeftSet;
        accessRetainedElements = IntermediateSetComparisonResult::copyElementsInBothSets;
        accessRemovedElements = IntermediateSetComparisonResult::copyElementsOnlyInRightSet;

        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> readIntermediateResultsForFinalResult() {
        accessAddedElements = IntermediateSetComparisonResult::readElementsOnlyInLeftSet;
        accessRetainedElements = IntermediateSetComparisonResult::readElementsInBothSets;
        accessRemovedElements = IntermediateSetComparisonResult::readElementsOnlyInRightSet;

        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> useComparisonFeatures(EnumSet<SetComparator.ComparisonFeature> comparisonFeatures) {
        this.comparisonFeatures = comparisonFeatures;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> findDifferencesOnly() {
        this.comparisonFeatures = EnumSet.of(SetComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_SET, SetComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_SET);
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> findSimilaritiesOnly() {
        this.comparisonFeatures = EnumSet.of(SetComparator.ComparisonFeature.ELEMENTS_IN_BOTH_SETS);
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> findSimilaritiesAndDifferences() {
        this.comparisonFeatures = EnumSet.allOf(SetComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readElementsOnlyInLeftSet().isEmpty() ||
                !intermediateComparisonResult.readElementsInBothSets().isEmpty() ||
                !intermediateComparisonResult.readElementsOnlyInRightSet().isEmpty();
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <=
                        intermediateComparisonResult.readElementsOnlyInLeftSet().size() +
                        intermediateComparisonResult.readElementsInBothSets().size() +
                        intermediateComparisonResult.readElementsOnlyInRightSet().size();
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> stopPredicate(Predicate<IntermediateSetComparisonResult<ValueType>> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> resultFunction(Function<IntermediateSetComparisonResult<ValueType>, SetComparisonResult<ValueType>> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> useCreateSetSupplier(Supplier<Set> createSetSupplier) {
        this.createSetSupplier = createSetSupplier;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> useCopySetFunction(Function<Set, Set> copySetFunction) {
        this.copySetFunction = copySetFunction;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> useDefaultCreateSetSupplier() {
        this.createSetSupplier = HashSet::new;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> useDefaultCopySetFunction() {
        this.copySetFunction = new CopyIfNonEmptyFunction<Set>(Set::isEmpty, HashSet::new);
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> allowAllFilter() {
        this.elementsFilter = (value) -> true;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> filterElements(Predicate<ValueType> elementsFilter) {
        this.elementsFilter = elementsFilter;
        return this;
    }

    public DefaultSetComparatorBuilder<ValueType> ignoreNulls() {
        this.elementsFilter = Objects::nonNull;
        return this;
    }

    public DefaultSetComparator<ValueType> build() {
        final Supplier<IntermediateSetComparisonResult<ValueType>> intermediateResultField = () -> new IntermediateSetComparisonResult<>(createSetSupplier, copySetFunction);

        final Function<IntermediateSetComparisonResult<ValueType>, SetComparisonResult<ValueType>> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableSetComparisonResultFunction<>(accessAddedElements, accessRetainedElements, accessRemovedElements);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultSetComparator<>(
                intermediateResultField,
                usedResultFunction,
                comparisonFeatures,
                stopPredicate,
                elementsFilter
        );
    }
}
