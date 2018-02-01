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

import spoon.pattern.matcher.ValueMatcher;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.ContainerKind;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.meta.impl.RoleHandlerHelper;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.PrinterHelper;

/**
 * Represents the request for substitution of an primitive attribute of AST node by different value.
 * For example: CtModifiable.modifiers, CtBinaryOperator.OPERATOR_KIND, ...
 * Values of CtNamedElement.simpleName, CtStatement.label, CtComment.comment
 */
public class NodeAttributeSubstitutionRequest implements SubstitutionRequest {
	private final NodeAttributesSubstitionRequest owner;
	private final CtRole roleOfSubstitutedValue;
	private ValueResolver valueResolver;


	NodeAttributeSubstitutionRequest(NodeAttributesSubstitionRequest owner, CtRole roleOfSubstitutedValue) {
		this.owner = owner;
		this.roleOfSubstitutedValue = roleOfSubstitutedValue;
	}

	public NodeAttributesSubstitionRequest getOwner() {
		return owner;
	}

	@Override
	public CtRole getRoleOfSubstitutedValue() {
		return roleOfSubstitutedValue;
	}

	public CtElement getSubstitutedNode() {
		return owner.getSubstitutedNode();
	}

	@Override
	public CtElement getParentNode() {
		return owner.getSubstitutedNode();
	}

	public <T extends CtElement> void substitute(T clonedElement, ParameterValueProvider parameters) {
		RoleHandler roleHandler = RoleHandlerHelper.getRoleHandler(clonedElement.getClass(), getRoleOfSubstitutedValue());
		if (roleHandler.getContainerKind() == ContainerKind.SINGLE) {
			ResultHolder.Single<?> result = new ResultHolder.Single<>(roleHandler.getValueClass());
			valueResolver.resolveValues(result, parameters);
			roleHandler.setValue(clonedElement, result.getResult());
		} else {
			ResultHolder.Multiple<?> result = new ResultHolder.Multiple<>(roleHandler.getValueClass());
			valueResolver.resolveValues(result, parameters);
			roleHandler.setValue(clonedElement, result.getResult());
		}
	}

	/**
	 * @param parameters
	 * @param attributeValue
	 * @return true if `attributeValue` matches with configured pattern
	 */
	public ParameterValueProvider matchesAttributeValue(ParameterValueProvider parameters, Object attributeValue) {
		if (valueResolver instanceof ValueMatcher) {
			return ((ValueMatcher) valueResolver).matches(parameters, attributeValue);
		}
		//TODO check this
//		return PatternMatcher.matches((ModelValueResolver) valueResolver, parameters, attributeValue, getSubstitutedNode());
		throw new UnsupportedOperationException("??");
	}

	@Override
	public void forEachParameterInfo(BiConsumer<ParameterInfo, ValueResolver> consumer) {
		valueResolver.forEachParameterInfo(consumer);
	}

	public ValueResolver getValueResolver() {
		return valueResolver;
	}

	public NodeAttributeSubstitutionRequest setValueResolver(ValueResolver valueResolver) {
		this.valueResolver = valueResolver;
		return this;
	}

	@Override
	public String toString() {
		PrinterHelper printer = new PrinterHelper(getOwner().getSubstitutedNode().getFactory().getEnvironment());
		appendDescription(printer);
		return printer.toString();
	}

	void appendDescription(PrinterHelper printer) {
		printer
		.write(getElementTypeName(getParentNode()))
		.write('.')
		.write(getRoleOfSubstitutedValue().getCamelCaseName())
		.write(" = ")
		.write(valueResolver.toString());
	}

	static String getElementTypeName(CtElement element) {
		String name = element.getClass().getSimpleName();
		if (name.endsWith("Impl")) {
			return name.substring(0, name.length() - 4);
		}
		return name;
	}
}
