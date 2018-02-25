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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import spoon.SpoonException;
import spoon.pattern.matcher.Matchers;
import spoon.pattern.matcher.TobeMatched;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.ContainerKind;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.meta.impl.RoleHandlerHelper;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.PrinterHelper;

import static spoon.pattern.matcher.TobeMatched.getMatchedParameters;
import static spoon.pattern.MatchHelper.createChainOfMatchers;

/**
 */
public class NodeMatcher extends ConstantMatcher<CtElement> {

	private final SubstitutionRequestProvider substitutionRequestProvider;
	private Map<CtRole, NodeAttributeSubstitutionRequest> attributeSubstititionRequests;

	public NodeMatcher(CtElement template, SubstitutionRequestProvider substitutionRequestProvider) {
		super(template);
		this.substitutionRequestProvider = substitutionRequestProvider;
	}

	public CtElement getSubstitutedNode() {
		return template;
	}

	@Override
	public NodeType getNodeType() {
		return attributeSubstititionRequests == null || attributeSubstititionRequests.isEmpty() ? NodeType.IMPLICIT : NodeType.ATTRIBUTE;
	}

	void addAttributeSubstitutionRequest(NodeAttributeSubstitutionRequest sr) {
		if (attributeSubstititionRequests == null) {
			attributeSubstititionRequests = new HashMap<>();
		}
		NodeAttributeSubstitutionRequest old = attributeSubstititionRequests.put(sr.getRoleOfSubstitutedValue(), sr);
		if (old != null && old != sr) {
			throw new SpoonException("Cannot add two substitution requests to the same node attribute " + sr.getRoleOfSubstitutedValue());
		}
	}

	public Map<CtRole, NodeAttributeSubstitutionRequest> getAttributeSubstititionRequests() {
		return attributeSubstititionRequests == null ? Collections.emptyMap() : Collections.unmodifiableMap(attributeSubstititionRequests);
	}

	public NodeAttributeSubstitutionRequest getAttributeSubstititionRequest(CtRole attributeRole) {
		if (attributeSubstititionRequests == null) {
			return null;
		}
		return attributeSubstititionRequests.get(attributeRole);
	}

