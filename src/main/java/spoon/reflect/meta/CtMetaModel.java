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
package spoon.reflect.meta;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.impl.RoleHandlerProvider;
import spoon.reflect.path.CtRole;

/**
 * Meta model of spoon model.
 * Provides access to attributes of nodes by {@link CtRole}
 */
public class CtMetaModel {
	/**
	 * @param element target element
	 * @param role the role which defines an attribute of the element
	 * @return {@link CtRoleHandler} implementation which knows all details about role of element
	 */
	public CtRoleHandler getRoleHandler(CtElement element, CtRole role) {
		return RoleHandlerProvider.getRoleHandler(element.getClass(), role);
	}
	/**
	 * @param element target element
	 * @param role the role which defines to be get attribute of the element
	 * @return value of element on role
	 */
	public Object getValue(CtElement element, CtRole role) {
		CtRoleHandler rh = RoleHandlerProvider.getRoleHandler(element.getClass(), role);
		return rh.getValue(element);
	}
	/**
	 * @param element target element
	 * @param role the role which defines to be set attribute of the element
	 * @param value new value which has to be set to attribute of element defined by role
	 */
	public void setValue(CtElement element, CtRole role, Object value) {
		CtRoleHandler rh = RoleHandlerProvider.getRoleHandler(element.getClass(), role);
		rh.setValue(element, value);
	}
}
