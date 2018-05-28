package spoon.test.variable.testclasses;


public class ArrayAccessSample {
    public void method(java.lang.String[] s) {
        s[0] = s[0];
        java.lang.System.err.println(s[0]);
    }
}

