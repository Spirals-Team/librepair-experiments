package spoon.test.processing.testclasses;


public class SampleForInsertBefore {
    public SampleForInsertBefore() {
        new java.lang.Thread() {};
    }

    public SampleForInsertBefore(int j) {
        this(j, 0);
        new java.lang.Thread() {};
        switch (j) {
            default :
                break;
        }
        switch (j) {
            default :
                break;
        }
        switch (j) {
            default :
                {
                    break;
                }
        }
    }

    public SampleForInsertBefore(int j, int k) {
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

