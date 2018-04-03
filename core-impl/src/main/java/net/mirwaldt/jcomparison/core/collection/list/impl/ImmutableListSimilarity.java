package net.mirwaldt.jcomparison.core.collection.list.impl;

import net.mirwaldt.jcomparison.core.collection.list.api.ListSimilarity;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

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
public class ImmutableListSimilarity<ValueType> implements ListSimilarity<ValueType>, Serializable {

	private static final long serialVersionUID = -3259099297551377497L;
	
	private final Map<Integer, ValueSimilarity<ValueType>> similarElements;
	private final Map<ValueType, Integer> similarFrequencies;
	private final Map<ValueType, ImmutableIntList> similarPositions;

	public ImmutableListSimilarity(Map<Integer, ValueSimilarity<ValueType>> similarElements,
                                   Map<ValueType, Integer> similarFrequencies,
                                   Map<ValueType, ImmutableIntList> similarPositions) {
		this.similarElements = Collections.unmodifiableMap(similarElements);
		this.similarFrequencies = Collections.unmodifiableMap(similarFrequencies);
		this.similarPositions = Collections.unmodifiableMap(similarPositions);
	}

	@Override
	public Map<Integer, ValueSimilarity<ValueType>> getSimilarElements() {
		return similarElements;
	}

	@Override
	public Map<ValueType, Integer> getSimilarFrequencies() {
		return similarFrequencies;
	}

	@Override
	public Map<ValueType, ImmutableIntList> getSimilarPositions() {
		return similarPositions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ImmutableListSimilarity<?> that = (ImmutableListSimilarity<?>) o;

		if (similarElements != null ? !similarElements.equals(that.similarElements) : that.similarElements != null)
			return false;
		if (similarFrequencies != null ? !similarFrequencies.equals(that.similarFrequencies) : that.similarFrequencies != null)
			return false;
		return similarPositions != null ? similarPositions.equals(that.similarPositions) : that.similarPositions == null;
	}

	@Override
	public int hashCode() {
		int result = similarElements != null ? similarElements.hashCode() : 0;
		result = 31 * result + (similarFrequencies != null ? similarFrequencies.hashCode() : 0);
		result = 31 * result + (similarPositions != null ? similarPositions.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ImmutableListSimilarity{" +
				"similarElements=" + similarElements +
				", similarFrequencies=" + similarFrequencies +
				", similarPositions=" + similarPositions +
				'}';
	}
}
