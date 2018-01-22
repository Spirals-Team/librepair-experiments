package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
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
@NotThreadSafe
public class IntermediateMapComparisonResult<KeyType, ValueType> {
    private final Supplier<Map> createMapSupplier;
    private final Function<Map, Map> copyMapFunction;

    private Map<KeyType, ValueType> entriesOnlyInLeftMap = Collections.emptyMap();
    private Map<KeyType, ValueType> similarValueEntries = Collections.emptyMap();
    private Map<KeyType, Pair<ValueType>> differentValueEntries = Collections.emptyMap();
    private Map<KeyType, ValueType> entriesOnlyInRightMap = Collections.emptyMap();
    private Map<KeyType, ComparisonResult<?,?,?>> comparedObjectEntries = Collections.emptyMap();

    private Map<KeyType, ValueType> unmodifiableEntriesOnlyInLeftMap = Collections.emptyMap();
    private Map<KeyType, ValueType> unmodifiableSimilarValueEntries = Collections.emptyMap();
    private Map<KeyType, Pair<ValueType>> unmodifiableDifferentValueEntries = Collections.emptyMap();
    private Map<KeyType, ValueType> unmodifiableEntriesOnlyInRightMap = Collections.emptyMap();
    private Map<KeyType, ComparisonResult<?,?,?>> unmodifiableComparedObjectEntries = Collections.emptyMap();

    public IntermediateMapComparisonResult(Supplier<Map> createMapSupplier, Function<Map, Map> copyMapFunction) {
        this.createMapSupplier = createMapSupplier;
        this.copyMapFunction = copyMapFunction;
    }

    public Map<KeyType, ValueType> readEntriesOnlyInLeftMap() {
        if(unmodifiableEntriesOnlyInLeftMap == null) {
            unmodifiableEntriesOnlyInLeftMap = Collections.unmodifiableMap(entriesOnlyInLeftMap);
        }
        return unmodifiableEntriesOnlyInLeftMap;
    }

    public Map<KeyType, ValueType> readSimilarValueEntries() {
        if(unmodifiableSimilarValueEntries == null) {
            unmodifiableSimilarValueEntries = Collections.unmodifiableMap(similarValueEntries);
        }
        return unmodifiableSimilarValueEntries;
    }

    public Map<KeyType, Pair<ValueType>> readDifferentValueEntries() {
        if(unmodifiableDifferentValueEntries == null) {
            unmodifiableDifferentValueEntries = Collections.unmodifiableMap(differentValueEntries);
        }
        return unmodifiableDifferentValueEntries;
    }

    public Map<KeyType, ValueType> readEntriesOnlyInRightMap() {
        if(unmodifiableEntriesOnlyInRightMap == null) {
            unmodifiableEntriesOnlyInRightMap = Collections.unmodifiableMap(entriesOnlyInRightMap);
        }
        return unmodifiableEntriesOnlyInRightMap;
    }

    public Map<KeyType, ComparisonResult<?,?,?>> readComparedObjectEntries() {
        if(unmodifiableComparedObjectEntries == null) {
            unmodifiableComparedObjectEntries = Collections.unmodifiableMap(comparedObjectEntries);
        }
        return unmodifiableComparedObjectEntries;
    }


    public Map<KeyType, ValueType> copyEntriesOnlyInLeftMap() {
        return (Map<KeyType, ValueType>) copyMapFunction.apply(readEntriesOnlyInLeftMap());
    }

    public Map<KeyType, ValueType> copySimilarValueEntries() {
        return (Map<KeyType, ValueType>) copyMapFunction.apply(readSimilarValueEntries());
    }

    public Map<KeyType, Pair<ValueType>> copyDifferentValueEntries() {
        return (Map<KeyType, Pair<ValueType>>) copyMapFunction.apply(readDifferentValueEntries());
    }

    public Map<KeyType, ValueType> copyEntriesOnlyInRightMap() {
        return (Map<KeyType, ValueType>) copyMapFunction.apply(readEntriesOnlyInRightMap());
    }

    public Map<KeyType, ComparisonResult<?,?,?>> copyComparedObjectEntries() {
        return (Map<KeyType, ComparisonResult<?,?,?>>) copyMapFunction.apply(readComparedObjectEntries());
    }

    public Map<KeyType, ValueType> writeEntriesOnlyInLeftMap() throws InvalidSupplyException {
        if(entriesOnlyInLeftMap == Collections.emptyMap()) {
            entriesOnlyInLeftMap = createMap(createMapSupplier);
            unmodifiableEntriesOnlyInLeftMap = null;

            SupplierHelper.checkSuppliedObjects(entriesOnlyInLeftMap, similarValueEntries, differentValueEntries, entriesOnlyInRightMap, comparedObjectEntries);
        }
        return entriesOnlyInLeftMap;
    }

    public Map<KeyType, ValueType> writeSimilarSimilarEntries() throws InvalidSupplyException {
        if(similarValueEntries == Collections.emptyMap()) {
            similarValueEntries = createMap(createMapSupplier);
            unmodifiableSimilarValueEntries = null;

            SupplierHelper.checkSuppliedObjects(similarValueEntries, entriesOnlyInLeftMap, differentValueEntries, entriesOnlyInRightMap, comparedObjectEntries);
        }
        return similarValueEntries;
    }

    public Map<KeyType, Pair<ValueType>> writeDifferentValueEntries() throws InvalidSupplyException {
        if(differentValueEntries == Collections.<KeyType, Pair<ValueType>> emptyMap()) {
            differentValueEntries = createMap(createMapSupplier);
            unmodifiableDifferentValueEntries = null;

            SupplierHelper.checkSuppliedObjects(differentValueEntries, entriesOnlyInLeftMap, similarValueEntries, entriesOnlyInRightMap, comparedObjectEntries);
        }
        return differentValueEntries;
    }

    public Map<KeyType, ValueType> writeEntriesOnlyInRightMap() throws InvalidSupplyException {
        if(entriesOnlyInRightMap == Collections.emptyMap()) {
            entriesOnlyInRightMap = createMap(createMapSupplier);
            unmodifiableEntriesOnlyInRightMap = null;

            SupplierHelper.checkSuppliedObjects(entriesOnlyInRightMap, entriesOnlyInLeftMap, similarValueEntries, differentValueEntries, comparedObjectEntries);
        }
        return entriesOnlyInRightMap;
    }

    public Map<KeyType, ComparisonResult<?,?,?>> writeComparedObjectEntries() throws InvalidSupplyException {
        if(comparedObjectEntries == Collections.<KeyType, ComparisonResult<?,?,?>>emptyMap()) {
            comparedObjectEntries = createMap(createMapSupplier);
            unmodifiableComparedObjectEntries = null;

            SupplierHelper.checkSuppliedObjects(comparedObjectEntries, entriesOnlyInLeftMap, entriesOnlyInRightMap, similarValueEntries, differentValueEntries);
        }
        return comparedObjectEntries;
    }
}
