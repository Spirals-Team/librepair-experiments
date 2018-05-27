package spoon.test.staticFieldAccess2;


public class ImplicitStaticFieldReference {
    public static String ImplicitStaticFieldReference = "c1";

    public static long staticField = Constants.PRIO;

    public String reader() {
        return spoon.test.staticFieldAccess2.ImplicitStaticFieldReference.ImplicitStaticFieldReference;
    }

    public void writer(String value) {
        spoon.test.staticFieldAccess2.ImplicitStaticFieldReference.ImplicitStaticFieldReference = value;
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

