package net.mirwaldt.jcomparison.core.iterable.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.mirwaldt.jcomparison.core.util.SupplierHelper.createMap;

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
public class IntermediateEachWithEachComparisonResult {
    private final Supplier<Map> createMapSupplier;
    private final Function<Map, Map> copyMapFunction;

    private Map<Pair<Object>, ComparisonResult<?,?,?>> comparisonResults = Collections.emptyMap();
    private Map<Pair<Object>, ComparisonResult<?,?,?>> unmodifiableComparisonResults = null;

    public IntermediateEachWithEachComparisonResult(
            Supplier<Map> createMapSupplier,
            Function<Map, Map> copyMapFunction) {
        this.createMapSupplier = createMapSupplier;
        this.copyMapFunction = copyMapFunction;
    }

    public Map<Pair<Object>, ComparisonResult<?,?,?>> readComparisonResults() {
        if(unmodifiableComparisonResults == null) {
            unmodifiableComparisonResults = Collections.unmodifiableMap(comparisonResults);
        }
        return unmodifiableComparisonResults;
    }

    public Map<Pair<Object>, ComparisonResult<?,?,?>> copyComparisonResults() {
        return copyMapFunction.apply(readComparisonResults());
    }

    public Map<Pair<Object>, ComparisonResult<?,?,?>> writeComparisonResults() {
        if (comparisonResults == Collections.<Pair<Object>, ComparisonResult<?,?,?>>emptyMap()) {
            comparisonResults = createMap(createMapSupplier);
       }
        return comparisonResults;
    }
}
