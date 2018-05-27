package spoon.test.variable.testclasses;


public class FieldAccessSample {
    private static int I;

    private java.lang.String s;

    private int i;

    public void method() {
        s = "tacos";
        java.lang.System.out.println(s);
        i = 3;
        spoon.test.variable.testclasses.FieldAccessSample.I = 42;
    }
}

