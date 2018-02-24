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

public class MainSettingValidator {
	private MainSettingValidator() {
	}

	/**
	 * Validate the setting instance.
	 * 
	 * @param mainSetting
	 *            instance to validate
	 * @throws IllegalArgumentException
	 *             if a property is invalid
	 */
	public static void validate(MainSetting mainSetting) throws IllegalArgumentException {
		if (mainSetting.threads() < 1) {
			throw new IllegalArgumentException("Thread number must be greater than zero");
		}
	}
}
