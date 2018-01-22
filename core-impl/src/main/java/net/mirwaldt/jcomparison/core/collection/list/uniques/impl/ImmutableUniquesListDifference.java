package net.mirwaldt.jcomparison.core.collection.list.uniques.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListDifference;

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
public class ImmutableUniquesListDifference<ValueType>
		implements UniquesListDifference<ValueType>, Serializable {

	private static final long serialVersionUID = 6260033620990437852L;

	private final Map<ValueType, Integer> elementsOnlyInLeftList;
	private final Map<ValueType, Pair<Integer>> elementsMovedBetweenLists;
	private final Map<ValueType, Integer> elementsOnlyInRightList;

	public ImmutableUniquesListDifference(Map<ValueType, Integer> elementsOnlyInLeftList, Map<ValueType, Pair<Integer>> elementsMovedBetweenLists, Map<ValueType, Integer> elementsOnlyInRightList) {
		if(isUnmodifiable(elementsOnlyInLeftList)) {
			this.elementsOnlyInLeftList = elementsOnlyInLeftList;
		} else {
			this.elementsOnlyInLeftList = Collections.unmodifiableMap(elementsOnlyInLeftList);
		}

		if(isUnmodifiable(elementsMovedBetweenLists)) {
			this.elementsMovedBetweenLists = elementsMovedBetweenLists;
		} else {
			this.elementsMovedBetweenLists = Collections.unmodifiableMap(elementsMovedBetweenLists);
		}

		if(isUnmodifiable(elementsOnlyInRightList)) {
			this.elementsOnlyInRightList = elementsOnlyInRightList;
		} else {
			this.elementsOnlyInRightList = Collections.unmodifiableMap(elementsOnlyInRightList);
		}
	}

	@Override
	public Map<ValueType, Integer> getElementsOnlyInLeftList() {
		return elementsOnlyInLeftList;
	}

	@Override
	public Map<ValueType, Pair<Integer>> getElementsMovedBetweenLists() {
		return elementsMovedBetweenLists;
	}

	@Override
	public Map<ValueType, Integer> getElementsOnlyInRightList() {
		return elementsOnlyInRightList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementsOnlyInRightList == null) ? 0 : elementsOnlyInRightList.hashCode());
		result = prime * result + ((elementsMovedBetweenLists == null) ? 0 : elementsMovedBetweenLists.hashCode());
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
		ImmutableUniquesListDifference other = (ImmutableUniquesListDifference) obj;
		if (elementsOnlyInRightList == null) {
			if (other.elementsOnlyInRightList != null)
				return false;
		} else if (!elementsOnlyInRightList.equals(other.elementsOnlyInRightList))
			return false;
		if (elementsMovedBetweenLists == null) {
			if (other.elementsMovedBetweenLists != null)
				return false;
		} else if (!elementsMovedBetweenLists.equals(other.elementsMovedBetweenLists))
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
		return "ImmutableUniquesListDifference [elementsOnlyInRightList=" + elementsOnlyInRightList + ", movedElements="
				+ elementsMovedBetweenLists + ", elementsOnlyInLeftList=" + elementsOnlyInLeftList + "]";
	}

}
