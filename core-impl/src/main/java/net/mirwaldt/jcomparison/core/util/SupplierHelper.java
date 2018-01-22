package net.mirwaldt.jcomparison.core.util;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.InvalidSupplyException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import static net.mirwaldt.jcomparison.core.util.UnmodifiableChecker.isUnmodifiable;

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
public class SupplierHelper {

    public static void checkSuppliedObject(Object suppliedObject) throws InvalidSupplyException {
        if(suppliedObject == null) {
            throw new InvalidSupplyException("Supplier may not supply null.");
        }
    }

    public static void checkSuppliedObjects(Object suppliedObject, Object ... otherSuppliedObjects) throws InvalidSupplyException {
        for (Object otherSuppliedObject : otherSuppliedObjects) {
            if(suppliedObject == otherSuppliedObject) {
                throw new InvalidSupplyException("Supplier may not supply the same object again.");
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <KeyType, ValueType> Map<KeyType, ValueType> createMap(Supplier<Map> createMapSupplier) throws InvalidSupplyException {
        final Map<KeyType, ValueType> newMap = (Map<KeyType, ValueType>) createMapSupplier.get();

        SupplierHelper.checkSuppliedObject(newMap);

        if (!newMap.isEmpty()) {
            throw new InvalidSupplyException("Supplier '" + Objects.toString(createMapSupplier) + "'  gave a non-empty map.");
        } else if(isUnmodifiable(newMap)) {
            throw new InvalidSupplyException("Supplier  '" + Objects.toString(createMapSupplier) + "' gave a unmodifable map.");
        } else {
            return newMap;
        }
    }

    @SuppressWarnings("unchecked")
    public static <ValueType> Set<ValueType> createSet(Supplier<Set> createSetSupplier) throws InvalidSupplyException {
        final Set<ValueType> newSet = (Set<ValueType>) createSetSupplier.get();

        SupplierHelper.checkSuppliedObject(newSet);

        if(!newSet.isEmpty()) {
            throw new InvalidSupplyException("Supplier '" + Objects.toString(createSetSupplier) + "' gave a non-empty set.");
        } else if(isUnmodifiable(newSet)) {
            throw new InvalidSupplyException("Supplier '" + Objects.toString(createSetSupplier) + "' gave a unmodifiable set.");
        } else {
            return newSet;
        }
    }

    @SuppressWarnings("unchecked")
    public static <ValueType> List<ValueType> createList(Supplier<List> createListSupplier) throws InvalidSupplyException {
        final List<ValueType> newList = (List<ValueType>) createListSupplier.get();

        SupplierHelper.checkSuppliedObject(newList);

        if(!newList.isEmpty()) {
            throw new InvalidSupplyException("Supplier '" + Objects.toString(createListSupplier) + "' gave a non-empty list.");
        } else if(isUnmodifiable(newList)) {
            throw new InvalidSupplyException("Supplier '" + Objects.toString(createListSupplier) + "' gave a unmodifiable list.");
        } else {
            return newList;
        }
    }
}
