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
package spoon.reflect.path.impl;


/**
 * A CtPathElement that define some roles for matching.
 * <p>
 * Differents roles are define :
 * <ul>
 * <li>statement: match on all statements define in the body of an executable</li>
 * <li>parameter: match on parameter of an executable</li>
 * <li>defaultValue: for value of ctFields</li>
 * </ul>
 */
public class CtRolePathElement extends spoon.reflect.path.impl.AbstractPathElement<spoon.reflect.declaration.CtElement, spoon.reflect.declaration.CtElement> {
    public static final java.lang.String STRING = "#";

    private final spoon.reflect.path.CtRole role;

    public CtRolePathElement(spoon.reflect.path.CtRole role) {
        this.role = role;
    }

    public spoon.reflect.path.CtRole getRole() {
        return role;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((spoon.reflect.path.impl.CtRolePathElement.STRING) + (getRole().toString())) + (getParamString());
    }

    private spoon.reflect.declaration.CtElement getFromSet(java.util.Set set, java.lang.String name) throws spoon.reflect.path.CtPathException {
        for (java.lang.Object o : set) {
            if (o instanceof spoon.reflect.declaration.CtNamedElement) {
                if (((spoon.reflect.declaration.CtNamedElement) (o)).getSimpleName().equals(name)) {
                    return ((spoon.reflect.declaration.CtElement) (o));
                }
            }else
                if (o instanceof spoon.reflect.reference.CtReference) {
                    if (((spoon.reflect.reference.CtReference) (o)).getSimpleName().equals(name)) {
                        return ((spoon.reflect.declaration.CtElement) (o));
                    }
                }else {
                    throw new spoon.reflect.path.CtPathException();
                }

        }
        // Element is not found in set.
        return null;
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.declaration.CtElement> getElements(java.util.Collection<spoon.reflect.declaration.CtElement> roots) {
        java.util.Collection<spoon.reflect.declaration.CtElement> matchs = new java.util.LinkedList<>();
        for (spoon.reflect.declaration.CtElement root : roots) {
            spoon.reflect.meta.RoleHandler roleHandler = spoon.reflect.meta.impl.RoleHandlerHelper.getOptionalRoleHandler(root.getClass(), getRole());
            if (roleHandler != null) {
                switch (roleHandler.getContainerKind()) {
                    case SINGLE :
                        if ((roleHandler.getValue(root)) != null) {
                            matchs.add(roleHandler.getValue(root));
                        }
                        break;
                    case LIST :
                        if (getArguments().containsKey("index")) {
                            int index = java.lang.Integer.parseInt(getArguments().get("index"));
                            if (index < (roleHandler.asList(root).size())) {
                                matchs.add(((spoon.reflect.declaration.CtElement) (roleHandler.asList(root).get(index))));
                            }
                        }else {
                            matchs.addAll(roleHandler.asList(root));
                        }
                        break;
                    case SET :
                        if (getArguments().containsKey("name")) {
                            java.lang.String name = getArguments().get("name");
                            try {
                                spoon.reflect.declaration.CtElement match = getFromSet(roleHandler.asSet(root), name);
                                if (match != null) {
                                    matchs.add(match);
                                }
                            } catch (spoon.reflect.path.CtPathException e) {
                            }
                        }else {
                            matchs.addAll(roleHandler.asSet(root));
                        }
                        // System.err.println("[ERROR] Element not found for name: " + name);
                        // No element found for name.
                        break;
                    case MAP :
                        if (getArguments().containsKey("key")) {
                            java.lang.String name = getArguments().get("key");
                            if (roleHandler.asMap(root).containsKey(name)) {
                                matchs.add(((spoon.reflect.declaration.CtElement) (roleHandler.asMap(root).get(name))));
                            }
                        }else {
                            java.util.Map<java.lang.String, spoon.reflect.declaration.CtElement> map = roleHandler.asMap(root);
                            matchs.addAll(map.values());
                        }
                        break;
                }
            }
        }
        return matchs;
    }
}

