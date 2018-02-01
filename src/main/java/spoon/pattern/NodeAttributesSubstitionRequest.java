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
import spoon.reflect.declaration.CtElement;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.PrinterHelper;

/**
 * Represents the request for substitution of attributes of node of Pattern model
 */
public class NodeAttributesSubstitionRequest extends AbstractNodeSubstitutionRequest {

	private Map<CtRole, NodeAttributeSubstitutionRequest> attributeSubstititionRequests;


	public NodeAttributesSubstitionRequest(ModelValueResolver owner, CtElement substitutedNode) {
		super(owner, substitutedNode);
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
	public void forEachParameterInfo(BiConsumer<ParameterInfo, ValueResolver> consumer) {
		if (attributeSubstititionRequests != null) {
			for (NodeAttributeSubstitutionRequest attributeSubstitutionRequest : attributeSubstititionRequests.values()) {
				attributeSubstitutionRequest.forEachParameterInfo(consumer);
			}
		}
	}

	@Override
	public String toString() {
		PrinterHelper printer = new PrinterHelper(getSubstitutedNode().getFactory().getEnvironment());
		printer.write(NodeAttributeSubstitutionRequest.getElementTypeName(getParentNode())).writeln().incTab();
		appendDescription(printer);
		return printer.toString();
	}

	@Override
	void appendDescription(PrinterHelper printer) {
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

	@Override
	public <T extends CtElement> void substitute(SubstitutionCloner substitutionCloner, ResultHolder<T> result) {
		T clone = substitutionCloner.originClone((T) getSubstitutedNode());
		if (attributeSubstititionRequests != null) {
			//there are some attribute substitution requests. Substitute attributes on the cloned node
			for (NodeAttributeSubstitutionRequest attrSubstRequest : attributeSubstititionRequests.values()) {
				attrSubstRequest.substitute(clone, substitutionCloner.getParameters());
			}
		}
		result.addResult(clone);
	}
}
