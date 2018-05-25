package spoon.test.testclasses;


public class SampleImportClass {
    public SampleImportClass() {
        new java.lang.Thread() {};
    }

    public SampleImportClass(int j) {
        this(j, 0);
        new java.lang.Thread() {};
        java.util.List<?> emptyList = java.util.Collections.EMPTY_LIST;
    }

    public SampleImportClass(int j, int k) {
        super();
        new java.lang.Thread() {};
    }

    void method() {
    }

    void method2() {
        new java.lang.Thread() {};
    }

    java.lang.Thread method3() {
        return new java.lang.Thread() {};
    }
}

