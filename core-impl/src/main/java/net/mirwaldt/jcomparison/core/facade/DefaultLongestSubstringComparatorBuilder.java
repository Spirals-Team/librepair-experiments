package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.impl.DefaultLongestSubstringComparator;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyModifiableFunction;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparator;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;
import net.mirwaldt.jcomparison.core.string.impl.ImmutableSubstringComparisonResultFunction;
import net.mirwaldt.jcomparison.core.string.impl.IntermediateSubstringComparisonResult;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
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
public class DefaultLongestSubstringComparatorBuilder {
    private Supplier<List> createListSupplier;
    private Function<List, List> copyListFunction;

    private Function<IntermediateSubstringComparisonResult, List<SubstringDifference>> accessSubstringDifference;
    private Function<IntermediateSubstringComparisonResult, List<SubstringSimilarity>> accessSubstringSimilarity;
    private Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> resultFunction;

    private EnumSet<SubstringComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<IntermediateSubstringComparisonResult> stopPredicate;
    private IntPredicate isWordDelimiter;
    
    public DefaultLongestSubstringComparatorBuilder copyIntermediateResults() {
        accessSubstringDifference = IntermediateSubstringComparisonResult::copyDifferentSubstrings;
        accessSubstringSimilarity = IntermediateSubstringComparisonResult::copySimilarSubstrings;

        return this;
    }

    public DefaultLongestSubstringComparatorBuilder readIntermediateResults() {
        accessSubstringDifference = IntermediateSubstringComparisonResult::readSubstringDifferences;
        accessSubstringSimilarity = IntermediateSubstringComparisonResult::readSubstringSimilarities;

        return this;
    }
    
    public DefaultLongestSubstringComparatorBuilder comparisonFeatures(EnumSet<SubstringComparator.ComparisonFeature> comparisonFeatures) {
        this.comparisonFeatures = comparisonFeatures;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder findDifferencesOnly() {
        this.comparisonFeatures = EnumSet.of(SubstringComparator.ComparisonFeature.SUBSTRING_DIFFERENCE);
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder findSimilaritiesOnly() {
        this.comparisonFeatures = EnumSet.of(SubstringComparator.ComparisonFeature.SUBSTRING_SIMILARITY);
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder findSimilaritiesAndDifferences() {
        this.comparisonFeatures = EnumSet.allOf(SubstringComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readSubstringDifferences().isEmpty() ||
                        !intermediateComparisonResult.readSubstringSimilarities().isEmpty();
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <= intermediateComparisonResult.readSubstringDifferences().size() +
                        intermediateComparisonResult.readSubstringSimilarities().size();
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder stopPredicate(Predicate<IntermediateSubstringComparisonResult> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder resultFunction(Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder useCreateListSupplier(Supplier<List> createListSupplier) {
        this.createListSupplier = createListSupplier;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder useCopyListFunction(Function<List, List> copyListFunction) {
        this.copyListFunction = copyListFunction;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder useDefaultCreateListSupplier() {
        this.createListSupplier = ArrayList::new;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder useDefaultCopyListFunction() {
        this.copyListFunction = new CopyIfNonEmptyModifiableFunction<List>(List::isEmpty, ArrayList::new);
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder useDefaultWordDelimiter() {
        this.isWordDelimiter = DefaultLongestSubstringComparator.NO_WORD_DELIMITER;
        return this;
    }

    public DefaultLongestSubstringComparatorBuilder useCustomWordDelimiter(IntPredicate isWordDelimiter) {
        this.isWordDelimiter = isWordDelimiter;
        return this;
    }

    public DefaultLongestSubstringComparator build() {
        final Supplier<IntermediateSubstringComparisonResult> intermediateResultField = () -> new IntermediateSubstringComparisonResult(createListSupplier, copyListFunction);

        final Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableSubstringComparisonResultFunction(accessSubstringDifference, accessSubstringSimilarity);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultLongestSubstringComparator(
                intermediateResultField,
                usedResultFunction,
                comparisonFeatures,
                stopPredicate,
                isWordDelimiter
        );
    }
}
