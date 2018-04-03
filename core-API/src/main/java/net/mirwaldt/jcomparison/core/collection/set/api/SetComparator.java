package net.mirwaldt.jcomparison.core.collection.set.api;

import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;

import java.util.Set;

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
public interface SetComparator<ValueType> extends ItemComparator<Set<ValueType>, SetComparisonResult<ValueType>> {
    enum ComparisonFeature {
        ELEMENTS_ONLY_IN_LEFT_SET,
        ELEMENTS_IN_BOTH_SETS,
        ELEMENTS_ONLY_IN_RIGHT_SET
    }
}
