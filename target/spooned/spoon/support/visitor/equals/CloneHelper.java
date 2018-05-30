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
package spoon.support.visitor.equals;


/**
 * {@link CloneHelper} is responsible for creating clones of {@link CtElement} AST nodes including the whole subtree.
 *
 * By default, the same instance of {@link CloneHelper} is used for whole clonning process.
 *
 * However, by subclassing this class and overriding method {@link #clone(CtElement)},
 * one can extend and/or modify the cloning behavior.
 *
 * For instance, one can listen to each call to clone and get each pair of `clone source` and `clone target`.
 */
public class CloneHelper {
    public static final spoon.support.visitor.equals.CloneHelper INSTANCE = new spoon.support.visitor.equals.CloneHelper();

    public <T extends spoon.reflect.declaration.CtElement> T clone(T element) {
        final spoon.support.visitor.clone.CloneVisitor cloneVisitor = new spoon.support.visitor.clone.CloneVisitor(this);
        cloneVisitor.scan(element);
        return cloneVisitor.getClone();
    }

    public <T extends spoon.reflect.declaration.CtElement> java.util.Collection<T> clone(java.util.Collection<T> elements) {
        if ((elements == null) || (elements.isEmpty())) {
            return new java.util.ArrayList<>();
        }
        java.util.Collection<T> others = new java.util.ArrayList<>();
        for (T element : elements) {
            addClone(others, element);
        }
        return others;
    }

    public <T extends spoon.reflect.declaration.CtElement> java.util.List<T> clone(java.util.List<T> elements) {
        if (elements instanceof spoon.support.util.EmptyClearableList) {
            return elements;
        }
        if ((elements == null) || (elements.isEmpty())) {
            return new java.util.ArrayList<>();
        }
        java.util.List<T> others = new java.util.ArrayList<>();
        for (T element : elements) {
            addClone(others, element);
        }
        return others;
    }

    public <T extends spoon.reflect.declaration.CtElement> java.util.Set<T> clone(java.util.Set<T> elements) {
        if (elements instanceof spoon.support.util.EmptyClearableSet) {
            return elements;
        }
        if ((elements == null) || (elements.isEmpty())) {
            return spoon.support.util.EmptyClearableSet.instance();
        }
        java.util.Set<T> others = new java.util.HashSet<>(elements.size());
        for (T element : elements) {
            addClone(others, element);
        }
        return others;
    }

    public <T extends spoon.reflect.declaration.CtElement> java.util.Map<java.lang.String, T> clone(java.util.Map<java.lang.String, T> elements) {
        if ((elements == null) || (elements.isEmpty())) {
            return new java.util.HashMap<>();
        }
        java.util.Map<java.lang.String, T> others = new java.util.HashMap<>();
        for (java.util.Map.Entry<java.lang.String, T> tEntry : elements.entrySet()) {
            addClone(others, tEntry.getKey(), tEntry.getValue());
        }
        return others;
    }

    /**
     * clones a element and adds it's clone as value into targetCollection
     *
     * @param targetCollection
     * 		- the collection which will receive a clone of element
     * @param element
     * 		to be cloned element
     */
    protected <T extends spoon.reflect.declaration.CtElement> void addClone(java.util.Collection<T> targetCollection, T element) {
        targetCollection.add(clone(element));
    }

    /**
     * clones a value and adds it's clone as value into targetMap under key
     *
     * @param targetMap
     * 		- the Map which will receive a clone of value
     * @param key
     * 		the target key, which has to be used to add cloned value into targetMap
     * @param value
     * 		to be cloned element
     */
    protected <T extends spoon.reflect.declaration.CtElement> void addClone(java.util.Map<java.lang.String, T> targetMap, java.lang.String key, T value) {
        targetMap.put(key, clone(value));
    }

    /**
     * Is called by {@link CloneVisitor} at the end of the cloning for each element.
     */
    public void tailor(final spoon.reflect.declaration.CtElement topLevelElement, final spoon.reflect.declaration.CtElement topLevelClone) {
        // this scanner visit certain nodes to done some additional work after cloning
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> clone) {
                // for instance, here we can do additional things
                // after cloning an executable reference
                // we have access here to "topLevelElement" and "topLevelClone"
                // if we want to analyze them as well.
                // super must be called to visit the subelements
                super.visitCtExecutableReference(clone);
            }
        }.scan(topLevelClone);
    }
}

