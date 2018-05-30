package spoon.test.query_function.testclasses;


public class VariableReferencesFromStaticMethod {
    int field = 1;

    static void staticMethod() {
        int field = 2;
        org.junit.Assert.assertTrue((field == 2));
    }
}

