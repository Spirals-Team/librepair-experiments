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
package spoon.reflect.path;


/**
 * This builder allow to create some CtPath.
 * <p>
 * Some examples:
 * <p>
 * To create a CtPath that match with any method in fr.spoon.Launcher:
 * <pre>
 * {@code
 * new CtPathBuilder().name("fr").name("spoon").name("Launcher").type("method");
 * }
 * </pre>
 * Created by nicolas on 10/06/2015.
 */
public class CtPathBuilder {
    private java.util.List<spoon.reflect.path.impl.CtPathElement> elements = new java.util.LinkedList<>();

    /**
     * Add a name matcher to this path.
     *
     * @param name
     * 		
     * @param args
     * 		
     * @return 
     */
    public spoon.reflect.path.CtPathBuilder name(java.lang.String name, java.lang.String[]... args) {
        spoon.reflect.path.impl.CtNamedPathElement e = new spoon.reflect.path.impl.CtNamedPathElement(name);
        if (args != null) {
            for (java.lang.String[] arg : args) {
                e.addArgument(arg[0], arg[1]);
            }
        }
        elements.add(e);
        return this;
    }

    /**
     * Add a simple wildcard. Match only on elements child of current one.
     */
    public spoon.reflect.path.CtPathBuilder wildcard() {
        return name(spoon.reflect.path.impl.CtNamedPathElement.WILDCARD);
    }

    /**
     * Add a recursive wildcard. It match on any child and sub-childs.
     */
    public spoon.reflect.path.CtPathBuilder recursiveWildcard() {
        return name(spoon.reflect.path.impl.CtNamedPathElement.RECURSIVE_WILCARD);
    }

    /**
     * Match on element of a given type.
     */
    public <T extends spoon.reflect.declaration.CtElement> spoon.reflect.path.CtPathBuilder type(java.lang.Class<T> type, java.lang.String[]... args) {
        spoon.reflect.path.impl.CtTypedNameElement e = new spoon.reflect.path.impl.CtTypedNameElement(type);
        if (args != null) {
            for (java.lang.String[] arg : args) {
                e.addArgument(arg[0], arg[1]);
            }
        }
        elements.add(e);
        return this;
    }

    /**
     * Match on elements by their role.
     *
     * @see CtRole
     */
    public spoon.reflect.path.CtPathBuilder role(spoon.reflect.path.CtRole role, java.lang.String[]... args) {
        spoon.reflect.path.impl.CtRolePathElement e = new spoon.reflect.path.impl.CtRolePathElement(role);
        if (args != null) {
            for (java.lang.String[] arg : args) {
                e.addArgument(arg[0], arg[1]);
            }
        }
        elements.add(e);
        return this;
    }

    /**
     * Build the CtPath
     */
    public spoon.reflect.path.CtPath build() {
        spoon.reflect.path.impl.CtPathImpl path = new spoon.reflect.path.impl.CtPathImpl();
        for (spoon.reflect.path.impl.CtPathElement el : elements) {
            path.addLast(el);
        }
        return path;
    }
}

