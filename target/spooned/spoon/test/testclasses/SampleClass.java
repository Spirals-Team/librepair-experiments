package spoon.test.testclasses;


public class SampleClass {
    public SampleClass() {
        new java.lang.Thread() {};
    }

    public SampleClass(int j) {
        this(j, 0);
        new java.lang.Thread() {};
    }

    public SampleClass(int j, int k) {
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

