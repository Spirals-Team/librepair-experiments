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

import java.util.List;
import java.util.Set;

import spoon.SpoonException;
import spoon.reflect.cu.SourcePositionHolder;
import spoon.reflect.meta.ContainerKind;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.path.CtRole;

/**
 * {@link ChildSourceFragment} of List or Set of {@link SourceFragment}s which belongs to collection role.
 * For example list of Type members or list of parameters, etc.
 * Or set of modifiers and annotations
 */
public class CollectionSourceFragment implements ChildSourceFragment {

	//TODO remove it if not needed
	private final SourcePositionHolder ownerElement;
	private final List<ChildSourceFragment> items;
	private final Set<RoleHandler> rolesInParent;

	public CollectionSourceFragment(SourcePositionHolder ownerElement, List<ChildSourceFragment> items, Set<RoleHandler> foundRoles) {
		super();
		this.ownerElement = ownerElement;
		this.items = items;
		this.rolesInParent = foundRoles;
	}

	@Override
	public String getSourceCode() {
		StringBuilder sb = new StringBuilder();
		for (ChildSourceFragment childSourceFragment : items) {
			sb.append(childSourceFragment.getSourceCode());
		}
		return sb.toString();
	}

	@Override
	public RoleHandler getRoleHandlerInParent() {
		throw new SpoonException("Unsupported operation");
	}

	@Override
	public TokenType getType() {
		return null;
	}

	@Override
	public boolean hasRole(CtRole role) {
		for (ChildSourceFragment childSourceFragment : items) {
			if (childSourceFragment.hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasRoleHandler(RoleHandler roleHandler) {
		for (ChildSourceFragment childSourceFragment : items) {
			if (childSourceFragment.hasRoleHandler(roleHandler)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean isModified(ChangeResolver changeResolver) {
		for (RoleHandler roleHandler : rolesInParent) {
			if (changeResolver.isRoleModified(roleHandler.getRole())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return child source fragments of this collection
	 */
	public List<ChildSourceFragment> getItems() {
		return items;
	}

	@Override
	public SourceFragment getSourceFragmentOfElement(SourcePositionHolder element) {
		for (ChildSourceFragment childSourceFragment : items) {
			SourceFragment sf = childSourceFragment.getSourceFragmentOfElement(element);
			if (sf != null) {
				return sf;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return items.toString();
	}

	/**
	 * @return true if collection contains only children of one role handler with container kind LIST
	 */
	public boolean isOrdered() {
		RoleHandler rh = null;
		for (ChildSourceFragment childSourceFragment : items) {
			RoleHandler childRH = childSourceFragment.getRoleHandlerInParent();
			if (rh == null) {
				rh = childRH;
			} else if (childRH != null && rh != childRH) {
				return false;
			}
		}
		return rh != null && rh.getContainerKind() == ContainerKind.LIST;
	}
}
