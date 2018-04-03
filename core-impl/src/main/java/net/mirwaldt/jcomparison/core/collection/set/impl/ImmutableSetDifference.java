package net.mirwaldt.jcomparison.core.collection.set.impl;

import net.mirwaldt.jcomparison.core.collection.set.api.SetDifference;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ImmutableSetDifference<ValueType> implements SetDifference<ValueType>, Serializable {

    private static final long serialVersionUID = 5477251886503201984L;

    private final Set<ValueType> elementsOnlyInLeftSet;
    private final Set<ValueType> elementsOnlyInRightSet;

    public ImmutableSetDifference(Set<ValueType> elementsOnlyInLeftSet, Set<ValueType> elementsOnlyInRightSet) {
        this.elementsOnlyInLeftSet = Collections.unmodifiableSet(elementsOnlyInLeftSet);
        this.elementsOnlyInRightSet = Collections.unmodifiableSet(elementsOnlyInRightSet);
    }

    @Override
    public Set<ValueType> getElementsOnlyInRightSet() {
        return elementsOnlyInRightSet;
    }

    @Override
    public Set<ValueType> getElementsOnlyInLeftSet() {
        return elementsOnlyInLeftSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((elementsOnlyInRightSet == null) ? 0 : elementsOnlyInRightSet.hashCode());
        result = prime * result + ((elementsOnlyInLeftSet == null) ? 0 : elementsOnlyInLeftSet.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImmutableSetDifference other = (ImmutableSetDifference) obj;
        if (elementsOnlyInRightSet == null) {
            if (other.elementsOnlyInRightSet != null)
                return false;
        } else if (!elementsOnlyInRightSet.equals(other.elementsOnlyInRightSet))
            return false;
        if (elementsOnlyInLeftSet == null) {
            if (other.elementsOnlyInLeftSet != null)
                return false;
        } else if (!elementsOnlyInLeftSet.equals(other.elementsOnlyInLeftSet))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ImmutableSetDifference [elementsOnlyInRightSet= " + elementsOnlyInRightSet + ", elementsOnlyInLeftSet= " + elementsOnlyInLeftSet + "]";
    }
}
