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

import java.util.List;

import spoon.pattern.ParameterValueProvider;
import spoon.reflect.declaration.CtElement;

/**
 * Holds information about list matching status
 */
public class ListMatch {
	/**
	 * Represents a failed {@link ListMatch}. Means no match.
	 */
	public static final ListMatch EMPTY = new ListMatch(null, null) {
		@Override
		public boolean isMatching() {
			return false;
		}
	};

	private final ParameterValueProvider parameters;
	private final List<? extends Object> targets;

	/**
	 * @param parameters the contains for matching parameters
	 * @param targets List of to be checked target {@link CtElement}s
	 */
	public ListMatch(ParameterValueProvider parameters, List<? extends Object> targets) {
		this.parameters = parameters;
		this.targets = targets;
	}

	/**
	 * @return parameters of last successful match
	 */
	public ParameterValueProvider getParameters() {
		return parameters;
	}

	public List<? extends Object> getTargets() {
		return targets;
	}
	/**
	 * @return true if it contains a match result. false if there is no match
	 */
	public boolean isMatching() {
		return true;
	}
}
