package spoon.test.properties.testclasses;


public class Sample {
    public Sample() {
        new java.lang.Thread() {};
    }

    public Sample(int j) {
        this(j, 0);
        new java.lang.Thread() {};
    }

    public Sample(int j, int k) {
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

