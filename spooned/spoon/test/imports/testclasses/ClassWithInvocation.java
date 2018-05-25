package spoon.test.imports.testclasses;


public class ClassWithInvocation {
    public ClassWithInvocation() {
        test(spoon.test.annotation.testclasses.GlobalAnnotation.class);
    }

    public java.lang.String test(java.lang.Class cl) {
        return cl.getCanonicalName();
    }
}

