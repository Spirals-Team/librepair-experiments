package net.mirwaldt.jcomparison.core.collection.list.duplicates.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableInt;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;
import net.mirwaldt.jcomparison.core.util.SupplierHelper;

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
public class IntermediateDuplicatesListComparisonResult<ValueType> {
    private final Supplier<Map> createMapSupplier;
    private final Function<Map, Map> copyMapFunction;

    private Map<Integer, ValueType> additionalElementsOnlyInLeftList = Collections.emptyMap();
    private Map<Integer, ValueType> similarValues = Collections.emptyMap();
    private Map<Integer, Pair<ValueType>> differentValues = Collections.emptyMap();
    private Map<Integer, ComparisonResult<?,?,?>> comparedElements = Collections.emptyMap();
    private Map<Integer, ValueType> additionalElementsOnlyInRightList = Collections.emptyMap();

    private Map<ValueType, MutableInt> leftElementFrequencies = Collections.emptyMap();
    private Map<ValueType, MutableInt> rightElementFrequencies = Collections.emptyMap();
    private Map<ValueType, Integer> similarFrequencies = Collections.emptyMap();
    private Map<ValueType, Pair<Integer>> differentFrequencies = Collections.emptyMap();

    private Map<Integer, ValueType> unmodifiableAdditionalElementsOnlyInLeftList = Collections.emptyMap();
    private Map<Integer, ValueType> unmodifiableSimilarValues = Collections.emptyMap();
    private Map<Integer, Pair<ValueType>> unmodifiableDifferentValues = Collections.emptyMap();
    private Map<Integer, ComparisonResult<?,?,?>> unmodifiableComparedElements = Collections.emptyMap();
    private Map<Integer, ValueType> unmodifiableAdditionalElementsOnlyInRightList = Collections.emptyMap();

    private Map<ValueType, MutableInt> unmodifiableLeftElementFrequencies = Collections.emptyMap();
    private Map<ValueType, MutableInt> unmodifiableRightElementFrequencies = Collections.emptyMap();
    private Map<ValueType, Integer> unmodifiableSimilarFrequencies = Collections.emptyMap();
    private Map<ValueType, Pair<Integer>> unmodifiableDifferentFrequencies = Collections.emptyMap();

    public IntermediateDuplicatesListComparisonResult(Supplier<Map> createMapSupplier, Function<Map, Map> copyMapFunction) {
        this.createMapSupplier = createMapSupplier;
        this.copyMapFunction = copyMapFunction;
    }

    public Map<Integer, ValueType> readAdditionalElementsOnlyInLeftList() {
        if(unmodifiableAdditionalElementsOnlyInLeftList == null) {
            unmodifiableAdditionalElementsOnlyInLeftList = Collections.unmodifiableMap(additionalElementsOnlyInLeftList);
        }
        return unmodifiableAdditionalElementsOnlyInLeftList;
    }

    public Map<Integer, ValueType> copyAdditionalElementsOnlyInLeftList() {
        return (Map<Integer, ValueType>) copyMapFunction.apply(readAdditionalElementsOnlyInLeftList());
    }

    public Map<Integer, ValueType> writeAdditionalElementsOnlyInLeftList() throws InvalidSupplyException {
        if (additionalElementsOnlyInLeftList == Collections.<Integer, ValueType>emptyMap()) {
            additionalElementsOnlyInLeftList = createMap(createMapSupplier);
            unmodifiableAdditionalElementsOnlyInLeftList = null;

            SupplierHelper.checkSuppliedObjects(additionalElementsOnlyInLeftList, similarValues, differentValues, comparedElements, additionalElementsOnlyInRightList, leftElementFrequencies, rightElementFrequencies, similarFrequencies, differentFrequencies);
        }
        return additionalElementsOnlyInLeftList;
    }

    public Map<Integer, ValueType> readSimilarValues() {
        if(unmodifiableSimilarValues == null) {
            unmodifiableSimilarValues = Collections.unmodifiableMap(similarValues);
        }
        return unmodifiableSimilarValues;
    }

