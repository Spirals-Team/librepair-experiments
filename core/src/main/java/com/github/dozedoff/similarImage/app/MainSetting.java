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
package com.github.dozedoff.similarImage.app;

public interface MainSetting {
	/**
	 * The number of threads to use for hashing and resizing.
	 * 
	 * @return the maximum thread count
	 */
	int threads();

	/**
	 * If ignored images should be included by default.
	 * 
	 * @return if true, ignored images will be included in the results
	 */
	boolean includeIgnoredImages();
}
