package net.mirwaldt.jcomparison.core.collection.list.duplicates.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.impl.ImmutableComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListDifference;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListSimilarity;

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
public class ImmutableDuplicatesListComparisonResult<ValueType> extends
		ImmutableComparisonResult<DuplicatesListSimilarity<ValueType>, DuplicatesListDifference<ValueType>, Integer>
		implements DuplicatesListComparisonResult<ValueType> {

	public ImmutableDuplicatesListComparisonResult(boolean hasSimilarity, DuplicatesListSimilarity<ValueType> similarity, boolean hasDifference, DuplicatesListDifference<ValueType> difference, boolean hasComparisonResults, Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults) {
		super(hasSimilarity, similarity, hasDifference, difference, hasComparisonResults, comparisonResults);
	}
}
