package net.mirwaldt.jcomparison.core.array.api;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

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
public interface ArrayComparisonResult extends ComparisonResult<Map<List<Integer>, ValueSimilarity<Object>>, ArrayDifference, List<Integer>> {

}
