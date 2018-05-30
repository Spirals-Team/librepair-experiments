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
 * A CtPathElement that match on CtNamedElement#getSimpleName
 */
public class CtNamedPathElement extends spoon.reflect.path.impl.AbstractPathElement<spoon.reflect.declaration.CtElement, spoon.reflect.declaration.CtElement> {
    public static final java.lang.String STRING = ".";

    public static final java.lang.String WILDCARD = "*";

    public static final java.lang.String RECURSIVE_WILCARD = "**";

    private final java.lang.String pattern;

    public CtNamedPathElement(java.lang.String pattern) {
        this.pattern = pattern;
    }

    public java.lang.String getPattern() {
        return pattern;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((spoon.reflect.path.impl.CtNamedPathElement.STRING) + (getPattern())) + (getParamString());
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.declaration.CtElement> getElements(java.util.Collection<spoon.reflect.declaration.CtElement> roots) {
        java.util.Collection<spoon.reflect.declaration.CtElement> results = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtElement element : roots) {
            results.addAll(scanElements(getChilds(element)));
        }
        return results;
    }

    public java.util.Collection<spoon.reflect.declaration.CtElement> scanElements(java.util.Collection<? extends spoon.reflect.declaration.CtElement> roots) {
        spoon.reflect.path.impl.CtNamedPathElement.NameScanner nameScanner = new spoon.reflect.path.impl.CtNamedPathElement.NameScanner();
        if (spoon.reflect.path.impl.CtNamedPathElement.RECURSIVE_WILCARD.equals(pattern)) {
            nameScanner.recurse(roots);
        }else {
            nameScanner.scan(roots);
        }
        return nameScanner.getResults();
    }

    private class NameScanner extends spoon.reflect.visitor.CtInheritanceScanner {
        private java.util.Collection<spoon.reflect.declaration.CtElement> results = new java.util.LinkedList<>();

        NameScanner() {
        }

        @java.lang.Override
        public void scanCtElement(spoon.reflect.declaration.CtElement e) {
            if ((spoon.reflect.path.impl.CtNamedPathElement.WILDCARD.equals(pattern)) || (spoon.reflect.path.impl.CtNamedPathElement.RECURSIVE_WILCARD.equals(pattern))) {
                results.add(e);
            }else
                if ((e instanceof spoon.reflect.declaration.CtNamedElement) && (((spoon.reflect.declaration.CtNamedElement) (e)).getSimpleName().matches(pattern))) {
                    results.add(e);
                }

        }

        private void recurse(java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
            scan(elements);
            for (spoon.reflect.declaration.CtElement element : elements) {
                recurse(getChilds(element));
            }
        }

        public java.util.Collection<spoon.reflect.declaration.CtElement> getResults() {
            return results;
        }
    }
}

