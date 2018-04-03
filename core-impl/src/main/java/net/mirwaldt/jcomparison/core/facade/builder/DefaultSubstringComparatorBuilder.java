package net.mirwaldt.jcomparison.core.facade.builder;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.impl.DefaultSubstringComparator;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyFunction;
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
public class DefaultSubstringComparatorBuilder {
    private Supplier<List> createListSupplier;
    private Function<List, List> copyListFunction;

    private Function<IntermediateSubstringComparisonResult, List<SubstringDifference>> accessSubstringDifference;
    private Function<IntermediateSubstringComparisonResult, List<SubstringSimilarity>> accessSubstringSimilarity;
    private Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> resultFunction;

    private EnumSet<SubstringComparator.ComparisonFeature> comparisonFeatures;
    private Predicate<IntermediateSubstringComparisonResult> stopPredicate;
    private IntPredicate isWordDelimiter;
    
    public DefaultSubstringComparatorBuilder copyIntermediateResults() {
        accessSubstringDifference = IntermediateSubstringComparisonResult::copyDifferentSubstrings;
        accessSubstringSimilarity = IntermediateSubstringComparisonResult::copySimilarSubstrings;

        return this;
    }

    public DefaultSubstringComparatorBuilder readIntermediateResults() {
        accessSubstringDifference = IntermediateSubstringComparisonResult::readSubstringDifferences;
        accessSubstringSimilarity = IntermediateSubstringComparisonResult::readSubstringSimilarities;

        return this;
    }
    
    public DefaultSubstringComparatorBuilder comparisonFeatures(EnumSet<SubstringComparator.ComparisonFeature> comparisonFeatures) {
        this.comparisonFeatures = comparisonFeatures;
        return this;
    }

    public DefaultSubstringComparatorBuilder findDifferencesOnly() {
        this.comparisonFeatures = EnumSet.of(SubstringComparator.ComparisonFeature.SUBSTRING_DIFFERENCE);
        return this;
    }

    public DefaultSubstringComparatorBuilder findSimilaritiesOnly() {
        this.comparisonFeatures = EnumSet.of(SubstringComparator.ComparisonFeature.SUBSTRING_SIMILARITY);
        return this;
    }

    public DefaultSubstringComparatorBuilder findSimilaritiesAndDifferences() {
        this.comparisonFeatures = EnumSet.allOf(SubstringComparator.ComparisonFeature.class);
        return this;
    }

    public DefaultSubstringComparatorBuilder findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultSubstringComparatorBuilder findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readSubstringDifferences().isEmpty() ||
                        !intermediateComparisonResult.readSubstringSimilarities().isEmpty();
        return this;
    }

    public DefaultSubstringComparatorBuilder findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <= intermediateComparisonResult.readSubstringDifferences().size() +
                        intermediateComparisonResult.readSubstringSimilarities().size();
        return this;
    }

    public DefaultSubstringComparatorBuilder stopPredicate(Predicate<IntermediateSubstringComparisonResult> stopPredicate) {
        this.stopPredicate = stopPredicate;
        return this;
    }

    public DefaultSubstringComparatorBuilder resultFunction(Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public DefaultSubstringComparatorBuilder useCreateListSupplier(Supplier<List> createListSupplier) {
        this.createListSupplier = createListSupplier;
        return this;
    }

    public DefaultSubstringComparatorBuilder useCopyListFunction(Function<List, List> copyListFunction) {
        this.copyListFunction = copyListFunction;
        return this;
    }

    public DefaultSubstringComparatorBuilder useDefaultCreateListSupplier() {
        this.createListSupplier = ArrayList::new;
        return this;
    }

    public DefaultSubstringComparatorBuilder useDefaultCopyListFunction() {
        this.copyListFunction = new CopyIfNonEmptyFunction<List>(List::isEmpty, ArrayList::new);
        return this;
    }

    public DefaultSubstringComparatorBuilder useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultSubstringComparatorBuilder useDefaultWordDelimiter() {
        this.isWordDelimiter = DefaultSubstringComparator.NO_WORD_DELIMITER;
        return this;
    }

    public DefaultSubstringComparatorBuilder useCustomWordDelimiter(IntPredicate isWordDelimiter) {
        this.isWordDelimiter = isWordDelimiter;
        return this;
    }

    public DefaultSubstringComparator build() {
        final Supplier<IntermediateSubstringComparisonResult> intermediateResultField = () -> new IntermediateSubstringComparisonResult(createListSupplier, copyListFunction);

        final Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableSubstringComparisonResultFunction(accessSubstringDifference, accessSubstringSimilarity);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultSubstringComparator(
                intermediateResultField,
                usedResultFunction,
                comparisonFeatures,
                stopPredicate,
                isWordDelimiter
        );
    }
}
