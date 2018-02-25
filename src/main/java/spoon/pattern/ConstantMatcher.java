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

import java.util.function.BiConsumer;

import spoon.SpoonException;

/**
 * Matches when target {@link Object#equals(Object)} == true with template of this {@link ConstantMatcher}
 */
public class ConstantMatcher<T> extends AbstractSimpleValueResolver {
	protected final T template;

	public ConstantMatcher(T template) {
		super();
		this.template = template;
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.IMPLICIT;
	}

	@Override
	public void forEachParameterInfo(BiConsumer<ParameterInfo, Parameterized> consumer) {
		//it has no parameters
	}

	@Override
	public <U> void generateTargets(ResultHolder<U> result, ParameterValueProvider parameters) {
		if (template == null) {
			result.addResult(null);
			return;
		} else if (template instanceof String) {
			result.addResult((U) template);
			return;
		} else if (template instanceof Enum) {
			result.addResult((U) template);
			return;
		}
		throw new SpoonException("Cannot generate constant of class: " + template.getClass());
	}

	@Override
	public ParameterValueProvider matchTarget(Object target, ParameterValueProvider parameters) {
		if (target == null && template == null) {
			return parameters;
		}
		if (target == null || template == null) {
			return null;
		}
		if (target.getClass() != template.getClass()) {
			return null;
		}
		return matchSingleNodeAttributes(parameters, (T) target);
	}

	protected ParameterValueProvider matchSingleNodeAttributes(ParameterValueProvider parameters, T target) {
		//it is non spoon object, it matches if it is equal
		return target.equals(template) ? parameters : null;
	}
}
