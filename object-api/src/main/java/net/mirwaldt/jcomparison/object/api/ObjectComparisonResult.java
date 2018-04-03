package net.mirwaldt.jcomparison.object.api;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public interface ObjectComparisonResult extends ComparisonResult<Map<Field, ValueSimilarity<Object>>, Map<Field, Pair<Object>>, Field> {
    
    @SuppressWarnings("rawtypes")
    ObjectComparisonResult EMPTY_OBJECT_COMPARISON_RESULT = new ObjectComparisonResult() {
        @Override
        public String toString() {
            return "EMPTY_OBJECT_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    static ObjectComparisonResult emptyObjectComparisonResult() {
        return EMPTY_OBJECT_COMPARISON_RESULT;
    }
}
