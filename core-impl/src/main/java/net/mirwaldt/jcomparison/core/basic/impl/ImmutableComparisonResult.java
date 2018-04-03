package net.mirwaldt.jcomparison.core.basic.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

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
public class ImmutableComparisonResult<SimilarityType, DifferenceType, ObjectIdentifierType>
		implements ComparisonResult<SimilarityType, DifferenceType, ObjectIdentifierType>, Serializable {

	private static final long serialVersionUID = -4369741787208742667L;
	
	private final boolean hasSimilarity;
	private final SimilarityType similarity;
	
	private final boolean hasDifference;
	private final DifferenceType difference;

	private final boolean hasComparisonResults;
	private final Map<ObjectIdentifierType, ComparisonResult<?, ?, ?>> comparisonResults;

	public ImmutableComparisonResult(boolean hasSimilarity, SimilarityType similarity, boolean hasDifference, DifferenceType difference, boolean hasComparisonResults, Map<ObjectIdentifierType, ComparisonResult<?, ?, ?>> comparisonResults) {
		this.hasSimilarity = hasSimilarity;
		this.similarity = similarity;
		this.hasDifference = hasDifference;
		this.difference = difference;
		this.hasComparisonResults = hasComparisonResults;
		this.comparisonResults = comparisonResults;
	}

	@Override
	public boolean hasSimilarities() {
		return hasSimilarity;
	}

	@Override
	public SimilarityType getSimilarities() {
		if(hasSimilarities()) {
			return similarity;
		} else {
			throw new NoSuchElementException("No similarity available");
		}
	}

	@Override
	public boolean hasDifferences() {
		return hasDifference;
	}

	@Override
	public DifferenceType getDifferences() {
		if(hasDifferences()) {
			return difference;
		} else {
			throw new NoSuchElementException("No difference available");
		}
	}

	@Override
	public boolean hasComparisonResults() {
		return hasComparisonResults;
	}

	@Override
	public Map<ObjectIdentifierType, ComparisonResult<?, ?, ?>> getComparisonResults() {
		if(hasComparisonResults()) {
			return comparisonResults;
		} else {
			throw new NoSuchElementException("No comparison results available");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ImmutableComparisonResult<?, ?, ?> that = (ImmutableComparisonResult<?, ?, ?>) o;

		if (hasSimilarity != that.hasSimilarity) return false;
		if (hasDifference != that.hasDifference) return false;
		if (hasComparisonResults != that.hasComparisonResults) return false;
		if (similarity != null ? !similarity.equals(that.similarity) : that.similarity != null) return false;
		if (difference != null ? !difference.equals(that.difference) : that.difference != null) return false;
		return comparisonResults != null ? comparisonResults.equals(that.comparisonResults) : that.comparisonResults == null;
	}

	@Override
	public int hashCode() {
		int result = (hasSimilarity ? 1 : 0);
		result = 31 * result + (similarity != null ? similarity.hashCode() : 0);
		result = 31 * result + (hasDifference ? 1 : 0);
		result = 31 * result + (difference != null ? difference.hashCode() : 0);
		result = 31 * result + (hasComparisonResults ? 1 : 0);
		result = 31 * result + (comparisonResults != null ? comparisonResults.hashCode() : 0);
		return result;
	}
}
