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
package spoon.pattern;

import java.util.HashMap;

import spoon.SpoonException;

/**
 * Provides value of parameter
 */
public class MapBuilder extends HashMap<String, Object> {

	private static final long serialVersionUID = -2795839181703357859L;

	public MapBuilder() {
	}

	@Override
	public MapBuilder put(String parameterName, Object value) {
		Object oldValue = super.put(parameterName, value);
		if (oldValue != null && oldValue != value) {
			throw new SpoonException("Cannot replace already entered value for key " + parameterName);
		}
		return this;
	}
}
