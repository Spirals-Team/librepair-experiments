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

import spoon.pattern.NodeAttributeSubstitutionRequest;
import spoon.pattern.NodeAttributesSubstitionRequest;
import spoon.pattern.ParameterValueProvider;
import spoon.reflect.meta.RoleHandler;

/**
 * Marks the SubstitutionRequest which has to match whole AST node (not only some attribute of node)
 */
public class SingleNodeSubstitutionRequestMatcherImpl extends SingleNodeMatcherImpl {

	private final NodeAttributesSubstitionRequest nodeAttributesSubstitionRequest;

	public SingleNodeSubstitutionRequestMatcherImpl(NodeAttributesSubstitionRequest nodeAttributesSubstitionRequest) {
		super(nodeAttributesSubstitionRequest.getOwner(), nodeAttributesSubstitionRequest.getSubstitutedNode());
		this.nodeAttributesSubstitionRequest = nodeAttributesSubstitionRequest;
	}

	protected ParameterValueProvider matchesRole(ParameterValueProvider parameters, Object target, RoleHandler roleHandler) {
		NodeAttributeSubstitutionRequest attrSubstReq = nodeAttributesSubstitionRequest.getAttributeSubstititionRequest(roleHandler.getRole());
		if (attrSubstReq != null) {
			//there exists requests for substitution of this attribute. Check if substitution request parameter accepts current attribute value
			//and store it if yes
			return attrSubstReq.matchesAttributeValue(parameters, roleHandler.getValue(target));
		}
		//no substitution the attributes must 1:1 match
		return super.matchesRole(parameters, target, roleHandler);
	}
}
