package spoon.support.visitor.equals;


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

    protected <T extends spoon.reflect.declaration.CtElement> void addClone(java.util.Collection<T> targetCollection, T element) {
        targetCollection.add(clone(element));
    }

    protected <T extends spoon.reflect.declaration.CtElement> void addClone(java.util.Map<java.lang.String, T> targetMap, java.lang.String key, T value) {
        targetMap.put(key, clone(value));
    }

    public void tailor(final spoon.reflect.declaration.CtElement topLevelElement, final spoon.reflect.declaration.CtElement topLevelClone) {
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> clone) {
                super.visitCtExecutableReference(clone);
            }
        }.scan(topLevelClone);
    }
}

