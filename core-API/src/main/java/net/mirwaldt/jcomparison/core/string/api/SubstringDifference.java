package net.mirwaldt.jcomparison.core.string.api;

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
public interface SubstringDifference {
	/**
	 * indicates where the substring of the left string begins
	 * @return position in the left string; -1 means nowhere
	 */
	int getPositionInLeftString();
	
	/**
	 * returns the substring of the left string which is different to the substring of the right string
	 * @return substring of the left string; is empty string if no substring could be found;
	 */
	String getSubstringInLeftString();

	/**
	 * indicates where the substring of the right string begins
	 * @return position in the right string; -1 means nowhere
	 */
	int getPositionInRightString();
	
	/**
	 * returns the substring of the right string which is different to the substring of the left string
	 * @return substring of the right string; is empty string if no substring could be found;
	 */
	String getSubstringInRightString();
}
