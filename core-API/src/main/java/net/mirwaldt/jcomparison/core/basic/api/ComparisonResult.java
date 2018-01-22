package net.mirwaldt.jcomparison.core.basic.api;

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
 *
 * Base class for all comparison results. All specific ComparisonResult-classes should
 * implement this class and respect the contract
 *
 */
public interface ComparisonResult<SimilarityType, DifferenceType, ObjectIdentifierType> {
	/**
	 * @return whether a similarity was found
	 */
	default boolean hasSimilarity() {
		return false;
	}
	
	/**
	 * @return the similarity if hasSimilarity() returns true
	 * @throws java.util.NoSuchElementException if hasSimilarity() returns false
	 */
	default SimilarityType getSimilarity() {
		throw new NoSuchElementException("No similarity available.");
	}
	
	/**
	 * @return whether a difference was found
	 */
	default boolean hasDifference() {
		return false;
	}
	
	/**
	 * @return the difference if hasDifference() returns true
	 * @throws java.util.NoSuchElementException if hasDifference() returns false
	 */
	default DifferenceType getDifference() {
		throw new NoSuchElementException("No difference available.");
	}

	/**
	 * @return true if the compared objects have at least one reference the comparator followed and compared their values.
	 */
	default boolean hasComparisonResults() {
		return false;
	}

	/**
	 * @return the comparison results for references if hasComparisonResults() returns true
	 * @throws java.util.NoSuchElementException if hasComparisonResults() returns false
	 */
	default Map<ObjectIdentifierType, ComparisonResult<?, ?, ?>> getComparisonResults() {
		throw new NoSuchElementException("No comparison results available.");
	}
}
