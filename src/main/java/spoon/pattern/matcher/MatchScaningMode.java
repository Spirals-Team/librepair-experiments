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
 * Represents a Match of TemplateMatcher
 *
 * TODO or remove it
 */
public enum MatchScaningMode {
	/**
	 * Searches for match in all elements of AST, without exceptions
	 */
	CHECK_ALL_CHILDREN,
	/**
	 * Searches for match in all elements of AST,
	 * but do not search in children of matched elements
	 */
	CHECK_CHILDREN_WHEN_MATCH,
	/**
	 * Search for match only on first level.
	 * Never search children for match
	 */
	CHECK_NO_CHILDREN
}
