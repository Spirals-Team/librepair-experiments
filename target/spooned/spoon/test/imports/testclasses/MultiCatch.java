package spoon.test.imports.testclasses;


public class MultiCatch {
    public void test() {
        try {
        } catch (java.lang.ArithmeticException | java.security.AccessControlException e) {
        }
    }
}

