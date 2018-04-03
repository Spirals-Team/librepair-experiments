package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

import java.util.Collections;
import java.util.List;
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
public class IntermediateArrayComparisonResult {
    private final Supplier<Map> createMapSupplier;
    private final Function<Map, Map> copyMapFunction;

    private Map<List<Integer>, Object> additionalElementsOnlyInLeftArray = Collections.emptyMap();
    private Map<List<Integer>, Object> additionalElementsOnlyInRightArray = Collections.emptyMap();
    private Map<List<Integer>, ValueSimilarity<?>> similarElements = Collections.emptyMap();
    private Map<List<Integer>, Pair<Object>> differentValues = Collections.emptyMap();
    private Map<List<Integer>, ComparisonResult<?,?,?>> comparisonResults = Collections.emptyMap();

    private Map<List<Integer>, Object> unmodifiableAdditionalElementsOnlyInLeftArray = Collections.emptyMap();
    private Map<List<Integer>, Object> unmodifiableAdditionalElementsOnlyInRightArray = Collections.emptyMap();
    private Map<List<Integer>, ValueSimilarity<?>> unmodifiableSimilarElement = Collections.emptyMap();
    private Map<List<Integer>, Pair<Object>> unmodifiableDifferentValues = Collections.emptyMap();
    private Map<List<Integer>, ComparisonResult<?,?,?>> unmodifiableComparisonResults = Collections.emptyMap();

    public IntermediateArrayComparisonResult(Supplier<Map> createMapSupplier, Function<Map, Map> copyMapFunction) {
        this.createMapSupplier = createMapSupplier;
        this.copyMapFunction = copyMapFunction;
    }

    public Map<List<Integer>, Object> readAdditionalElementsOnlyInLeftArray() {
        if (unmodifiableAdditionalElementsOnlyInLeftArray == null) {
            unmodifiableAdditionalElementsOnlyInLeftArray = Collections.unmodifiableMap(additionalElementsOnlyInLeftArray);
        }
        return unmodifiableAdditionalElementsOnlyInLeftArray;
    }

    public Map<List<Integer>, Object> readAdditionalElementsOnlyInRightArray() {
        if (unmodifiableAdditionalElementsOnlyInRightArray == null) {
            unmodifiableAdditionalElementsOnlyInRightArray = Collections.unmodifiableMap(additionalElementsOnlyInRightArray);
        }
        return unmodifiableAdditionalElementsOnlyInRightArray;
    }

    public Map<List<Integer>, ValueSimilarity<?>> readSimilarElements() {
        if (unmodifiableSimilarElement == null) {
            unmodifiableSimilarElement = Collections.unmodifiableMap(similarElements);
        }
        return unmodifiableSimilarElement;
    }

    public Map<List<Integer>, Pair<Object>> readDifferentValues() {
        if (unmodifiableDifferentValues == null) {
            unmodifiableDifferentValues = Collections.unmodifiableMap(differentValues);
        }
        return unmodifiableDifferentValues;
    }

    public Map<List<Integer>, ComparisonResult<?,?,?>> readComparisonResults() {
        if (unmodifiableComparisonResults == null) {
            unmodifiableComparisonResults = Collections.unmodifiableMap(comparisonResults);
        }
        return unmodifiableComparisonResults;
    }

    public Map<List<Integer>, Object> copyAdditionalElementsOnlyInLeftArray() {
        return (Map<List<Integer>, Object>) copyMapFunction.apply(additionalElementsOnlyInLeftArray);
    }

    public Map<List<Integer>, Object> copyAdditionalElementsOnlyInRightArray() {
        return (Map<List<Integer>, Object>) copyMapFunction.apply(additionalElementsOnlyInRightArray);
    }

    public Map<List<Integer>, ValueSimilarity<?>> copySimilarElements() {
        return (Map<List<Integer>, ValueSimilarity<?>>) copyMapFunction.apply(similarElements);
    }

    public Map<List<Integer>, Pair<Object>> copyDifferentValues() {
        return (Map<List<Integer>, Pair<Object>>) copyMapFunction.apply(differentValues);
    }

    public Map<List<Integer>, ComparisonResult<?,?,?>> copyComparisonResults() {
        return (Map<List<Integer>, ComparisonResult<?,?,?>>) copyMapFunction.apply(comparisonResults);
    }

    public Map<List<Integer>, Object> writeAdditionalElementsOnlyInLeftArray() {
        if (additionalElementsOnlyInLeftArray == Collections.<List<Integer>, Object>emptyMap()) {
            additionalElementsOnlyInLeftArray = createMap(createMapSupplier);
            unmodifiableAdditionalElementsOnlyInLeftArray = null;
        }
        return additionalElementsOnlyInLeftArray;
    }

    public Map<List<Integer>, Object> writeAdditionalElementsOnlyInRightArray() {
        if (additionalElementsOnlyInRightArray == Collections.<List<Integer>, Object>emptyMap()) {
            additionalElementsOnlyInRightArray = createMap(createMapSupplier);
            unmodifiableAdditionalElementsOnlyInRightArray = null;
       }
        return additionalElementsOnlyInRightArray;
    }

    public Map<List<Integer>, ValueSimilarity<?>> writeSimilarElements() {
        if (similarElements == Collections.<List<Integer>, ValueSimilarity<?>>emptyMap()) {
            similarElements = createMap(createMapSupplier);
            unmodifiableSimilarElement = null;
        }
        return similarElements;
    }

    public Map<List<Integer>, Pair<Object>> writeDifferentValues() {
        if (differentValues == Collections.<List<Integer>, Pair<Object>>emptyMap()) {
            differentValues = createMap(createMapSupplier);
            unmodifiableDifferentValues = null;
        }
        return differentValues;
    }

    public Map<List<Integer>, ComparisonResult<?,?,?>> writeComparisonResults() {
        if (comparisonResults == Collections.<List<Integer>, ComparisonResult<?,?,?>>emptyMap()) {
            comparisonResults = createMap(createMapSupplier);
            unmodifiableComparisonResults = null;
        }
        return comparisonResults;
    }
}
