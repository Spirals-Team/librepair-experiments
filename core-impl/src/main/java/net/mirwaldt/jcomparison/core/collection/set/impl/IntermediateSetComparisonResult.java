package net.mirwaldt.jcomparison.core.collection.set.impl;

import net.mirwaldt.jcomparison.core.annotation.NotThreadSafe;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.mirwaldt.jcomparison.core.util.SupplierHelper.createSet;

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
public class IntermediateSetComparisonResult<ValueType> {
    private final Supplier<Set> createSetSupplier;
    private final Function<Set, Set> copySetFunction;

    private Set<ValueType> elementsOnlyInLeftSet = Collections.emptySet();
    private Set<ValueType> elementsInBothSets = Collections.emptySet();
    private Set<ValueType> elementsOnlyInRightSet = Collections.emptySet();

    private Set<ValueType> unmodifiableElementsOnlyInLeftSet = Collections.emptySet();
    private Set<ValueType> unmodifiableElementsInBothSets = Collections.emptySet();
    private Set<ValueType> unmodifiableElementsOnlyInRightSet = Collections.emptySet();

    public IntermediateSetComparisonResult(Supplier<Set> createSetSupplier, Function<Set, Set> copySetFunction) {
        this.createSetSupplier = createSetSupplier;
        this.copySetFunction = copySetFunction;
    }

    public Set<ValueType> readElementsOnlyInLeftSet() {
        if(unmodifiableElementsOnlyInLeftSet == null) {
            unmodifiableElementsOnlyInLeftSet = Collections.unmodifiableSet(elementsOnlyInLeftSet);
        }
        return unmodifiableElementsOnlyInLeftSet;
    }

    public Set<ValueType> readElementsInBothSets() {
        if(unmodifiableElementsInBothSets == null) {
            unmodifiableElementsInBothSets = Collections.unmodifiableSet(elementsInBothSets);
        }
        return unmodifiableElementsInBothSets;
    }

    public Set<ValueType> readElementsOnlyInRightSet() {
        if(unmodifiableElementsOnlyInRightSet == null) {
            unmodifiableElementsOnlyInRightSet = Collections.unmodifiableSet(elementsOnlyInRightSet);
        }
        return unmodifiableElementsOnlyInRightSet;
    }

    public Set<ValueType> copyElementsOnlyInLeftSet() {
        return copySetFunction.apply(readElementsOnlyInLeftSet());
    }

    public Set<ValueType> copyElementsInBothSets() {
        return copySetFunction.apply(readElementsInBothSets());
    }

    public Set<ValueType> copyElementsOnlyInRightSet() {
        return copySetFunction.apply(readElementsOnlyInRightSet());
    }

    public Set<ValueType> writeElementsOnlyInLeftSet() {
        if(elementsOnlyInLeftSet == Collections.emptySet()) {
            elementsOnlyInLeftSet = createSetSupplier.get();
            unmodifiableElementsOnlyInLeftSet = null;
        }
        return elementsOnlyInLeftSet;
    }

    public Set<ValueType> writeElementsInBothSets() {
        if(elementsInBothSets == Collections.emptySet()) {
            elementsInBothSets = createSetSupplier.get();
            unmodifiableElementsInBothSets = null;
        }
        return elementsInBothSets;
    }

    public Set<ValueType> writeElementsOnlyInRightSet() {
        if(elementsOnlyInRightSet == Collections.emptySet()) {
            elementsOnlyInRightSet = createSetSupplier.get();
            unmodifiableElementsOnlyInRightSet = null;
        }
        return elementsOnlyInRightSet;
    }
}