    public Map<Integer, ValueType> copySimilarValues() {
        return (Map<Integer, ValueType>) copyMapFunction.apply(readSimilarValues());
    }

    public Map<Integer, ValueType> writeSimilarValues() throws InvalidSupplyException {
        if (similarValues == Collections.<Integer, ValueType>emptyMap()) {
            similarValues = createMap(createMapSupplier);
            unmodifiableSimilarValues = null;

            SupplierHelper.checkSuppliedObjects(similarValues, additionalElementsOnlyInLeftList, differentValues, comparedElements, additionalElementsOnlyInRightList, leftElementFrequencies, rightElementFrequencies, similarFrequencies, differentFrequencies);
        }
        return similarValues;
    }

    public Map<Integer, Pair<ValueType>> readDifferentValues() {
        if(unmodifiableDifferentValues == null) {
            unmodifiableDifferentValues = Collections.unmodifiableMap(differentValues);
        }
        return unmodifiableDifferentValues;
    }

    public Map<Integer, Pair<ValueType>> copyDifferentValues() {
        return (Map<Integer, Pair<ValueType>>) copyMapFunction.apply(readDifferentValues());
    }

    public Map<Integer, Pair<ValueType>> writeDifferentValues() throws InvalidSupplyException {
        if (differentValues == Collections.<Integer, Pair<ValueType>>emptyMap()) {
            differentValues = createMap(createMapSupplier);
            unmodifiableDifferentValues = null;

            SupplierHelper.checkSuppliedObjects(differentValues, additionalElementsOnlyInLeftList, similarValues, comparedElements, additionalElementsOnlyInRightList, leftElementFrequencies, rightElementFrequencies, similarFrequencies, differentFrequencies);
        }
        return differentValues;
    }

    public Map<Integer, ComparisonResult<?,?,?>> readComparedElements() {
        if(unmodifiableComparedElements == null) {
            unmodifiableComparedElements = Collections.unmodifiableMap(comparedElements);
        }
        return unmodifiableComparedElements;
    }

    public Map<Integer, ComparisonResult<?,?,?>> copyComparedElements() {
        return (Map<Integer, ComparisonResult<?,?,?>>) copyMapFunction.apply(readComparedElements());
    }

    public Map<Integer, ComparisonResult<?,?,?>> writeComparedElements() throws InvalidSupplyException {
        if (comparedElements == Collections.<Integer, ComparisonResult<?,?,?>>emptyMap()) {
            comparedElements = createMap(createMapSupplier);
            unmodifiableComparedElements = null;

            SupplierHelper.checkSuppliedObjects(comparedElements, additionalElementsOnlyInLeftList, similarValues, differentValues, additionalElementsOnlyInRightList, leftElementFrequencies, rightElementFrequencies, similarFrequencies, differentFrequencies);
        }
        return comparedElements;
    }

    public Map<Integer, ValueType> readAdditionalElementsOnlyInRightList() {
        if(unmodifiableAdditionalElementsOnlyInRightList == null) {
            unmodifiableAdditionalElementsOnlyInRightList = Collections.unmodifiableMap(additionalElementsOnlyInRightList);
        }
        return unmodifiableAdditionalElementsOnlyInRightList;
    }

    public Map<Integer, ValueType> copyAdditionalElementsOnlyInRightList() {
        return (Map<Integer, ValueType>) copyMapFunction.apply(readAdditionalElementsOnlyInRightList());
    }

    public Map<Integer, ValueType> writeAdditionalElementsOnlyInRightList() throws InvalidSupplyException {
        if (additionalElementsOnlyInRightList == Collections.<Integer, ValueType>emptyMap()) {
            additionalElementsOnlyInRightList = createMap(createMapSupplier);
            unmodifiableAdditionalElementsOnlyInRightList = null;

            SupplierHelper.checkSuppliedObjects(additionalElementsOnlyInRightList, additionalElementsOnlyInLeftList, similarValues, differentValues, comparedElements, leftElementFrequencies, rightElementFrequencies, similarFrequencies, differentFrequencies);
        }
        return additionalElementsOnlyInRightList;
    }

