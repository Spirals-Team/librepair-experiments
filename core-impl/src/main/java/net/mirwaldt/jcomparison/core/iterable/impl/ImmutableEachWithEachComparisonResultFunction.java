package net.mirwaldt.jcomparison.core.iterable.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.iterable.api.EachWithEachComparisonResult;

import java.util.Map;
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
public class ImmutableEachWithEachComparisonResultFunction implements Function<IntermediateEachWithEachComparisonResult, EachWithEachComparisonResult> {

    private final Function<IntermediateEachWithEachComparisonResult, Map<Pair<Object>, ComparisonResult<?,?,?>>> accessComparisonResults;

    public ImmutableEachWithEachComparisonResultFunction(Function<IntermediateEachWithEachComparisonResult, Map<Pair<Object>, ComparisonResult<?,?,?>>> accessComparisonResults) {
        this.accessComparisonResults = accessComparisonResults;
    }

    @Override
    public EachWithEachComparisonResult apply(IntermediateEachWithEachComparisonResult intermediateComparisonResult) {
        final boolean hasComparisonResults = !intermediateComparisonResult.readComparisonResults().isEmpty();

        if(hasComparisonResults) {
            final Map<Pair<Object>, ComparisonResult<?,?,?>> comparisonResults = accessComparisonResults.apply(intermediateComparisonResult);

            return new ImmutableEachWithEachComparisonResult(true, comparisonResults);
        } else {
            // TODO: better introduce empty comparison result?
            return new ImmutableEachWithEachComparisonResult(false, null);
        }
    }

}
