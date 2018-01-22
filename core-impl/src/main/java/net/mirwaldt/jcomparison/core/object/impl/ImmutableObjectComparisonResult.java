package net.mirwaldt.jcomparison.core.object.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.impl.ImmutableComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.object.api.ObjectComparisonResult;

import java.lang.reflect.Field;
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
public class ImmutableObjectComparisonResult
        extends ImmutableComparisonResult<Map<Field, Object>, Map<Field, Pair<Object>>, Field>
        implements ObjectComparisonResult {

    public ImmutableObjectComparisonResult(boolean hasSimilarity, Map<Field, Object> similarity, boolean hasDifference, Map<Field, Pair<Object>> difference, boolean hasComparisonResults, Map<Field, ComparisonResult<?, ?, ?>> comparisonResults) {
        super(hasSimilarity, similarity, hasDifference, difference, hasComparisonResults, comparisonResults);
    }
}
