package net.mirwaldt.jcomparison.core.collection.list.duplicates.impl;

import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListSimilarity;

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
public class ImmutableDuplicatesListSimilarity<ValueType> implements DuplicatesListSimilarity<ValueType>, Serializable {

	private static final long serialVersionUID = -3259099297551377497L;
	
	private final Map<Integer, ValueType> similarElements;
	private final Map<ValueType, Integer> similarFrequencies;

	public ImmutableDuplicatesListSimilarity(Map<Integer, ValueType> similarElements,
			Map<ValueType, Integer> similarFrequencies) {
		if(isUnmodifiable(similarElements)) {
			this.similarElements = similarElements;
		} else {
			this.similarElements = Collections.unmodifiableMap(similarElements);
		}

		if(isUnmodifiable(similarFrequencies)) {
			this.similarFrequencies = similarFrequencies;
		} else {
			this.similarFrequencies = Collections.unmodifiableMap(similarFrequencies);
		}
	}

	@Override
	public Map<Integer, ValueType> getSimilarElements() {
		return similarElements;
	}

	@Override
	public Map<ValueType, Integer> getSimilarFrequencies() {
		return similarFrequencies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((similarElements == null) ? 0 : similarElements.hashCode());
		result = prime * result + ((similarFrequencies == null) ? 0 : similarFrequencies.hashCode());
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
		ImmutableDuplicatesListSimilarity other = (ImmutableDuplicatesListSimilarity) obj;
		if (similarElements == null) {
			if (other.similarElements != null)
				return false;
		} else if (!similarElements.equals(other.similarElements))
			return false;
		if (similarFrequencies == null) {
			if (other.similarFrequencies != null)
				return false;
		} else if (!similarFrequencies.equals(other.similarFrequencies))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImmutableDuplicatesListCommon [similarElements=" + similarElements + ", similarFrequencies="
				+ similarFrequencies + "]";
	}
}
