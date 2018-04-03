package net.mirwaldt.jcomparison.core.collection.list.impl;

import net.mirwaldt.jcomparison.core.collection.list.api.ListDifference;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;

import java.io.Serializable;
import java.util.Collections;
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
public class ImmutableListDifference<ValueType> implements ListDifference<ValueType>, Serializable {

	private static final long serialVersionUID = 4892309583833669401L;

	private final Map<Integer, Pair<ValueType>> differentElements;
	private final Map<ValueType, Pair<Integer>> differentFrequencies;
	private final Map<ValueType, Pair<ImmutableIntList>> differentPositions;
	private final Map<ValueType, ImmutableIntList> elementsOnlyInLeftList;
	private final Map<ValueType, ImmutableIntList> elementsOnlyInRightList;

	public ImmutableListDifference(Map<Integer, Pair<ValueType>> differentElements,
                                   Map<ValueType, Pair<Integer>> differentFrequencies,
                                   Map<ValueType, Pair<ImmutableIntList>> differentPositions,
                                   Map<ValueType, ImmutableIntList> elementsOnlyInLeftList,
                                   Map<ValueType, ImmutableIntList> elementsOnlyInRightList) {
		this.differentElements = Collections.unmodifiableMap(differentElements);
		this.differentFrequencies = Collections.unmodifiableMap(differentFrequencies);
		this.differentPositions = Collections.unmodifiableMap(differentPositions);
		this.elementsOnlyInLeftList = Collections.unmodifiableMap(elementsOnlyInLeftList);
		this.elementsOnlyInRightList = Collections.unmodifiableMap(elementsOnlyInRightList);
	}

	@Override
	public Map<Integer, Pair<ValueType>> getDifferentElements() {
		return differentElements;
	}

	@Override
	public Map<ValueType, Pair<Integer>> getDifferentFrequencies() {
		return differentFrequencies;
	}

	@Override
	public Map<ValueType, Pair<ImmutableIntList>> getDifferentPositions() {
		return differentPositions;
	}

	@Override
	public Map<ValueType, ImmutableIntList> getElementsOnlyInRightList() {
		return elementsOnlyInRightList;
	}

	@Override
	public Map<ValueType, ImmutableIntList> getElementsOnlyInLeftList() {
		return elementsOnlyInLeftList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ImmutableListDifference<?> that = (ImmutableListDifference<?>) o;

		if (differentElements != null ? !differentElements.equals(that.differentElements) : that.differentElements != null)
			return false;
		if (differentFrequencies != null ? !differentFrequencies.equals(that.differentFrequencies) : that.differentFrequencies != null)
			return false;
		if (differentPositions != null ? !differentPositions.equals(that.differentPositions) : that.differentPositions != null)
			return false;
		if (elementsOnlyInLeftList != null ? !elementsOnlyInLeftList.equals(that.elementsOnlyInLeftList) : that.elementsOnlyInLeftList != null)
			return false;
		return elementsOnlyInRightList != null ? elementsOnlyInRightList.equals(that.elementsOnlyInRightList) : that.elementsOnlyInRightList == null;
	}

	@Override
	public int hashCode() {
		int result = differentElements != null ? differentElements.hashCode() : 0;
		result = 31 * result + (differentFrequencies != null ? differentFrequencies.hashCode() : 0);
		result = 31 * result + (differentPositions != null ? differentPositions.hashCode() : 0);
		result = 31 * result + (elementsOnlyInLeftList != null ? elementsOnlyInLeftList.hashCode() : 0);
		result = 31 * result + (elementsOnlyInRightList != null ? elementsOnlyInRightList.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ImmutableListDifference{" +
				"differentElements=" + differentElements +
				", differentFrequencies=" + differentFrequencies +
				", differentPositions=" + differentPositions +
				", elementsOnlyInLeftList=" + elementsOnlyInLeftList +
				", elementsOnlyInRightList=" + elementsOnlyInRightList +
				'}';
	}
}
