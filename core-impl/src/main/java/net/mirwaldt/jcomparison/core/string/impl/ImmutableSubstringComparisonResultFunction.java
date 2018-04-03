package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.facade.EmptyComparisonResults;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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
public class ImmutableSubstringComparisonResultFunction implements Function<IntermediateSubstringComparisonResult, SubstringComparisonResult> {

    private final Function<IntermediateSubstringComparisonResult, List<SubstringDifference>> accessSubstringDifference;
    private final Function<IntermediateSubstringComparisonResult, List<SubstringSimilarity>> accessSubstringSimilarity;

    public ImmutableSubstringComparisonResultFunction(Function<IntermediateSubstringComparisonResult, List<SubstringDifference>> accessSubstringDifference, Function<IntermediateSubstringComparisonResult, List<SubstringSimilarity>> accessSubstringSimilarity) {
        this.accessSubstringDifference = accessSubstringDifference;
        this.accessSubstringSimilarity = accessSubstringSimilarity;
    }

    @Override
    public SubstringComparisonResult apply(IntermediateSubstringComparisonResult intermediateSubstringComparisonResult) {
        final boolean hasSimilarity = !intermediateSubstringComparisonResult.readSubstringSimilarities().isEmpty();
        final boolean hasDifference = !intermediateSubstringComparisonResult.readSubstringDifferences().isEmpty();

        if(hasSimilarity || hasDifference) {
            final List<SubstringDifference> accessedSubstringDifferences = accessSubstringDifference.apply(intermediateSubstringComparisonResult);
            final List<SubstringSimilarity> accessedSubstringSimilarities = accessSubstringSimilarity.apply(intermediateSubstringComparisonResult);
            
            final List<SubstringSimilarity> substringSimilarities = Collections.unmodifiableList(accessedSubstringSimilarities);
            final List<SubstringDifference> substringDifferences = Collections.unmodifiableList(accessedSubstringDifferences);

            return new ImmutableSubstringComparisonResult(hasSimilarity, substringSimilarities, hasDifference,
                    substringDifferences);
        } else {
            return EmptyComparisonResults.emptySubstringComparisonResult();
        }
    }
}
