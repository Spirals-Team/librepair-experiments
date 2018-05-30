package spoon.test.staticFieldAccess2;


import spoon.test.staticFieldAccess2.ImplicitStaticFieldReference;

import static spoon.test.staticFieldAccess2.ImplicitStaticFieldReference.ImplicitStaticFieldReference;


public class ImplicitStaticFieldReference {
    public static java.lang.String ImplicitStaticFieldReference = "c1";

    public static long staticField = spoon.test.staticFieldAccess2.Constants.PRIO;

    public java.lang.String reader() {
        return ImplicitStaticFieldReference;
    }

    public void writer(java.lang.String value) {
        ImplicitStaticFieldReference = value;
    }

    public static long longReader() {
        return spoon.test.staticFieldAccess2.ImplicitStaticFieldReference.staticField;
    }

    public static void longWriter(long value) {
        spoon.test.staticFieldAccess2.ImplicitStaticFieldReference.staticField = value;
    }

    public void testLocalMethodInvocations() {
        reader();
        spoon.test.staticFieldAccess2.ImplicitStaticFieldReference.longWriter(7);
    }
}

