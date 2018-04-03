package net.mirwaldt.jcomparison.core.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;


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
    
    @SuppressWarnings("unchecked")
    public static <KeyType, ValueType> Map<KeyType, ValueType> createMap(Supplier<Map> createMapSupplier) {
        return (Map<KeyType, ValueType>) createMapSupplier.get();
    }

    @SuppressWarnings("unchecked")
    public static <ValueType> Set<ValueType> createSet(Supplier<Set> createSetSupplier) {
        return (Set<ValueType>) createSetSupplier.get();
    }

    @SuppressWarnings("unchecked")
    public static <ValueType> List<ValueType> createList(Supplier<List> createListSupplier) {
        return  (List<ValueType>) createListSupplier.get(); 
    }
}
