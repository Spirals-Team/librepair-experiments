package spoon.test.type.testclasses;


public class Pozole<A extends java.lang.annotation.Annotation> {
    @spoon.test.type.testclasses.Spice(klass = spoon.test.type.testclasses.Pozole.class)
    public void make() {
        java.util.List<A> list = new java.util.ArrayList<@spoon.test.annotation.testclasses.TypeAnnotation(clazz = java.lang.Float.class, classes = { java.lang.Integer.class })
        A>();
        addDeliciousIngredient(((java.lang.Class<? extends A>) (java.lang.annotation.Annotation.class)));
    }

    void addDeliciousIngredient(java.lang.Class<? extends A> ingredient) {
    }

    public void eat() {
        java.lang.Object a = null;
        if (a instanceof java.lang.String) {
        }
        if (a instanceof java.util.Collection<?>) {
        }
    }

    public void season() {
        java.lang.Object a = null;
        if (a instanceof java.lang.@spoon.test.annotation.testclasses.TypeAnnotation(integer = 1)
        Object[]) {
        }
        if (a instanceof java.lang.Object[]) {
        }
    }

    public void prepare() {
        class Test<T extends java.lang.Runnable & java.io.Serializable> {}
        final java.lang.Runnable runnable = ((java.lang.Runnable & java.io.Serializable) (() -> java.lang.System.err.println("")));
    }

    public void finish() {
        class Test<T extends java.lang.Runnable> {}
        final java.lang.Runnable runnable = ((java.lang.Runnable) (() -> java.lang.System.err.println("")));
    }
}

