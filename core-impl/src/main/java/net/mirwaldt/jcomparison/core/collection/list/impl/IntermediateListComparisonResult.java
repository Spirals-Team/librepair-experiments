package net.mirwaldt.jcomparison.core.collection.list.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.primitive.impl.MutableInt;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
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
public class IntermediateListComparisonResult<ValueType> {
    private final Supplier<Map> createMapSupplier;
    private final Function<Map, Map> copyMapFunction;

    private Map<Integer, ValueSimilarity<ValueType>> similarValues = Collections.emptyMap();
    private Map<Integer, Pair<ValueType>> differentValues = Collections.emptyMap();
    private Map<Integer, ComparisonResult<?,?,?>> comparedElements = Collections.emptyMap();

    private Map<ValueType, MutableInt> leftElementFrequencies = Collections.emptyMap();
    private Map<ValueType, MutableInt> rightElementFrequencies = Collections.emptyMap();
    private Map<ValueType, Integer> similarFrequencies = Collections.emptyMap();
    private Map<ValueType, Pair<Integer>> differentFrequencies = Collections.emptyMap();

    private Map<ValueType, List<Integer>> leftElementPositions = Collections.emptyMap();
    private Map<ValueType, List<Integer>> rightElementPositions = Collections.emptyMap();
    private Map<ValueType, ImmutableIntList> similarPositions = Collections.emptyMap();
    private Map<ValueType, Pair<ImmutableIntList>> differentPositions = Collections.emptyMap();

    private Map<ValueType, ImmutableIntList> elementsOnlyInLeftList = Collections.emptyMap();
    private Map<ValueType, ImmutableIntList> elementsOnlyInRightList = Collections.emptyMap();
    
    private Map<Integer, ValueSimilarity<ValueType>> unmodifiableSimilarValues = Collections.emptyMap();
    private Map<Integer, Pair<ValueType>> unmodifiableDifferentValues = Collections.emptyMap();
    private Map<Integer, ComparisonResult<?,?,?>> unmodifiableComparedElements = Collections.emptyMap();

    private Map<ValueType, MutableInt> unmodifiableLeftElementFrequencies = Collections.emptyMap();
    private Map<ValueType, MutableInt> unmodifiableRightElementFrequencies = Collections.emptyMap();
    private Map<ValueType, Integer> unmodifiableSimilarFrequencies = Collections.emptyMap();
    private Map<ValueType, Pair<Integer>> unmodifiableDifferentFrequencies = Collections.emptyMap();
    
    private Map<ValueType, List<Integer>> unmodifiableLeftElementPositions = Collections.emptyMap();
    private Map<ValueType, List<Integer>> unmodifiableRightElementPositions = Collections.emptyMap();
    private Map<ValueType, ImmutableIntList> unmodifiableSimilarPositions = Collections.emptyMap();
    private Map<ValueType, Pair<ImmutableIntList>> unmodifiableDifferentPositions = Collections.emptyMap();

    private Map<ValueType, ImmutableIntList> unmodifiableElementsOnlyInLeftList = Collections.emptyMap();
    private Map<ValueType, ImmutableIntList> unmodifiableElementsOnlyInRightList = Collections.emptyMap();

    public IntermediateListComparisonResult(Supplier<Map> createMapSupplier, Function<Map, Map> copyMapFunction) {
        this.createMapSupplier = createMapSupplier;
        this.copyMapFunction = copyMapFunction;
    }

    public Map<Integer, ValueSimilarity<ValueType>> readSimilarValues() {
        if(unmodifiableSimilarValues == null) {
            unmodifiableSimilarValues = Collections.unmodifiableMap(similarValues);
        }
        return unmodifiableSimilarValues;
    }

    public Map<Integer, ValueSimilarity<ValueType>> copySimilarValues() {
        return (Map<Integer, ValueSimilarity<ValueType>>) copyMapFunction.apply(readSimilarValues());
    }

