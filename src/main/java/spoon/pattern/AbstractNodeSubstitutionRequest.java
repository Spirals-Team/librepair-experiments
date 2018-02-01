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

import spoon.reflect.declaration.CtElement;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.PrinterHelper;

/**
 * Represents the generic request for some substitution of Pattern model node
 */
public abstract class AbstractNodeSubstitutionRequest implements NodeSubstitutor, SubstitutionRequest {

	protected final ModelValueResolver owner;
	protected final CtElement substitutedNode;

	public AbstractNodeSubstitutionRequest(ModelValueResolver owner, CtElement substitutedNode) {
		super();
		this.owner = owner;
		this.substitutedNode = substitutedNode;
	}

	public ModelValueResolver getOwner() {
		return owner;
	}

	public CtElement getSubstitutedNode() {
		return substitutedNode;
	}

	public CtRole getRoleOfSubstitutedValue() {
		return getSubstitutedNode().getRoleInParent();
	}

	public CtElement getParentNode() {
		return getSubstitutedNode().getParent();
	}

	abstract void appendDescription(PrinterHelper printer);

	ValueResolver getValueResolver() {
		return null;
	}
}
