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
package spoon.legacy;


/**
 * Filters elements by name (for instance to find a method). Example:
 *
 * <pre>
 * CtMethod&lt;?&gt; normalFor = type.getElements(
 * 		new NameFilter&lt;CtMethod&lt;?&gt;&gt;(&quot;normalFor&quot;)).get(0);
 * </pre>
 *
 * @deprecated Use {@link NamedElementFilter} instead: the actual NameFilter could return wrongly typed results. NamedElementFilter explicit the use of a type.
 */
@java.lang.Deprecated
public class NameFilter<T extends spoon.reflect.declaration.CtNamedElement> implements spoon.reflect.visitor.Filter<T> {
    private final java.lang.String name;

    /**
     *
     *
     * @param name
     * 		Name of the expected element
     */
    public NameFilter(java.lang.String name) {
        if (name == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.name = name;
    }

    public boolean matches(T element) {
        try {
            return name.equals(element.getSimpleName());
        } catch (java.lang.UnsupportedOperationException e) {
            return false;
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    public java.lang.Class<T> getType() {
        return ((java.lang.Class<T>) (spoon.reflect.declaration.CtNamedElement.class));
    }
}