    public Map<Integer, ValueSimilarity<ValueType>> writeSimilarValues() {
        if (similarValues == Collections.<Integer, ValueSimilarity<ValueType>>emptyMap()) {
            similarValues = createMap(createMapSupplier);
            unmodifiableSimilarValues = null;
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

    public Map<Integer, Pair<ValueType>> writeDifferentValues() {
        if (differentValues == Collections.<Integer, Pair<ValueType>>emptyMap()) {
            differentValues = createMap(createMapSupplier);
            unmodifiableDifferentValues = null;
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

    public Map<Integer, ComparisonResult<?,?,?>> writeComparedElements() {
        if (comparedElements == Collections.<Integer, ComparisonResult<?,?,?>>emptyMap()) {
            comparedElements = createMap(createMapSupplier);
            unmodifiableComparedElements = null;
        }
        return comparedElements;
    }

    public Map<ValueType, MutableInt> readLeftElementFrequencies() {
        if(unmodifiableLeftElementFrequencies == null) {
            unmodifiableLeftElementFrequencies = Collections.unmodifiableMap(leftElementFrequencies);
        }
        return unmodifiableLeftElementFrequencies;
    }

    public Map<ValueType, MutableInt> writeLeftElementFrequencies() {
        if (leftElementFrequencies == Collections.<ValueType, MutableInt>emptyMap()) {
            leftElementFrequencies = createMap(createMapSupplier);
            unmodifiableLeftElementFrequencies = null;
        }
        return leftElementFrequencies;
    }

    public Map<ValueType, MutableInt> readRightElementFrequencies() {
        if(unmodifiableRightElementFrequencies == null) {
            unmodifiableRightElementFrequencies = Collections.unmodifiableMap(rightElementFrequencies);
        }
        return unmodifiableRightElementFrequencies;
    }

    public Map<ValueType, MutableInt> writeRightElementFrequencies() {
        if (rightElementFrequencies == Collections.<ValueType, MutableInt>emptyMap()) {
            rightElementFrequencies = createMap(createMapSupplier);
            unmodifiableRightElementFrequencies = null;
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

    public Map<ValueType, Integer> writeSimilarFrequencies() {
        if (similarFrequencies == Collections.<ValueType, Integer>emptyMap()) {
            similarFrequencies = createMap(createMapSupplier);
            unmodifiableSimilarFrequencies = null;
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

    public Map<ValueType, Pair<Integer>> writeDifferentFrequencies() {
        if (differentFrequencies == Collections.<ValueType, Pair<Integer>>emptyMap()) {
            differentFrequencies = createMap(createMapSupplier);
            unmodifiableDifferentFrequencies = null;
        }
        return differentFrequencies;
    }
    
    public Map<ValueType, List<Integer>> readLeftElementPositions() {
        if(unmodifiableLeftElementPositions == null) {
            unmodifiableLeftElementPositions = Collections.unmodifiableMap(leftElementPositions);
        }
        return unmodifiableLeftElementPositions;
    }

    public Map<ValueType, List<Integer>> copyLeftElementPositions() {
        return (Map<ValueType, List<Integer>>) copyMapFunction.apply(readLeftElementPositions());
    }

    public Map<ValueType, List<Integer>> writeLeftElementPositions() {
        if (leftElementPositions == Collections.<ValueType, List<Integer>>emptyMap()) {
            leftElementPositions = createMap(createMapSupplier);
            unmodifiableLeftElementPositions = null;
        }
        return leftElementPositions;
    }
    
    public Map<ValueType, List<Integer>> readRightElementPositions() {
        if(unmodifiableRightElementPositions == null) {
            unmodifiableRightElementPositions = Collections.unmodifiableMap(rightElementPositions);
        }
        return unmodifiableRightElementPositions;
    }

    public Map<ValueType, List<Integer>> copyRightElementPositions() {
        return (Map<ValueType, List<Integer>>) copyMapFunction.apply(readLeftElementPositions());
    }

    public Map<ValueType, List<Integer>> writeRightElementPositions() {
        if (rightElementPositions == Collections.<ValueType, List<Integer>>emptyMap()) {
            rightElementPositions = createMap(createMapSupplier);
            unmodifiableRightElementPositions = null;
        }
        return rightElementPositions;
    }

    public Map<ValueType, ImmutableIntList> readSimilarPositions() {
        if(unmodifiableSimilarPositions == null) {
            unmodifiableSimilarPositions = Collections.unmodifiableMap(similarPositions);
        }
        return unmodifiableSimilarPositions;
    }

    public Map<ValueType, ImmutableIntList> copySimilarPositions() {
        return (Map<ValueType, ImmutableIntList>) copyMapFunction.apply(readSimilarPositions());
    }

    public Map<ValueType, ImmutableIntList> writeSimilarPositions() {
        if (similarPositions == Collections.<ValueType, ImmutableIntList>emptyMap()) {
            similarPositions = createMap(createMapSupplier);
            unmodifiableSimilarPositions = null;
        }
        return similarPositions;
    }

    public Map<ValueType, Pair<ImmutableIntList>> readDifferentPositions() {
        if(unmodifiableDifferentPositions == null) {
            unmodifiableDifferentPositions = Collections.unmodifiableMap(differentPositions);
        }
        return unmodifiableDifferentPositions;
    }

    public Map<ValueType, Pair<ImmutableIntList>> copyDifferentPositions() {
        return (Map<ValueType, Pair<ImmutableIntList>>) copyMapFunction.apply(readDifferentPositions());
    }

    public Map<ValueType, Pair<ImmutableIntList>> writeDifferentPositions() {
        if (differentPositions == Collections.<ValueType, Pair<ImmutableIntList>>emptyMap()) {
            differentPositions = createMap(createMapSupplier);
            unmodifiableDifferentPositions = null;
        }
        return differentPositions;
    }
    
    public Map<ValueType, ImmutableIntList> readElementsOnlyInLeftList() {
        if(unmodifiableElementsOnlyInLeftList == null) {
            unmodifiableElementsOnlyInLeftList = Collections.unmodifiableMap(elementsOnlyInLeftList);
        }
        return unmodifiableElementsOnlyInLeftList;
    }

    public Map<ValueType, ImmutableIntList> copyElementsOnlyInLeftList() {
        return (Map<ValueType, ImmutableIntList>) copyMapFunction.apply(readElementsOnlyInLeftList());
    }

    public Map<ValueType,ImmutableIntList> writeElementsOnlyInLeftList() {
        if (elementsOnlyInLeftList == Collections.<ValueType, ImmutableIntList>emptyMap()) {
            elementsOnlyInLeftList = createMap(createMapSupplier);
            unmodifiableElementsOnlyInLeftList = null;
        }
        return elementsOnlyInLeftList;
    }

    public Map<ValueType, ImmutableIntList> readElementsOnlyInRightList() {
        if(unmodifiableElementsOnlyInRightList == null) {
            unmodifiableElementsOnlyInRightList = Collections.unmodifiableMap(elementsOnlyInRightList);
        }
        return unmodifiableElementsOnlyInRightList;
    }

    public Map<ValueType, ImmutableIntList> copyElementsOnlyInRightList() {
        return (Map<ValueType, ImmutableIntList>) copyMapFunction.apply(readElementsOnlyInRightList());
    }

    public Map<ValueType, ImmutableIntList> writeElementsOnlyInRightList() {
        if (elementsOnlyInRightList == Collections.<ValueType, ImmutableIntList>emptyMap()) {
            elementsOnlyInRightList = createMap(createMapSupplier);
            unmodifiableElementsOnlyInRightList = null;
        }
        return elementsOnlyInRightList;
    }
}
