package spoon.reflect.path.impl;


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

