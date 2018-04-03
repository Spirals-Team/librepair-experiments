package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.pair.api.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
public class ImmutableArrayDifference implements ArrayDifference {
    private final Map<List<Integer>, ?> additionalElementsOnlyInLeftArray;
    private final Map<List<Integer>, ?> additionalElementsOnlyInRightArray;
    private final Map<List<Integer>, Pair<?>> differentValues;

    public ImmutableArrayDifference(Map<List<Integer>, ?> additionalElementsOnlyInLeftArray, Map<List<Integer>, ?> additionalElementsOnlyInRightArray, Map<List<Integer>, Pair<?>> differentValues) {
        this.additionalElementsOnlyInLeftArray = Collections.unmodifiableMap(additionalElementsOnlyInLeftArray);
        this.additionalElementsOnlyInRightArray = Collections.unmodifiableMap(additionalElementsOnlyInRightArray);
        this.differentValues = Collections.unmodifiableMap(differentValues);
    }

    @Override
    public Map<List<Integer>, ?> getAdditionalItemsOnlyInLeftArray() {
        return additionalElementsOnlyInLeftArray;
    }

    @Override
    public Map<List<Integer>, Pair<?>> getDifferentElements() {
        return differentValues;
    }

    @Override
    public Map<List<Integer>, ?> getAdditionalItemsOnlyInRightArray() {
        return additionalElementsOnlyInRightArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableArrayDifference that = (ImmutableArrayDifference) o;

        if (additionalElementsOnlyInLeftArray != null ? !additionalElementsOnlyInLeftArray.equals(that.additionalElementsOnlyInLeftArray) : that.additionalElementsOnlyInLeftArray != null)
            return false;
        if (additionalElementsOnlyInRightArray != null ? !additionalElementsOnlyInRightArray.equals(that.additionalElementsOnlyInRightArray) : that.additionalElementsOnlyInRightArray != null)
            return false;
        return differentValues != null ? differentValues.equals(that.differentValues) : that.differentValues == null;
    }

    @Override
    public int hashCode() {
        int result = additionalElementsOnlyInLeftArray != null ? additionalElementsOnlyInLeftArray.hashCode() : 0;
        result = 31 * result + (additionalElementsOnlyInRightArray != null ? additionalElementsOnlyInRightArray.hashCode() : 0);
        result = 31 * result + (differentValues != null ? differentValues.hashCode() : 0);
        return result;
    }
}
