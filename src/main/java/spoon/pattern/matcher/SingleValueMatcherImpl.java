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

import spoon.pattern.ParameterValueProvider;

/**
 * Marks the SubstitutionRequest which has to match whole AST node (not only some attribute of node)
 */
public class SingleValueMatcherImpl implements SingleValueMatcher {

	protected final Object template;

	public SingleValueMatcherImpl(Object template) {
		super();
		this.template = template;
	}

	@Override
	public ParameterValueProvider matches(ParameterValueProvider parameters, Object target) {
		if (target == template) {
			return parameters;
		}
		if (target == null || template == null) {
			return null;
		}
		if (target.getClass() != template.getClass()) {
			return null;
		}
		return matchTarget(parameters, target);
	}

	protected ParameterValueProvider matchTarget(ParameterValueProvider parameters, Object target) {
		return target.equals(template) ? parameters : null;
	}
}
