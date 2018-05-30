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
package spoon.reflect.visitor;


/**
 * This class defines a scanner that maintains a scanning stack for contextual
 * awareness.
 */
public class CtDequeScanner extends spoon.reflect.visitor.CtScanner {
    /**
     * Default constructor.
     */
    public CtDequeScanner() {
    }

    /**
     * The stack of elements.
     */
    protected java.util.Deque<spoon.reflect.declaration.CtElement> elementsDeque = new java.util.ArrayDeque<spoon.reflect.declaration.CtElement>();

    /**
     * Pops the element.
     */
    protected void exit(spoon.reflect.declaration.CtElement e) {
        spoon.reflect.declaration.CtElement ret = elementsDeque.pop();
        if (ret != e) {
            throw new java.lang.RuntimeException("Unconsitant Stack");
        }
        super.exit(e);
    }

    /**
     * Pushes the element.
     */
    protected void enter(spoon.reflect.declaration.CtElement e) {
        elementsDeque.push(e);
        super.enter(e);
    }
}

