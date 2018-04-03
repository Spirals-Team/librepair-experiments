package net.mirwaldt.jcomparison.provider.api;

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
 * ObjectMetaType can be a type info (Class<?>) or a field info (Field)
 *
 */
public interface ComparatorProvider<ComparatorType> {
	/**
	 * provides an item comparator depending on meta information and the objects themselves.
	 * The latter should enable to avoid too expensive or useless comparisons. 
	 * 
	 * @param objectMeta meta information about the object, e.g. the object type or field information
	 * @param leftObject the left object
	 * @param rightObject the right object
	 * @return the appropriate comparator; may never be null
	 */
	ComparatorType provideComparator(
            Object objectMeta, Object leftObject, Object rightObject);
}