	@Override
	public void forEachParameterInfo(BiConsumer<ParameterInfo, Parameterized> consumer) {
		if (attributeSubstititionRequests != null) {
			for (NodeAttributeSubstitutionRequest attributeSubstitutionRequest : attributeSubstititionRequests.values()) {
				attributeSubstitutionRequest.forEachParameterInfo(consumer);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> void generateTargets(ResultHolder<U> result, ParameterValueProvider parameters) {
		SubstitutionCloner cloner = new SubstitutionCloner(substitutionRequestProvider, parameters);
		CtElement clone = cloner.originClone(template);
		generateSingleNodeAttributes(clone, parameters);
		result.addResult((U) clone);
	}

	protected void generateSingleNodeAttributes(CtElement clone, ParameterValueProvider parameters) {
		//there are some attribute substitution requests. Substitute attributes on the cloned node
		for (NodeAttributeSubstitutionRequest attrSubstRequest : getAttributeSubstititionRequests().values()) {
			RoleHandler roleHandler = RoleHandlerHelper.getRoleHandler(clone.getClass(), attrSubstRequest.getRoleOfSubstitutedValue());
			if (roleHandler.getContainerKind() == ContainerKind.SINGLE) {
				roleHandler.setValue(clone, attrSubstRequest.getValueResolver().generateTarget(parameters, roleHandler.getValueClass()));
			} else {
				roleHandler.setValue(clone, attrSubstRequest.getValueResolver().generateTargets(parameters, roleHandler.getValueClass()));
			}
		}
	}

	@Override
	protected ParameterValueProvider matchSingleNodeAttributes(ParameterValueProvider parameters, CtElement target) {
		//it is spoon element, it matches if to be matched attributes matches
		//to be matched attributes must be same or substituted
		//iterate over all attributes of to be matched class
		for (RoleHandler roleHandler : RoleHandlerHelper.getRoleHandlers(((CtElement) target).getClass())) {
			if (isMatchingRole(roleHandler, target.getClass())) {
				//this role has to be checked
				parameters = matchesRole(parameters, (CtElement) target, roleHandler);
				if (parameters == null) {
					return null;
				}
			} //else role has to be ignored. It is derived or not relevant for pattern matching process
		}
		return parameters;
	}

	protected ParameterValueProvider matchesRole(ParameterValueProvider parameters, CtElement target, RoleHandler roleHandler) {
		NodeAttributeSubstitutionRequest attrSubstReq = getAttributeSubstititionRequest(roleHandler.getRole());
		if (attrSubstReq != null) {
			//there exists requests for substitution of this attribute. Check if substitution request parameter accepts current attribute value
			//and store it if yes
			return attrSubstReq.matchesAttributeValue(parameters, roleHandler.getValue(target));
		}

		TobeMatched tobeMatched = TobeMatched.create(parameters, roleHandler.getContainerKind(), roleHandler.getValue(target));
		Matchers matchers = createChainOfMatchers(substitutionRequestProvider, roleHandler.getContainerKind(), roleHandler.getValue(template), true);
		return getMatchedParameters(matchers.matchAllWith(tobeMatched));
	}

	private static final Map<CtRole, Class[]> roleToSkippedClass = new HashMap<>();
	static {
		roleToSkippedClass.put(CtRole.COMMENT, new Class[]{Object.class});
		roleToSkippedClass.put(CtRole.POSITION, new Class[]{Object.class});
		roleToSkippedClass.put(CtRole.TYPE, new Class[]{CtInvocation.class, CtExecutableReference.class});
		roleToSkippedClass.put(CtRole.DECLARING_TYPE, new Class[]{CtExecutableReference.class});
		roleToSkippedClass.put(CtRole.INTERFACE, new Class[]{CtTypeReference.class});
		roleToSkippedClass.put(CtRole.MODIFIER, new Class[]{CtTypeReference.class});
		roleToSkippedClass.put(CtRole.SUPER_TYPE, new Class[]{CtTypeReference.class});
	}

	/**
	 * @param roleHandler the to be checked role
	 * @param targetClass the class which is going to be checked
	 * @return true if the role is relevant for matching process
	 */
	private static boolean isMatchingRole(RoleHandler roleHandler, Class<?> targetClass) {
		//match on super roles only. Ignore derived roles
		if (roleHandler.getRole().getSuperRole() != null) {
			return false;
		}
		Class<?>[] classes = roleToSkippedClass.get(roleHandler.getRole());
		if (classes != null) {
			for (Class<?> cls : classes) {
				if (cls.isAssignableFrom(targetClass)) {
					return false;
				}
			}
		}
		return true;
	}
	@Override
	public String toString() {
		PrinterHelper printer = new PrinterHelper(getSubstitutedNode().getFactory().getEnvironment());
		printer.write(NodeAttributeSubstitutionRequest.getElementTypeName(getSubstitutedNode().getParent())).writeln().incTab();
		appendDescription(printer);
		return printer.toString();
	}

	public void appendDescription(PrinterHelper printer) {
		if (attributeSubstititionRequests == null || attributeSubstititionRequests.values().isEmpty()) {
			printer.write("** no attribute substitution **");
		} else {
			boolean multipleAttrs = attributeSubstititionRequests.size() > 1;
			if (multipleAttrs) {
				printer.incTab();
			}
			for (NodeAttributeSubstitutionRequest attributeSubstitutionRequest : attributeSubstititionRequests.values()) {
				if (multipleAttrs) {
					printer.writeln();
				}
				attributeSubstitutionRequest.appendDescription(printer);
			}
			if (multipleAttrs) {
				printer.decTab();
			}
		}
	}
}
