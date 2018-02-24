/*  Copyright (C) 2017  Nicholas Wright
    
    This file is part of similarImage - A similar image finder using pHash
    
    similarImage is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.dozedoff.similarImage.thread.pipeline;

import java.util.function.Function;

import com.github.dozedoff.similarImage.db.ImageRecord;
import com.github.dozedoff.similarImage.duplicate.DuplicateUtil;
import com.google.common.collect.Multimap;

/**
 * Stage to remove duplicate groups that have the exact same image set.
 * 
 * @author Nicholas Wright
 *
 */
public class RemoveDuplicateSetStage implements Function<Multimap<Long, ImageRecord>, Multimap<Long, ImageRecord>> {
	/**
	 * Remove groups with duplicate image groups. The operation is performed on the parameter.
	 * 
	 * @param toPrune
	 *            the map that will have duplicate image groups removed
	 * @return the same map that was passed as a parameter
	 */
	@Override
	public Multimap<Long, ImageRecord> apply(Multimap<Long, ImageRecord> toPrune) {
		DuplicateUtil.removeDuplicateSets(toPrune);
		return toPrune;
	}
}
