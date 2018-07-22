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
package spoon.reflect.visitor.printer.change;

import spoon.reflect.cu.SourcePositionHolder;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.path.CtRole;

/**
 * a {@link ChildSourceFragment} of some primitive String token.
 */
public class ConstantSourceFragment implements ChildSourceFragment {

	private final String source;
	private final TokenType type;

	public ConstantSourceFragment(String source, TokenType type) {
		super();
		this.source = source;
		this.type = type;
	}

	@Override
	public String getSourceCode() {
		return source;
	}

	@Override
	public RoleHandler getRoleHandlerInParent() {
		return null;
	}

	@Override
	public TokenType getType() {
		return type;
	}

	@Override
	public boolean hasRole(CtRole role) {
		return false;
	}

	@Override
	public Boolean isModified(ChangeResolver changedRoles) {
		if (type == TokenType.IDENTIFIER) {
			//we do not know the role of the identifier element, so we do not know whether it is modified or not
			return null;
		}
		return false;
	}

	@Override
	public String toString() {
		return "|" + getSourceCode() + "|";
	}

	@Override
	public SourceFragment getSourceFragmentOfElement(SourcePositionHolder element) {
		return null;
	}
}
