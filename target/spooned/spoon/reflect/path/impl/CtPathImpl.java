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
 * Default implementation for a CtPath
 */
public class CtPathImpl implements spoon.reflect.path.CtPath {
    private java.util.LinkedList<spoon.reflect.path.impl.CtPathElement> elements = new java.util.LinkedList<>();

    public java.util.List<spoon.reflect.path.impl.CtPathElement> getElements() {
        return elements;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtElement> java.util.Collection<T> evaluateOn(spoon.reflect.declaration.CtElement... startNode) {
        java.util.Collection<spoon.reflect.declaration.CtElement> filtered = java.util.Arrays.asList(startNode);
        for (spoon.reflect.path.impl.CtPathElement element : elements) {
            filtered = element.getElements(filtered);
        }
        return ((java.util.Collection<T>) (filtered));
    }

    public spoon.reflect.path.impl.CtPathImpl addFirst(spoon.reflect.path.impl.CtPathElement element) {
        elements.addFirst(element);
        return this;
    }

    public spoon.reflect.path.impl.CtPathImpl addLast(spoon.reflect.path.impl.CtPathElement element) {
        elements.addLast(element);
        return this;
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder str = new java.lang.StringBuilder();
        for (spoon.reflect.path.impl.CtPathElement element : elements) {
            str.append(element.toString());
        }
        return str.toString();
    }
}

