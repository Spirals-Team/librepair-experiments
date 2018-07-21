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
 */
public interface ChildSourceFragment  {
	/**
	 * @return origin source code of whole fragment represented by this instance
	 */
	String getSourceCode();
	/**
	 * @return {@link RoleHandler} which describes attributes contained in this fragment
	 */
	RoleHandler getRoleHandlerInParent();
	/**
	 * @param roleHandler
	 * @return true if at least one sub item of this fragment belongs to `role` in parent
	 */
	boolean hasRole(CtRole role);
	/**
	 * @return type of token of this fragment
	 */
	TokenType getType();
	/**
	 * TODO - use Enum to represent null value as UNKNOWN
	 * @param changeResolver
	 * @return whether something in this fragment is modified
	 */
	Boolean isModified(ChangeResolver changeResolver);
	/**
	 * @param element
	 * @return {@link SourceFragment} which represents `element` or null if not found
	 */
	SourceFragment getSourceFragmentOfElement(SourcePositionHolder element);
}
