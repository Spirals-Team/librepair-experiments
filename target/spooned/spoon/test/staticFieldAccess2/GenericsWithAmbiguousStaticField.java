package spoon.test.staticFieldAccess2;


public class GenericsWithAmbiguousStaticField {
    static java.lang.String GenericsWithAmbiguousStaticField = "x";

    public <V, C extends java.util.List<V>> void m1() {
        spoon.test.staticFieldAccess2.GenericsWithAmbiguousStaticField.<V, C>genericMethod();
        spoon.test.staticFieldAccess2.GenericsWithAmbiguousStaticField.genericMethod();
    }

    public static <V, C extends java.util.List<V>> java.util.List<C> genericMethod() {
        return null;
    }
}

