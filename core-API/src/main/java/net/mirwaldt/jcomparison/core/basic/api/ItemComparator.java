package net.mirwaldt.jcomparison.core.basic.api;

import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.basic.impl.LazyInitializingVisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

import java.util.Iterator;

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
public interface ItemComparator<ItemType, ComparisonResultType extends ComparisonResult<?, ?, ?>> {
	/**
	 * compares two items with each other or throws an exception if the items cannot be compared.
	 *
	 * @param leftItem the left item
	 * @param rightItem the right item
	 * @return the result of the comparison
	 * @throws ComparisonFailedException if the arguments cannot be compared. Can contain a NPE as cause.
	 */
	default ComparisonResultType compare(ItemType leftItem, ItemType rightItem)
			throws ComparisonFailedException {
		return compare(leftItem, rightItem, new LazyInitializingVisitedObjectsTrace(), new ComparatorOptions());
	}

	/**
	 * compares two items with each other or throws an exception if the items cannot be compared.
	 * 
	 * The extra parameter visitedObjectsTrace was introduced because all solutions for "smuggling" the trace
	 * through the comparators needed dirty hacks.
	 * This makes the interface uglier but this tradeoff is much better than all the hacks in the implementations.
	 *
	 * @param leftItem the left item
	 * @param rightItem the right item
	 * @param visitedObjectsTrace the visited objects trace to avoid infinite loops/recursion because of cycles in graphs
	 * @param comparatorOptions options that enable optimizations   
	 * @return the result of the comparison
	 * @throws ComparisonFailedException if the arguments cannot be compared. Can contain a NPE as cause.
	 */
    ComparisonResultType compare(ItemType leftItem,
								 ItemType rightItem, 
								 VisitedObjectsTrace visitedObjectsTrace, 
								 ComparatorOptions comparatorOptions)
			throws ComparisonFailedException;
}
