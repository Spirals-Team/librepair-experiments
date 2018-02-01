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

import spoon.pattern.ModelValueResolver;
import spoon.pattern.ParameterValueProvider;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.meta.impl.RoleHandlerHelper;

/**
 * Marks the SubstitutionRequest which has to match whole AST node (not only some attribute of node)
 */
public class SingleNodeMatcherImpl extends SingleValueMatcherImpl {

	protected final ModelValueResolver owner;

	public SingleNodeMatcherImpl(ModelValueResolver owner, CtElement template) {
		super(template);
		this.owner = owner;
	}

	@Override
	protected ParameterValueProvider matchTarget(ParameterValueProvider parameters, Object target) {
		//to be matched attributes must be same or substituted
		//iterate over all attributes of to be matched class
		for (RoleHandler roleHandler : RoleHandlerHelper.getRoleHandlers(((CtElement) target).getClass())) {
			if (PatternMatcher.isMatchingRole(roleHandler, target.getClass())) {
				//this role has to be checked
				parameters = matchesRole(parameters, target, roleHandler);
				if (parameters == null) {
					return null;
				}
			} //else role has to be ignored. It is derived or not relevant for pattern matching process
		}
		return parameters;
	}

	protected ParameterValueProvider matchesRole(ParameterValueProvider parameters, Object target, RoleHandler roleHandler) {
		return PatternMatcher.matches(owner, parameters, roleHandler, roleHandler.getValue(target), roleHandler.getValue(template));
	}
}
