package net.mirwaldt.jcomparison.core.collection.list.uniques.impl;

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
public class IntermediateUniquesListComparisonResult<ValueType> {
    private final Supplier<Map> createMapSupplier;
    private final Function<Map, Map> copyMapFunction;

    private Map<ValueType, Integer> elementsOnlyInLeftList = Collections.emptyMap();
    private Map<ValueType, Integer> elementsInBothListsAtTheSameIndex = Collections.emptyMap();
    private Map<ValueType, Pair<Integer>> elementsMovedBetweenLists = Collections.emptyMap();
    private Map<ValueType, Integer> elementsOnlyInRightList = Collections.emptyMap();

    private Map<ValueType, Integer> unmodifiableElementsOnlyInLeftList = Collections.emptyMap();
    private Map<ValueType, Integer> unmodifiableElementsInBothListsAtTheSameIndex = Collections.emptyMap();
    private Map<ValueType, Pair<Integer>> unmodifiableElementsMovedBetweenLists = Collections.emptyMap();
    private Map<ValueType, Integer> unmodifiableElementsOnlyInRightList = Collections.emptyMap();

    public IntermediateUniquesListComparisonResult(Supplier<Map> createMapSupplier, Function<Map, Map> copyMapFunction) {
        this.createMapSupplier = createMapSupplier;
        this.copyMapFunction = copyMapFunction;
    }

    public Map<ValueType, Integer> readElementsOnlyInLeftList() {
        if(unmodifiableElementsOnlyInLeftList == null) {
            unmodifiableElementsOnlyInLeftList = Collections.unmodifiableMap(elementsOnlyInLeftList);
        }
        return unmodifiableElementsOnlyInLeftList;
    }

    public Map<ValueType, Integer> readElementsInBothListsAtTheSameIndex() {
        if(unmodifiableElementsInBothListsAtTheSameIndex == null) {
            unmodifiableElementsInBothListsAtTheSameIndex = Collections.unmodifiableMap(elementsInBothListsAtTheSameIndex);
        }
        return unmodifiableElementsInBothListsAtTheSameIndex;
    }

    public Map<ValueType, Pair<Integer>> readElementsMovedBetweenLists() {
        if(unmodifiableElementsMovedBetweenLists == null) {
            unmodifiableElementsMovedBetweenLists = Collections.unmodifiableMap(elementsMovedBetweenLists);
        }
        return unmodifiableElementsMovedBetweenLists;
    }

    public Map<ValueType, Integer> readElementsOnlyInRightList() {
        if(unmodifiableElementsOnlyInRightList == null) {
            unmodifiableElementsOnlyInRightList = Collections.unmodifiableMap(elementsOnlyInRightList);
        }
        return unmodifiableElementsOnlyInRightList;
    }

    public Map<ValueType, Integer> copyElementsOnlyInLeftList() {
        return (Map<ValueType, Integer>) copyMapFunction.apply(elementsOnlyInLeftList);
    }

    public Map<ValueType, Integer> copyElementsInBothListsAtTheSameIndex() {
        return (Map<ValueType, Integer>) copyMapFunction.apply(elementsInBothListsAtTheSameIndex);
    }

    public Map<ValueType, Pair<Integer>> copyElementsMovedBetweenLists() {
        return (Map<ValueType, Pair<Integer>>) copyMapFunction.apply(elementsMovedBetweenLists);
    }

    public Map<ValueType, Integer> copyElementsOnlyInRightList() {
        return (Map<ValueType, Integer>) copyMapFunction.apply(elementsOnlyInRightList);
    }

    public Map<ValueType, Integer> writeElementsOnlyInLeftList() throws InvalidSupplyException {
        if(elementsOnlyInLeftList == Collections.<ValueType, Integer>emptyMap()) {
            elementsOnlyInLeftList = createMap(createMapSupplier);
            unmodifiableElementsOnlyInLeftList = null;

            SupplierHelper.checkSuppliedObjects(elementsOnlyInLeftList, elementsInBothListsAtTheSameIndex, elementsMovedBetweenLists, elementsOnlyInRightList);
        }
        return elementsOnlyInLeftList;
    }

    public Map<ValueType, Integer> writeElementsInBothListsAtTheSameIndex() throws InvalidSupplyException {
        if(elementsInBothListsAtTheSameIndex == Collections.<ValueType, Integer>emptyMap()) {
            elementsInBothListsAtTheSameIndex = createMap(createMapSupplier);
            unmodifiableElementsInBothListsAtTheSameIndex = null;

            SupplierHelper.checkSuppliedObjects(elementsInBothListsAtTheSameIndex, elementsOnlyInLeftList, elementsMovedBetweenLists, elementsOnlyInRightList);
        }
        return elementsInBothListsAtTheSameIndex;
    }

    public Map<ValueType, Pair<Integer>> writeElementsMovedBetweenLists() throws InvalidSupplyException {
        if(elementsMovedBetweenLists == Collections.<ValueType, Pair<Integer>> emptyMap()) {
            elementsMovedBetweenLists = createMap(createMapSupplier);
            unmodifiableElementsMovedBetweenLists = null;

            SupplierHelper.checkSuppliedObjects(elementsMovedBetweenLists, elementsOnlyInLeftList, elementsInBothListsAtTheSameIndex, elementsOnlyInRightList);
        }
        return elementsMovedBetweenLists;
    }

    public Map<ValueType, Integer> writeElementsOnlyInRightList() throws InvalidSupplyException {
        if(elementsOnlyInRightList == Collections.<ValueType, Integer>emptyMap()) {
            elementsOnlyInRightList = createMap(createMapSupplier);
            unmodifiableElementsOnlyInRightList = null;

            SupplierHelper.checkSuppliedObjects(elementsOnlyInRightList, elementsOnlyInLeftList, elementsInBothListsAtTheSameIndex, elementsMovedBetweenLists);
        }
        return elementsOnlyInRightList;
    }
}
