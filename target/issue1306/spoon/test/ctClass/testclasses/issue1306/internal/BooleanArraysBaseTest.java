package spoon.test.ctClass.testclasses.issue1306.internal;


import static spoon.test.ctClass.testclasses.issue1306.test.BooleanArrays.arrayOf;


public class BooleanArraysBaseTest {
    protected boolean[] actual;

    protected BooleanArrays arrays;

    public void setUp() {
        actual = arrayOf(true, false);
        arrays = new BooleanArrays();
    }
}

