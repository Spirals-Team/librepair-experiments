package spoon.test.staticFieldAccess2;


public class ImplicitStaticClassAccess {
    static String ImplicitStaticClassAccess = "";

    ImplicitStaticClassAccess() {
        this(spoon.test.staticFieldAccess2.ImplicitStaticClassAccess.class);
    }

    ImplicitStaticClassAccess(Class<?> clazz) {
        spoon.test.staticFieldAccess2.ImplicitStaticClassAccess.class.getName();
    }

    public void testLocalMethodInvocations() {
        spoon.test.staticFieldAccess2.ImplicitStaticClassAccess.class.getName();
    }
}

