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
 * A single path element from a CtPath.
 * <p>
 * Internal interface, not meant to be used by client code.
 *
 * @param <P>
 * 		the type of the queried elements
 * @param <T>
 * 		the type of the returned elements
 */
public interface CtPathElement<P extends spoon.reflect.declaration.CtElement, T extends spoon.reflect.declaration.CtElement> {
    /**
     * Get elements childs of roots that match with this path.
     */
    java.util.Collection<T> getElements(java.util.Collection<P> roots);

    /**
     * Add a path argument.
     *
     * For instance, addArgument("index",3) will select only the third element in an indexed list.
     */
    <C extends spoon.reflect.path.impl.CtPathElement<P, T>> C addArgument(java.lang.String key, java.lang.String value);
}

