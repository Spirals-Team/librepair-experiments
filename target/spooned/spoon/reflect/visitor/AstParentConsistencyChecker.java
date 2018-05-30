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


public class AstParentConsistencyChecker extends spoon.reflect.visitor.CtScanner {
    private spoon.reflect.declaration.CtElement parent;

    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        if (element == null) {
            return;
        }
        if ((((parent) != null) && (element.isParentInitialized()))// this is the fix of #1747
         && ((element.getParent()) != (parent))) {
            throw new java.lang.IllegalStateException((((((spoon.reflect.visitor.AstParentConsistencyChecker.toDebugString(element)) + " is set as child of\n") + (spoon.reflect.visitor.AstParentConsistencyChecker.toDebugString(element.getParent()))) + "however it is visited as a child of\n") + (spoon.reflect.visitor.AstParentConsistencyChecker.toDebugString(parent))));
        }
        spoon.reflect.declaration.CtElement parent = this.parent;
        this.parent = element;
        super.scan(element);
        this.parent = parent;
    }

    private static java.lang.String toDebugString(spoon.reflect.declaration.CtElement e) {
        return ((((((("Element: " + e) + "\nSignature: ") + (e.getShortRepresentation())) + "\nClass: ") + (e.getClass())) + "\nposition: ") + (e.getPosition())) + "\n";
    }
}

