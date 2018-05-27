package spoon.test.staticFieldAccess2;


public class ImplicitStaticClassAccess {
    static java.lang.String ImplicitStaticClassAccess = "";

    ImplicitStaticClassAccess() {
        this(spoon.test.staticFieldAccess2.ImplicitStaticClassAccess.class);
    }

    ImplicitStaticClassAccess(java.lang.Class<?> clazz) {
        spoon.test.staticFieldAccess2.ImplicitStaticClassAccess.class.getName();
    }

    public void testLocalMethodInvocations() {
        spoon.test.staticFieldAccess2.ImplicitStaticClassAccess.class.getName();
    }
}

