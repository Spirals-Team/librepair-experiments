package net.mirwaldt.jcomparison.core.collection.set.impl;

import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.collection.set.api.SetDifference;
import net.mirwaldt.jcomparison.core.facade.EmptyComparisonResults;

import java.util.Collections;
import java.util.Set;
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
public class ImmutableSetComparisonResultFunction<Type> implements Function<IntermediateSetComparisonResult<Type>, SetComparisonResult<Type>> {

    private final Function<IntermediateSetComparisonResult<Type>, Set<Type>> accessElementsOnlyInLeftSet;
    private final Function<IntermediateSetComparisonResult<Type>, Set<Type>> accessElementsInBothSets;
    private final Function<IntermediateSetComparisonResult<Type>, Set<Type>> accessElementsOnlyInRightSet;

    public ImmutableSetComparisonResultFunction(Function<IntermediateSetComparisonResult<Type>, Set<Type>> accessElementsOnlyInLeftSet, Function<IntermediateSetComparisonResult<Type>, Set<Type>> accessElementsInBothSets, Function<IntermediateSetComparisonResult<Type>, Set<Type>> accessElementsOnlyInRightSet) {
        this.accessElementsOnlyInLeftSet = accessElementsOnlyInLeftSet;
        this.accessElementsInBothSets = accessElementsInBothSets;
        this.accessElementsOnlyInRightSet = accessElementsOnlyInRightSet;
    }

    @Override
    public SetComparisonResult<Type> apply(IntermediateSetComparisonResult<Type> typeIntermediateSetComparisonResult) {
        final boolean hasSetSimilarity = !typeIntermediateSetComparisonResult.copyElementsInBothSets().isEmpty();
        final boolean hasSetDifference = !(typeIntermediateSetComparisonResult.readElementsOnlyInLeftSet().isEmpty() && typeIntermediateSetComparisonResult.readElementsOnlyInRightSet().isEmpty());

        if (hasSetSimilarity || hasSetDifference) {
            final Set<Type> elementsOnlyInLeftSet = accessElementsOnlyInLeftSet.apply(typeIntermediateSetComparisonResult);
            final Set<Type> elementsInBothSets = accessElementsInBothSets.apply(typeIntermediateSetComparisonResult);
            final Set<Type> elementsOnlyInRightSet = accessElementsOnlyInRightSet.apply(typeIntermediateSetComparisonResult);

            final Set<Type> setSimilarity = Collections.unmodifiableSet(elementsInBothSets);
            final SetDifference<Type> setDifference = new ImmutableSetDifference<>(elementsOnlyInLeftSet, elementsOnlyInRightSet);

            return new ImmutableSetComparisonResult<>(hasSetSimilarity, setSimilarity, hasSetDifference,
                    setDifference);
        } else {
            return EmptyComparisonResults.emptySetComparisonResult();
        }
    }
}
