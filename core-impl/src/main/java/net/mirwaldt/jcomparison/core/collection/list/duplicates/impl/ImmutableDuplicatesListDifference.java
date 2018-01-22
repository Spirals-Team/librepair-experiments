package net.mirwaldt.jcomparison.core.collection.list.duplicates.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListDifference;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

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
public class ImmutableDuplicatesListDifference<ValueType> implements DuplicatesListDifference<ValueType>, Serializable {

	private static final long serialVersionUID = 4892309583833669401L;

	private final Map<Integer, ValueType> elementsOnlyInLeftList;
	private final Map<Integer, Pair<ValueType>> differentElements;
	private final Map<ValueType, Pair<Integer>> differentFrequencies;
	private final Map<Integer, ValueType> elementsOnlyInRightList;

	public ImmutableDuplicatesListDifference(Map<Integer, ValueType> elementsOnlyInLeftList,
                                             Map<Integer, Pair<ValueType>> differentElements, Map<Integer, ValueType> elementsOnlyInRightList,
                                             Map<ValueType, Pair<Integer>> differentFrequencies) {
		if(isUnmodifiable(elementsOnlyInLeftList)) {
			this.elementsOnlyInLeftList = elementsOnlyInLeftList;
		} else {
			this.elementsOnlyInLeftList = Collections.unmodifiableMap(elementsOnlyInLeftList);
		}

		if(isUnmodifiable(differentElements)) {
			this.differentElements = differentElements;
		} else {
			this.differentElements = Collections.unmodifiableMap(differentElements);
		}

		if(isUnmodifiable(differentFrequencies)) {
			this.differentFrequencies = differentFrequencies;
		} else {
			this.differentFrequencies = Collections.unmodifiableMap(differentFrequencies);
		}

		if(isUnmodifiable(elementsOnlyInRightList)) {
			this.elementsOnlyInRightList = elementsOnlyInRightList;
		} else {
			this.elementsOnlyInRightList = Collections.unmodifiableMap(elementsOnlyInRightList);
		}
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
	public Map<Integer, ValueType> getElementsAdditionalInRightList() {
		return elementsOnlyInRightList;
	}

	@Override
	public Map<Integer, ValueType> getElementsAdditionalInLeftList() {
		return elementsOnlyInLeftList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementsOnlyInRightList == null) ? 0 : elementsOnlyInRightList.hashCode());
		result = prime * result + ((differentElements == null) ? 0 : differentElements.hashCode());
		result = prime * result + ((differentFrequencies == null) ? 0 : differentFrequencies.hashCode());
		result = prime * result + ((elementsOnlyInLeftList == null) ? 0 : elementsOnlyInLeftList.hashCode());
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
		ImmutableDuplicatesListDifference other = (ImmutableDuplicatesListDifference) obj;
		if (elementsOnlyInRightList == null) {
			if (other.elementsOnlyInRightList != null)
				return false;
		} else if (!elementsOnlyInRightList.equals(other.elementsOnlyInRightList))
			return false;
		if (differentElements == null) {
			if (other.differentElements != null)
				return false;
		} else if (!differentElements.equals(other.differentElements))
			return false;
		if (differentFrequencies == null) {
			if (other.differentFrequencies != null)
				return false;
		} else if (!differentFrequencies.equals(other.differentFrequencies))
			return false;
		if (elementsOnlyInLeftList == null) {
			if (other.elementsOnlyInLeftList != null)
				return false;
		} else if (!elementsOnlyInLeftList.equals(other.elementsOnlyInLeftList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImmutableDuplicatesListDifference [elementsOnlyInRightList=" + elementsOnlyInRightList + ", differentElements="
				+ differentElements + ", elementsOnlyInLeftList=" + elementsOnlyInLeftList + ", differentFrequencies="
				+ differentFrequencies + "]";
	}

}
