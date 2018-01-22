package net.mirwaldt.jcomparison.core.collection.list.uniques.api;

import net.mirwaldt.jcomparison.core.pair.api.Pair;

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
public interface UniquesListDifference<ValueType> {
	/**
	 * added values as map with the added element as key and its index as value 
	 */
	Map<ValueType, Integer> getElementsOnlyInLeftList();
	
	/**
	 * added values as map with the retained element as key and its index-change as value.
	 * 
	 * The index-change is a value pair whose left element is the index of the element in the left list
	 * and whose right element is index of the element in the right list. 
	 */
	Map<ValueType, Pair<Integer>> getElementsMovedBetweenLists();
	
	/**
	 * removed values as map with the removed element as key and its index as value 
	 */
	Map<ValueType, Integer> getElementsOnlyInRightList();
}
