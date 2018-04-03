package net.mirwaldt.jcomparison.core.value.api;

import net.mirwaldt.jcomparison.core.pair.api.Pair;

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
 *   
 * Why this?
 * Most of the time one value is enough but not if an epsilon is used.
 * Then a pair of the left and right value is expected.
 * This interface leaves the choice 
 * between one single value and a pair of values open.
 */
public interface ValueSimilarity<ValueType> {
    boolean isOneSimilarValue();
    ValueType getSimilarValue();
    Pair<ValueType> getSimilarValues();
}
