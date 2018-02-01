/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.pattern.matcher;

/**
 * Marks the SubstitutionRequest which has to match whole AST node (not only some attribute of node)
 */
public interface ValueMatcher extends SingleValueMatcher {
	/**
	 * @return minimal count of accepted elements
	 */
	int getMinOccurences();
	/**
	 * @return maximal count of accepted elements
	 */
	int getMaxOccurences();

	/**
	 * If two {@link ValueMatcher}s in a list are matching the same element, then
	 * @return true if it should first try to match element using this {@link ValueMatcher}.
	 * false if it should first try to match element using the second {@link ValueMatcher}
	 */
	Quantifier getMatchingStrategy();
}
