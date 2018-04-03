package net.mirwaldt.jcomparison.core.collection.list;

import net.mirwaldt.jcomparison.core.collection.list.api.ListComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.api.ListDifference;
import net.mirwaldt.jcomparison.core.collection.list.api.ListSimilarity;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;

import java.util.Arrays;
import java.util.List;

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
class FindDifferencesAndSimilaritiesUniquesListComparisonDemo {
	public static void main(String[] args)
			throws IllegalArgumentException, ComparisonFailedException {
		final List<Integer> leftList = Arrays.asList(1, 2, 4);
		final List<Integer> rightList = Arrays.asList(4, 2, 1);

		System.out.println("Left list :" + leftList);
		System.out.println("Right list :" + rightList);
		System.out.println();

		findDifferencesAndSimilaritiesInLists(leftList, rightList);
	}

	private static void findDifferencesAndSimilaritiesInLists(List<Integer> leftList, List<Integer> rightList)
			throws IllegalArgumentException, ComparisonFailedException {
		final ListComparator<Integer> listComparator = DefaultComparators
				.<Integer>createDefaultListComparatorBuilder()
				.build();

		final ListComparisonResult<Integer> listComparisonResult = listComparator.compare(leftList, rightList);
		
		
		final ListSimilarity<Integer> similarities = listComparisonResult.getSimilarities();
		System.out.println("Similarities:");
		System.out.println("Similar elements: " + similarities.getSimilarElements());
		System.out.println("Similar frequencies: " + similarities.getSimilarFrequencies());
		System.out.println("Similar positions :" + similarities.getSimilarPositions());
		
		
		final ListDifference<Integer> differences = listComparisonResult.getDifferences();		
		System.out.println("Differences:");
		System.out.println("Only in left list :  " + differences.getElementsOnlyInLeftList());
		System.out.println("Different in both lists :" + differences.getDifferentElements());
		System.out.println("Only in right list :" + differences.getElementsOnlyInRightList());
		System.out.println("Different frequencies :" + differences.getDifferentFrequencies());
		System.out.println("Different positions :" + differences.getDifferentPositions());
		System.out.println();
	}
}
