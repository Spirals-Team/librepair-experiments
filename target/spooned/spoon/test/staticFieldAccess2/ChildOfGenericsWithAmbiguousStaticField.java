package spoon.test.staticFieldAccess2;


public class ChildOfGenericsWithAmbiguousStaticField extends spoon.test.staticFieldAccess2.GenericsWithAmbiguousStaticField {
    public <V, C extends java.util.List<V>> void m1() {
        spoon.test.staticFieldAccess2.GenericsWithAmbiguousStaticField.<V, C>genericMethod();
        spoon.test.staticFieldAccess2.GenericsWithAmbiguousStaticField.genericMethod();
    }
}

