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
public interface SubstringSimilarity {
	/**
	 * indicates where the common substring of the left string begins
	 * @return position in the left string; -1 means nowhere
	 */
	int getPositionInLeftString();
	
	/**
	 * indicates where the common substring of the right string begins
	 * @return position in the right string; -1 means nowhere
	 */
	int getPositionInRightString();
	
	/**
	 * returns the similar substring of the left string and the right string
	 * @return substring of the left string
	 */
	String getSimilarSubstring();
	
}
