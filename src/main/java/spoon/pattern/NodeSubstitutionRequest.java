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
import spoon.reflect.visitor.PrinterHelper;

/**
 * Represents the request for substitution of one template {@link CtElement} in Pattern model by 0, 1 or more generated CtElements
 * Represents the request for matching 0, 1 or more target {@link CtElement}s one template CtElement in Pattern model
 */
public class NodeSubstitutionRequest extends AbstractNodeSubstitutionRequest {

	private ValueResolver valueResolver;

	NodeSubstitutionRequest(ModelValueResolver owner, ValueResolver valueResolver, CtElement substitutedNode) {
		super(owner, substitutedNode);
		this.valueResolver = valueResolver;
	}

	/**
	 * Substitutes getSubstitutedNode() by the cloned value converted to the type desired by CtRole of getSubstitutedNode().getParent()
	 * @param substitutionCloner a cloning context
	 * @param result collects result of substitution
	 */
	@Override
	public <T extends CtElement> void substitute(SubstitutionCloner substitutionCloner, ResultHolder<T> result) {
		//we are going to substitute origin. Mark it's parent that it should try to simplify parent of substituted code
		substitutionCloner.planSimplification(getSubstitutedNode().getParent());
		valueResolver.resolveValues(result, substitutionCloner.getParameters());
	}

	@Override
	public String toString() {
		PrinterHelper printer = new PrinterHelper(getSubstitutedNode().getFactory().getEnvironment());
		printer
			.write(getElementTypeName(getParentNode()))
			.write('#')
			.write(getSubstitutedNode().getRoleInParent().getCamelCaseName())
			.write('/')
			.write(getElementTypeName(getSubstitutedNode()));
			appendDescription(printer);
		return printer.toString();
	}

	@Override
	void appendDescription(PrinterHelper printer) {
		printer.write(" <= ")
		.write(valueResolver.toString());
	}

	private static String getElementTypeName(CtElement element) {
		String name = element.getClass().getSimpleName();
		if (name.endsWith("Impl")) {
			return name.substring(0, name.length() - 4);
		}
		return name;
	}

	@Override
	public void forEachParameterInfo(BiConsumer<ParameterInfo, ValueResolver> consumer) {
		valueResolver.forEachParameterInfo(consumer);
	}

	@Override
	public ValueResolver getValueResolver() {
		return valueResolver;
	}

	public NodeSubstitutionRequest setValueResolver(ValueResolver valueResolver) {
		this.valueResolver = valueResolver;
		return this;
	}
}
