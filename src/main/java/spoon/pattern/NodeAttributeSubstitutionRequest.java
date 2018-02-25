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

import spoon.reflect.declaration.CtElement;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.PrinterHelper;

/**
 * Represents the request for substitution of an primitive attribute of AST node by different value.
 * For example: CtModifiable.modifiers, CtBinaryOperator.OPERATOR_KIND, CtType.typeMembers, CtExecutable.parameters, ...
 * Values of CtNamedElement.simpleName, CtStatement.label, CtComment.comment
 */
public class NodeAttributeSubstitutionRequest {
	private final CtElement substitutedNode;
	private final CtRole roleOfSubstitutedValue;
	private ValueResolver valueResolver;


	NodeAttributeSubstitutionRequest(CtElement substitutedNode, CtRole roleOfSubstitutedValue) {
		this.substitutedNode = substitutedNode;
		this.roleOfSubstitutedValue = roleOfSubstitutedValue;
	}

	/**
	 * @return {@link CtRole} whose value will be substituted
	 */
	public CtRole getRoleOfSubstitutedValue() {
		return roleOfSubstitutedValue;
	}

	public CtElement getSubstitutedNode() {
		return substitutedNode;
	}

	/**
	 * @param parameters
	 * @param attributeValue
	 * @return true if `attributeValue` matches with configured pattern
	 */
	public ParameterValueProvider matchesAttributeValue(ParameterValueProvider parameters, Object attributeValue) {
		if (valueResolver instanceof SimpleValueResolver) {
			return ((SimpleValueResolver) valueResolver).matchTarget(attributeValue, parameters);
		}
		//TODO check this
//		return PatternMatcher.matches((ModelValueResolver) valueResolver, parameters, attributeValue, getSubstitutedNode());
		throw new UnsupportedOperationException("??");
	}

	/**
	 * @param consumer is called once for each ParameterInfo registered on this SubstitutionRequest
	 */
	public void forEachParameterInfo(BiConsumer<ParameterInfo, Parameterized> consumer) {
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
		PrinterHelper printer = new PrinterHelper(getSubstitutedNode().getFactory().getEnvironment());
		appendDescription(printer);
		return printer.toString();
	}

	void appendDescription(PrinterHelper printer) {
		printer
		.write(getElementTypeName(getSubstitutedNode()))
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