    public Map<ValueType, MutableInt> readLeftElementFrequencies() {
        if(unmodifiableLeftElementFrequencies == null) {
            unmodifiableLeftElementFrequencies = Collections.unmodifiableMap(leftElementFrequencies);
        }
        return unmodifiableLeftElementFrequencies;
    }

    public Map<ValueType, MutableInt> writeLeftElementFrequencies() throws InvalidSupplyException {
        if (leftElementFrequencies == Collections.<ValueType, MutableInt>emptyMap()) {
            leftElementFrequencies = createMap(createMapSupplier);
            unmodifiableLeftElementFrequencies = null;

            SupplierHelper.checkSuppliedObjects(leftElementFrequencies, additionalElementsOnlyInLeftList, similarValues, differentValues, comparedElements, additionalElementsOnlyInRightList, rightElementFrequencies, similarFrequencies, differentFrequencies);
        }
        return leftElementFrequencies;
    }

    public Map<ValueType, MutableInt> readRightElementFrequencies() {
        if(unmodifiableRightElementFrequencies == null) {
            unmodifiableRightElementFrequencies = Collections.unmodifiableMap(rightElementFrequencies);
        }
        return unmodifiableRightElementFrequencies;
    }

    public Map<ValueType, MutableInt> writeRightElementFrequencies() throws InvalidSupplyException {
        if (rightElementFrequencies == Collections.<ValueType, MutableInt>emptyMap()) {
            rightElementFrequencies = createMap(createMapSupplier);
            unmodifiableRightElementFrequencies = null;

            SupplierHelper.checkSuppliedObjects(rightElementFrequencies, additionalElementsOnlyInLeftList, similarValues, differentValues, comparedElements, additionalElementsOnlyInRightList, leftElementFrequencies, similarFrequencies, differentFrequencies);
        }
        return rightElementFrequencies;
    }

    public Map<ValueType, Integer> readSimilarFrequencies() {
        if(unmodifiableSimilarFrequencies == null) {
            unmodifiableSimilarFrequencies = Collections.unmodifiableMap(similarFrequencies);
        }
        return unmodifiableSimilarFrequencies;
    }

    public Map<ValueType, Integer> copySimilarFrequencies() {
        return (Map<ValueType, Integer>) copyMapFunction.apply(readSimilarFrequencies());
    }

    public Map<ValueType, Integer> writeSimilarFrequencies() throws InvalidSupplyException {
        if (similarFrequencies == Collections.<ValueType, Integer>emptyMap()) {
            similarFrequencies = createMap(createMapSupplier);
            unmodifiableSimilarFrequencies = null;

            SupplierHelper.checkSuppliedObjects(similarFrequencies, additionalElementsOnlyInLeftList, similarValues, differentValues, comparedElements, additionalElementsOnlyInRightList, leftElementFrequencies, rightElementFrequencies, differentFrequencies);
        }
        return similarFrequencies;
    }

    public Map<ValueType, Pair<Integer>> readDifferentFrequencies() {
        if(unmodifiableDifferentFrequencies == null) {
            unmodifiableDifferentFrequencies = Collections.unmodifiableMap(differentFrequencies);
        }
        return unmodifiableDifferentFrequencies;
    }

    public Map<ValueType, Pair<Integer>> copyDifferentFrequencies() {
        return (Map<ValueType, Pair<Integer>>) copyMapFunction.apply(readDifferentFrequencies());
    }

    public Map<ValueType, Pair<Integer>> writeDifferentFrequencies() throws InvalidSupplyException {
        if (differentFrequencies == Collections.<ValueType, Pair<Integer>>emptyMap()) {
            differentFrequencies = createMap(createMapSupplier);
            unmodifiableDifferentFrequencies = null;

            SupplierHelper.checkSuppliedObjects(differentFrequencies, additionalElementsOnlyInLeftList, similarValues, differentValues, comparedElements, additionalElementsOnlyInRightList, leftElementFrequencies, rightElementFrequencies, similarFrequencies);
        }
        return differentFrequencies;
    }
}
