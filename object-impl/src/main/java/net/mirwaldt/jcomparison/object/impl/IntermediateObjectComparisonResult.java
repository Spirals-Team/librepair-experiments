package net.mirwaldt.jcomparison.object.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
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
public class IntermediateObjectComparisonResult {
    private final Supplier<Map> createMapSupplier = HashMap::new;
    private final Function<Map, Map> copyMapFunction;

    private Map<Field, ValueSimilarity<Object>> similarValuesOfFields = Collections.emptyMap();
    private Map<Field, Pair<Object>> differentValuesOfFields = Collections.emptyMap();
    private Map<Field, ComparisonResult<?,?,?>> comparisonResultOfFields = Collections.emptyMap();

    private Map<Field, ValueSimilarity<Object>> unmodifiableSimilarValuesOfFields = Collections.emptyMap();
    private Map<Field, Pair<Object>> unmodifiableDifferentValuesOfFields = Collections.emptyMap();
    private Map<Field, ComparisonResult<?,?,?>> unmodifiableComparisonResultOfFields = Collections.emptyMap();

    public IntermediateObjectComparisonResult(Function<Map, Map> copyMapFunction) {
        this.copyMapFunction = copyMapFunction;
    }

    public Map<Field, ValueSimilarity<Object>> readSimilarValuesOfFields() {
        if (unmodifiableSimilarValuesOfFields == null) {
            unmodifiableSimilarValuesOfFields = Collections.unmodifiableMap(similarValuesOfFields);
        }
        return unmodifiableSimilarValuesOfFields;
    }

    public Map<Field, Pair<Object>> readDifferentValuesOfFields() {
        if (unmodifiableDifferentValuesOfFields == null) {
            unmodifiableDifferentValuesOfFields = Collections.unmodifiableMap(differentValuesOfFields);
        }
        return unmodifiableDifferentValuesOfFields;
    }

    public Map<Field, ComparisonResult<?,?,?>> readComparisonResultOfFields() {
        if (unmodifiableComparisonResultOfFields == null) {
            unmodifiableComparisonResultOfFields = Collections.unmodifiableMap(comparisonResultOfFields);
        }
        return unmodifiableComparisonResultOfFields;
    }

    public Map<Field, ValueSimilarity<Object>> copySimilarValuesOfFields() {
        return (Map<Field, ValueSimilarity<Object>>) copyMapFunction.apply(readSimilarValuesOfFields());
    }

    public Map<Field, Pair<Object>> copyDifferentValuesOfFields() {
        return (Map<Field, Pair<Object>>) copyMapFunction.apply(readDifferentValuesOfFields());
    }

    public Map<Field, ComparisonResult<?,?,?>> copyComparisonResultOfFields() {
        return (Map<Field, ComparisonResult<?,?,?>>) copyMapFunction.apply(readComparisonResultOfFields());
    }

    public Map<Field, ValueSimilarity<Object>> writeSimilarSimilarValuesOfFields() {
        if (similarValuesOfFields == Collections.<Field, ValueSimilarity<Object>>emptyMap()) {
            similarValuesOfFields = createMap(createMapSupplier);
            unmodifiableSimilarValuesOfFields = null;
        }
        return similarValuesOfFields;
    }

    public Map<Field, Pair<Object>> writeDifferentValuesOfFields() {
        if (differentValuesOfFields == Collections.<Field, Pair<Object>>emptyMap()) {
            differentValuesOfFields = createMap(createMapSupplier);
            unmodifiableDifferentValuesOfFields = null;
        }
        return differentValuesOfFields;
    }

    public Map<Field, ComparisonResult<?,?,?>> writeComparisonResultOfFields() {
        if (comparisonResultOfFields == Collections.<Field, ComparisonResult<?,?,?>>emptyMap()) {
            comparisonResultOfFields = createMap(createMapSupplier);
            unmodifiableComparisonResultOfFields = null;
        }
        return comparisonResultOfFields;
    }
}
