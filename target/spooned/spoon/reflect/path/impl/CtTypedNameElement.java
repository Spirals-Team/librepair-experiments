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
 * spoon.reflect.path.impl.CtPathElement that match on CtNamedElement
 */
public class CtTypedNameElement<P extends spoon.reflect.declaration.CtElement, T extends spoon.reflect.declaration.CtElement> extends spoon.reflect.path.impl.AbstractPathElement<P, T> {
    public static final java.lang.String STRING = "/";

    private final java.lang.Class<T> type;

    public CtTypedNameElement(java.lang.Class<T> type) {
        this.type = type;
    }

    public java.lang.Class<T> getType() {
        return type;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((spoon.reflect.path.impl.CtTypedNameElement.STRING) + (type.getSimpleName())) + (getParamString());
    }

    @java.lang.Override
    public java.util.Collection<T> getElements(java.util.Collection<P> roots) {
        java.util.Collection<T> elements = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtElement root : roots) {
            for (spoon.reflect.declaration.CtElement child : getChilds(root)) {
                if (match(child)) {
                    elements.add(((T) (child)));
                }
            }
        }
        return elements;
    }

    private boolean match(spoon.reflect.declaration.CtElement element) {
        return (element != null) && (type.isAssignableFrom(element.getClass()));
    }
}

