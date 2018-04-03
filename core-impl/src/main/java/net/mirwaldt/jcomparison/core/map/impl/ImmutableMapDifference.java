package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;

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
public class ImmutableMapDifference<KeyType, ValueType> implements MapDifference<KeyType, ValueType>, Serializable {
	private final Map<KeyType, ValueType> entriesOnlyInRightMap;
	private final Map<KeyType, ValueType> entriesOnlyInLeftMap;
	private final Map<KeyType, Pair<ValueType>> differentValueEntries;

	public ImmutableMapDifference(Map<KeyType, ValueType> entriesOnlyInLeftMap,
								  Map<KeyType, Pair<ValueType>> differentValueEntries,
								  Map<KeyType, ValueType> entriesOnlyInRightMap) {
		this.entriesOnlyInLeftMap = Collections.unmodifiableMap(entriesOnlyInLeftMap);
		this.differentValueEntries = Collections.unmodifiableMap(differentValueEntries);
		this.entriesOnlyInRightMap = Collections.unmodifiableMap(entriesOnlyInRightMap);
	}

	@Override
	public Map<KeyType, ValueType> getEntriesOnlyInRightMap() {
		return entriesOnlyInRightMap;
	}

	@Override
	public Map<KeyType, Pair<ValueType>> getDifferentEntries() {
		return differentValueEntries;
	}

	@Override
	public Map<KeyType, ValueType> getEntriesOnlyInLeftMap() {
		return entriesOnlyInLeftMap;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ImmutableMapDifference<?, ?> that = (ImmutableMapDifference<?, ?>) o;

		if (entriesOnlyInRightMap != null ? !entriesOnlyInRightMap.equals(that.entriesOnlyInRightMap) : that.entriesOnlyInRightMap != null)
			return false;
		if (entriesOnlyInLeftMap != null ? !entriesOnlyInLeftMap.equals(that.entriesOnlyInLeftMap) : that.entriesOnlyInLeftMap != null)
			return false;
		return differentValueEntries != null ? differentValueEntries.equals(that.differentValueEntries) : that.differentValueEntries == null;
	}

	@Override
	public int hashCode() {
		int result = entriesOnlyInRightMap != null ? entriesOnlyInRightMap.hashCode() : 0;
		result = 31 * result + (entriesOnlyInLeftMap != null ? entriesOnlyInLeftMap.hashCode() : 0);
		result = 31 * result + (differentValueEntries != null ? differentValueEntries.hashCode() : 0);
		return result;
	}
}
