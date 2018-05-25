package spoon.test.staticFieldAccess2;


import java.util.List;


public class GenericsWithAmbiguousStaticField {
    static String GenericsWithAmbiguousStaticField = "x";

    public <V, C extends List<V>> void m1() {
        spoon.test.staticFieldAccess2.GenericsWithAmbiguousStaticField.<V, C>genericMethod();
        spoon.test.staticFieldAccess2.GenericsWithAmbiguousStaticField.genericMethod();
    }

    public static <V, C extends List<V>> List<C> genericMethod() {
        return null;
    }
}

