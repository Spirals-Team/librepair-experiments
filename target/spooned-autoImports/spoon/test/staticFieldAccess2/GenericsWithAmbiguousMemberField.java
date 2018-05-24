package spoon.test.staticFieldAccess2;


import java.util.List;


public class GenericsWithAmbiguousMemberField {
    String GenericsWithAmbiguousMemberField = "x";

    public <V, C extends List<V>> void m1() {
        spoon.test.staticFieldAccess2.GenericsWithAmbiguousMemberField.<V, C>genericMethod();
        GenericsWithAmbiguousMemberField.length();
        spoon.test.staticFieldAccess2.GenericsWithAmbiguousMemberField.genericMethod();
    }

    public static <V, C extends List<V>> List<C> genericMethod() {
        return null;
    }
}

