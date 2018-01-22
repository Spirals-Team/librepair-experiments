package net.mirwaldt.jcomparison.core.collection.list.duplicates;

import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListDifference;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListSimilarity;
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
class FindDifferencesAndSimilaritiesDuplicatesListComparisonDemo {
	public static void main(String[] args)
			throws IllegalArgumentException, ComparisonFailedException {
		final List<Integer> leftList = Arrays.asList(1, 2, 4);
		final List<Integer> rightList = Arrays.asList(1, 1, 4, 6);

		System.out.println("Left list :" + leftList);
		System.out.println("Right list :" + rightList);
		System.out.println();

		findDifferencesAndSimilaritiesInLists(leftList, rightList);
	}

	private static void findDifferencesAndSimilaritiesInLists(List<Integer> leftList, List<Integer> rightList)
			throws IllegalArgumentException, ComparisonFailedException {
		final DuplicatesListComparator<Integer> duplicatesListComparator = DefaultComparators.<Integer>createDefaultDuplicatesListComparatorBuilder().build();

		final DuplicatesListComparisonResult<Integer> duplicatesListComparisonResult = duplicatesListComparator.compare(leftList, rightList);

		final DuplicatesListDifference<Integer> differences = duplicatesListComparisonResult.getDifference();		
		System.out.println("Differences:");
		System.out.println("Additional in left list :  " + differences.getElementsAdditionalInLeftList());
		System.out.println("Changed in both lists :" + differences.getDifferentElements());
		System.out.println("Additional in right list :" + differences.getElementsAdditionalInRightList());
		System.out.println("Changed frequencies :" + differences.getDifferentFrequencies());
		System.out.println();

		final DuplicatesListSimilarity<Integer> similarities = duplicatesListComparisonResult.getSimilarity();
		System.out.println("Similarities:");
		System.out.println("Similar elements: " + similarities.getSimilarElements());
		System.out.println("Similar frequencies: " + similarities.getSimilarFrequencies());
	}
}
