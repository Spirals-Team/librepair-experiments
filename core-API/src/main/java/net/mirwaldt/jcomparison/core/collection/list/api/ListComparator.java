package net.mirwaldt.jcomparison.core.collection.list.api;

import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;

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
public interface ListComparator<ValueType>
		extends ItemComparator<List<ValueType>, ListComparisonResult<ValueType>> {
	enum ComparisonFeature {
		SIMILAR_ELEMENTS(true, false, true, false, false, false, true),
		DIFFERENT_ELEMENTS(true, false, true, false, false, true, false),
		COMPARE_ELEMENTS_DEEP(false, true, false, false, false, false, false),
		SIMILAR_FREQUENCIES(false, false, false, true, false, false, true),
		DIFFERENT_FREQUENCIES(false, false, false, true, false, true, false),
		SIMILAR_POSITIONS(false, false, false, false, true, false, true),
		DIFFERENT_POSITIONS(false, false, false, false, true, true, false),
		ELEMENTS_ONLY_IN_LEFT_LIST(true, false, true, false, false, true, false),
		ELEMENTS_ONLY_IN_RIGHT_LIST(true, false, true, false, false, true, false);

		private final boolean needsValueComparison;
		private final boolean needsNonValueComparison;
		private final boolean considersOccurence;
		private final boolean considersFrequency;
		private final boolean considersPositions;
		private final boolean isDifference;
		private final boolean isSimilarity;

		ComparisonFeature(boolean needsValueComparison, 
						  boolean needsNonValueComparison, 
						  boolean considersOccurence, 
						  boolean considersFrequency, 
						  boolean considersPositions, 
						  boolean isDifference, 
						  boolean isSimilarity) {
			this.needsValueComparison = needsValueComparison;
			this.needsNonValueComparison = needsNonValueComparison;
			this.considersOccurence = considersOccurence;
			this.considersFrequency = considersFrequency;
			this.considersPositions = considersPositions;
			this.isDifference = isDifference;
			this.isSimilarity = isSimilarity;
		}

		public boolean needsValueComparison() {
			return needsValueComparison;
		}

		public boolean needsNonValueComparison() {
			return needsNonValueComparison;
		}

		public boolean considersOccurence() {
			return considersOccurence;
		}

		public boolean considersFrequency() {
			return considersFrequency;
		}

		public boolean considersPositions() {
			return considersPositions;
		}

		public boolean isDifference() {
			return isDifference;
		}

		public boolean isSimilarity() {
			return isSimilarity;
		}
	}
}
