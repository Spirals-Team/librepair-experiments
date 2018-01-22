package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;
import net.mirwaldt.jcomparison.core.util.SupplierHelper;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;

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
public class IntermediateArrayComparisonResult {
    private final Supplier<Map> createMapSupplier;
    private final Function<Map, Map> copyMapFunction;

    private Map<ImmutableIntList, Object> additionalElementsOnlyInLeftArray = Collections.emptyMap();
    private Map<ImmutableIntList, Object> additionalElementsOnlyInRightArray = Collections.emptyMap();
    private Map<ImmutableIntList, Object> similarElements = Collections.emptyMap();
    private Map<ImmutableIntList, Pair<Object>> differentValues = Collections.emptyMap();
    private Map<ImmutableIntList, ComparisonResult<?,?,?>> comparisonResults = Collections.emptyMap();

    private Map<ImmutableIntList, Object> unmodifiableAdditionalElementsOnlyInLeftArray = Collections.emptyMap();
    private Map<ImmutableIntList, Object> unmodifiableAdditionalElementsOnlyInRightArray = Collections.emptyMap();
    private Map<ImmutableIntList, Object> unmodifiableSimilarElement = Collections.emptyMap();
    private Map<ImmutableIntList, Pair<Object>> unmodifiableDifferentValues = Collections.emptyMap();
    private Map<ImmutableIntList, ComparisonResult<?,?,?>> unmodifiableComparisonResults = Collections.emptyMap();

    public IntermediateArrayComparisonResult(Supplier<Map> createMapSupplier, Function<Map, Map> copyMapFunction) {
        this.createMapSupplier = createMapSupplier;
        this.copyMapFunction = copyMapFunction;
    }

    public Map<ImmutableIntList, Object> readAdditionalElementsOnlyInLeftArray() {
        if (unmodifiableAdditionalElementsOnlyInLeftArray == null) {
            unmodifiableAdditionalElementsOnlyInLeftArray = Collections.unmodifiableMap(additionalElementsOnlyInLeftArray);
        }
        return unmodifiableAdditionalElementsOnlyInLeftArray;
    }

    public Map<ImmutableIntList, Object> readAdditionalElementsOnlyInRightArray() {
        if (unmodifiableAdditionalElementsOnlyInRightArray == null) {
            unmodifiableAdditionalElementsOnlyInRightArray = Collections.unmodifiableMap(additionalElementsOnlyInRightArray);
        }
        return unmodifiableAdditionalElementsOnlyInRightArray;
    }

    public Map<ImmutableIntList, Object> readSimilarElements() {
        if (unmodifiableSimilarElement == null) {
            unmodifiableSimilarElement = Collections.unmodifiableMap(similarElements);
        }
        return unmodifiableSimilarElement;
    }

    public Map<ImmutableIntList, Pair<Object>> readDifferentValues() {
        if (unmodifiableDifferentValues == null) {
            unmodifiableDifferentValues = Collections.unmodifiableMap(differentValues);
        }
        return unmodifiableDifferentValues;
    }

    public Map<ImmutableIntList, ComparisonResult<?,?,?>> readComparisonResults() {
        if (unmodifiableComparisonResults == null) {
            unmodifiableComparisonResults = Collections.unmodifiableMap(comparisonResults);
        }
        return unmodifiableComparisonResults;
    }

    public Map<ImmutableIntList, Object> copyAdditionalElementsOnlyInLeftArray() {
        return (Map<ImmutableIntList, Object>) copyMapFunction.apply(additionalElementsOnlyInLeftArray);
    }

    public Map<ImmutableIntList, Object> copyAdditionalElementsOnlyInRightArray() {
        return (Map<ImmutableIntList, Object>) copyMapFunction.apply(additionalElementsOnlyInRightArray);
    }

    public Map<ImmutableIntList, Object> copySimilarElements() {
        return (Map<ImmutableIntList, Object>) copyMapFunction.apply(similarElements);
    }

    public Map<ImmutableIntList, Pair<Object>> copyDifferentValues() {
        return (Map<ImmutableIntList, Pair<Object>>) copyMapFunction.apply(differentValues);
    }

    public Map<ImmutableIntList, ComparisonResult<?,?,?>> copyComparisonResults() {
        return (Map<ImmutableIntList, ComparisonResult<?,?,?>>) copyMapFunction.apply(comparisonResults);
    }

    public Map<ImmutableIntList, Object> writeAdditionalElementsOnlyInLeftArray() throws InvalidSupplyException {
        if (additionalElementsOnlyInLeftArray == Collections.<ImmutableIntList, Object>emptyMap()) {
            additionalElementsOnlyInLeftArray = createMap(createMapSupplier);
            unmodifiableAdditionalElementsOnlyInLeftArray = null;

            SupplierHelper.checkSuppliedObjects(additionalElementsOnlyInLeftArray, additionalElementsOnlyInRightArray, similarElements, differentValues, comparisonResults);
        }
        return additionalElementsOnlyInLeftArray;
    }

    public Map<ImmutableIntList, Object> writeAdditionalElementsOnlyInRightArray() throws InvalidSupplyException {
        if (additionalElementsOnlyInRightArray == Collections.<ImmutableIntList, Object>emptyMap()) {
            additionalElementsOnlyInRightArray = createMap(createMapSupplier);
            unmodifiableAdditionalElementsOnlyInRightArray = null;

            SupplierHelper.checkSuppliedObjects(additionalElementsOnlyInRightArray, additionalElementsOnlyInLeftArray, similarElements, differentValues, comparisonResults);
        }
        return additionalElementsOnlyInRightArray;
    }

    public Map<ImmutableIntList, Object> writeSimilarElements() throws InvalidSupplyException {
        if (similarElements == Collections.<ImmutableIntList, Object>emptyMap()) {
            similarElements = createMap(createMapSupplier);
            unmodifiableSimilarElement = null;

            SupplierHelper.checkSuppliedObjects(similarElements, additionalElementsOnlyInLeftArray, additionalElementsOnlyInRightArray, differentValues, comparisonResults);
        }
        return similarElements;
    }

    public Map<ImmutableIntList, Pair<Object>> writeDifferentValues() throws InvalidSupplyException {
        if (differentValues == Collections.<ImmutableIntList, Pair<Object>>emptyMap()) {
            differentValues = createMap(createMapSupplier);
            unmodifiableDifferentValues = null;

            SupplierHelper.checkSuppliedObjects(differentValues, additionalElementsOnlyInLeftArray, additionalElementsOnlyInRightArray, similarElements, comparisonResults);
        }
        return differentValues;
    }

    public Map<ImmutableIntList, ComparisonResult<?,?,?>> writeComparisonResults() throws InvalidSupplyException {
        if (comparisonResults == Collections.<ImmutableIntList, ComparisonResult<?,?,?>>emptyMap()) {
            comparisonResults = createMap(createMapSupplier);
            unmodifiableComparisonResults = null;

            SupplierHelper.checkSuppliedObjects(comparisonResults, additionalElementsOnlyInLeftArray, additionalElementsOnlyInRightArray, similarElements, differentValues);
        }
        return comparisonResults;
    }
}
