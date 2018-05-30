package spoon.test.ctClass.testclasses.issue1306.internal;


public class BooleanArraysBaseTest {
    protected boolean[] actual;

    protected spoon.test.ctClass.testclasses.issue1306.internal.BooleanArrays arrays;

    public void setUp() {
        actual = spoon.test.ctClass.testclasses.issue1306.test.BooleanArrays.arrayOf(true, false);
        arrays = new spoon.test.ctClass.testclasses.issue1306.internal.BooleanArrays();
    }
}

